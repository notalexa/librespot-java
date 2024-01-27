/*
 * Copyright 2021 devgianlu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.gianlu.librespot.dealer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.gianlu.librespot.common.AsyncWorker;
import xyz.gianlu.librespot.common.BytesArrayList;
import xyz.gianlu.librespot.common.NameThreadFactory;
import xyz.gianlu.librespot.common.Utils;
import xyz.gianlu.librespot.core.Session;
import xyz.gianlu.librespot.mercury.MercuryClient;

import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

/**
 * @author Gianlu
 */
public class DealerClient implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(DealerClient.class);
    private final AsyncWorker<Runnable> asyncWorker;
    private final Session session;
    private final Map<String, RequestListener> reqListeners = new HashMap<>();
    private final Map<MessageListener, List<String>> msgListeners = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new NameThreadFactory((r) -> "dealer-scheduler-" + r.hashCode()));
    private volatile ConnectionHolder conn = null;
    private ScheduledFuture<?> lastScheduledReconnection;
    private CountDownLatch startLatch=new CountDownLatch(1);

    public DealerClient(@NotNull Session session) {
        this.session = session;
        this.asyncWorker = new AsyncWorker<>("dealer-worker", Runnable::run);
    }
    
    public void start() {
    	startLatch.countDown();
    }

    @NotNull
    private static Map<String, String> getHeaders(@NotNull JsonObject obj) {
        JsonObject headers = obj.getAsJsonObject("headers");
        if (headers == null) return Collections.emptyMap();

        Map<String, String> map = new HashMap<>();
        for (String key : headers.keySet()) map.put(key, headers.get(key).getAsString());
        return map;
    }

    /**
     * Creates a new WebSocket client. <b>Intended for internal use only!</b>
     */
    public synchronized void connect() throws IOException, MercuryClient.MercuryException {
        conn = new ConnectionHolder(session, new Request.Builder()
                .url(String.format("wss://%s/?access_token=%s", session.apResolver().getRandomDealer(), session.tokens().get("playlist-read")))
                .build());
    }

    private void waitForListeners() {
    	try {
    		startLatch.await();
    	} catch(Throwable t) {
    	}
    }

    private void handleRequest(@NotNull JsonObject obj) {
        String mid = obj.get("message_ident").getAsString();
        String key = obj.get("key").getAsString();

        Map<String, String> headers = getHeaders(obj);
        JsonObject payload = obj.getAsJsonObject("payload");
        if ("gzip".equals(headers.get("Transfer-Encoding"))) {
            byte[] gzip = Utils.fromBase64(payload.get("compressed").getAsString());
            try (GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(gzip)); Reader reader = new InputStreamReader(in)) {
                payload = JsonParser.parseReader(reader).getAsJsonObject();
            } catch (IOException ex) {
                LOGGER.warn("Failed decompressing request! {mid: {}, key: {}}", mid, key, ex);
                return;
            }
        }

        int pid = payload.get("message_id").getAsInt();
        String sender = payload.get("sent_by_device_id").getAsString();

        JsonObject command = payload.getAsJsonObject("command");
        LOGGER.trace("Received request. {mid: {}, key: {}, pid: {}, sender: {}, command: {}}", mid, key, pid, sender, command);

        boolean interesting = false;
        synchronized (reqListeners) {
            for (String midPrefix : reqListeners.keySet()) {
                if (mid.startsWith(midPrefix)) {
                    RequestListener listener = reqListeners.get(midPrefix);
                    interesting = true;
                    asyncWorker.submit(() -> {
                        try {
                            RequestResult result = listener.onRequest(mid, pid, sender, command);
                            if (conn != null) conn.sendReply(key, result);
                            LOGGER.debug("Handled request. {key: {}, result: {}}", key, result);
                        } catch (Exception ex) {
                            if (conn != null) conn.sendReply(key, RequestResult.UPSTREAM_ERROR);
                            LOGGER.error("Failed handling request. {key: {}}", key, ex);
                        }
                    });
                }
            }
        }

        if (!interesting) LOGGER.debug("Couldn't dispatch request: " + mid);
    }

    private void handleMessage(@NotNull JsonObject obj) {
        String uri = obj.get("uri").getAsString();

        Map<String, String> headers = getHeaders(obj);
        JsonArray payloads = obj.getAsJsonArray("payloads");
        byte[] decodedPayload;
        if (payloads != null) {
            if ("application/json".equals(headers.get("Content-Type"))) {
                if (payloads.size() > 1) throw new UnsupportedOperationException();
                decodedPayload = payloads.get(0).getAsJsonObject().toString().getBytes();
            } else if ("text/plain".equals(headers.get("Content-Type"))) {
                if (payloads.size() > 1) throw new UnsupportedOperationException();
                decodedPayload = payloads.get(0).getAsString().getBytes();
            } else {
                String[] payloadsStr = new String[payloads.size()];
                for (int i = 0; i < payloads.size(); i++) payloadsStr[i] = payloads.get(i).getAsString();

                InputStream in = BytesArrayList.streamBase64(payloadsStr);
                if ("gzip".equals(headers.get("Transfer-Encoding"))) {
                    try {
                        in = new GZIPInputStream(in);
                    } catch (IOException ex) {
                        LOGGER.warn("Failed decompressing message! {uri: {}}", uri, ex);
                        return;
                    }
                }

                try {
                    ByteArrayOutputStream out = new ByteArrayOutputStream(in.available());
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer)) != -1) out.write(buffer, 0, read);
                    decodedPayload = out.toByteArray();
                } catch (IOException ex) {
                    throw new IllegalStateException(ex);
                } finally {
                    try {
                        in.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        } else {
            decodedPayload = new byte[0];
        }

        boolean interesting = false;
        synchronized (msgListeners) {
            for (MessageListener listener : msgListeners.keySet()) {
                boolean dispatched = false;
                List<String> keys = msgListeners.get(listener);
                for (String key : keys) {
                    if (uri.startsWith(key) && !dispatched) {
                        interesting = true;
                        asyncWorker.submit(() -> {
                            try {
                                listener.onMessage(uri, headers, decodedPayload);
                            } catch (IOException ex) {
                                LOGGER.error("Failed dispatching message! {uri: {}}", uri, ex);
                            } catch (Exception ex) {
                                LOGGER.error("Failed handling message! {uri: {}}", uri, ex);
                            }
                        });
                        dispatched = true;
                    }
                }
            }
        }

        if (!interesting) LOGGER.debug("Couldn't dispatch message: " + uri);
    }

    public void addMessageListener(@NotNull MessageListener listener, @NotNull String... uris) {
        synchronized (msgListeners) {
            if (msgListeners.containsKey(listener))
                throw new IllegalArgumentException(String.format("A listener for %s has already been added.", Arrays.toString(uris)));

            msgListeners.put(listener, Arrays.asList(uris));
            msgListeners.notifyAll();
        }
    }

    public void removeMessageListener(@NotNull MessageListener listener) {
        synchronized (msgListeners) {
            msgListeners.remove(listener);
        }
    }

    public void addRequestListener(@NotNull RequestListener listener, @NotNull String uri) {
        synchronized (reqListeners) {
            if (reqListeners.containsKey(uri))
                throw new IllegalArgumentException(String.format("A listener for '%s' has already been added.", uri));

            reqListeners.put(uri, listener);
            reqListeners.notifyAll();
        }
    }

    public void removeRequestListener(@NotNull RequestListener listener) {
        synchronized (reqListeners) {
            reqListeners.values().remove(listener);
        }
    }

    @Override
    public void close() {
        asyncWorker.close();
        scheduler.shutdown();

        if (conn != null) {
            ConnectionHolder tmp = conn; // Do not trigger connectionInvalided()
            conn = null;
            tmp.close();
        }

        if (lastScheduledReconnection != null) {
            lastScheduledReconnection.cancel(true);
            lastScheduledReconnection = null;
        }

        msgListeners.clear();
    }

    /**
     * Called when the {@link ConnectionHolder} has been closed and cannot be used no more for a connection.
     */
    private synchronized void connectionInvalided() {
        if (lastScheduledReconnection != null && !lastScheduledReconnection.isDone())
            throw new IllegalStateException();

        conn = null;

        LOGGER.trace("Scheduled reconnection attempt in 10 seconds...");
        lastScheduledReconnection = scheduler.schedule(() -> {
            lastScheduledReconnection = null;

            try {
                connect();
            } catch (IOException | MercuryClient.MercuryException ex) {
                LOGGER.error("Failed reconnecting, retrying...", ex);
                connectionInvalided();
            }
        }, 10, TimeUnit.SECONDS);
    }

    public enum RequestResult {
        UNKNOWN_SEND_COMMAND_RESULT, SUCCESS,
        DEVICE_NOT_FOUND, CONTEXT_PLAYER_ERROR,
        DEVICE_DISAPPEARED, UPSTREAM_ERROR,
        DEVICE_DOES_NOT_SUPPORT_COMMAND, RATE_LIMITED
    }

    public interface RequestListener {
        @NotNull
        RequestResult onRequest(@NotNull String mid, int pid, @NotNull String sender, @NotNull JsonObject command);
    }

    public interface MessageListener {
        void onMessage(@NotNull String uri, @NotNull Map<String, String> headers, @NotNull byte[] payload) throws IOException;
    }

    private class ConnectionHolder implements Closeable {
        private final WebSocket ws;
        private boolean closed = false;
        private boolean receivedPong = false;
        private ScheduledFuture<?> lastScheduledPing;

        ConnectionHolder(@NotNull Session session, @NotNull Request request) {
            ws = session.client().newWebSocket(request, new WebSocketListenerImpl());
        }

        private void sendPing() {
            ws.send("{\"type\":\"ping\"}");
        }

        void sendReply(@NotNull String key, @NotNull RequestResult result) {
            boolean success = result == RequestResult.SUCCESS;
            ws.send(String.format("{\"type\":\"reply\", \"key\": \"%s\", \"payload\": {\"success\": %b}}", key, success));
        }

        @Override
        public void close() {
            if (closed) {
                ws.cancel();
            } else {
                closed = true;
                ws.close(1000, null);
            }

            if (lastScheduledPing != null) {
                lastScheduledPing.cancel(false);
                lastScheduledPing = null;
            }

            if (conn == ConnectionHolder.this)
                connectionInvalided();
            else
                LOGGER.debug("Did not dispatch connection invalidated: {} != {}", conn, ConnectionHolder.this);
        }

        private class WebSocketListenerImpl extends WebSocketListener {

            @Override
            public void onOpen(@NotNull WebSocket ws, @NotNull Response response) {
                if (closed || scheduler.isShutdown()) {
                    LOGGER.error("I wonder what happened here... Terminating. {closed: {}}", closed);
                    return;
                }

                LOGGER.debug("Dealer connected! {host: {}}", response.request().url().host());
                lastScheduledPing = scheduler.scheduleAtFixedRate(() -> {
                    sendPing();
                    receivedPong = false;

                    scheduler.schedule(() -> {
                        if (lastScheduledPing == null || lastScheduledPing.isCancelled()) return;

                        if (!receivedPong) {
                            LOGGER.warn("Did not receive ping in 3 seconds. Reconnecting...");
                            ConnectionHolder.this.close();
                            return;
                        }

                        receivedPong = false;
                    }, 3, TimeUnit.SECONDS);
                }, 0, 30, TimeUnit.SECONDS);
            }

            @Override
            public void onMessage(@NotNull WebSocket ws, @NotNull String text) {
                JsonObject obj = JsonParser.parseString(text).getAsJsonObject();

                waitForListeners();

                MessageType type = MessageType.parse(obj.get("type").getAsString());
                switch (type) {
                    case MESSAGE:
                        try {
                            handleMessage(obj);
                        } catch (Exception ex) {
                            LOGGER.warn("Failed handling message: " + obj, ex);
                        }
                        break;
                    case REQUEST:
                        try {
                            handleRequest(obj);
                        } catch (Exception ex) {
                            LOGGER.warn("Failed handling request: " + obj, ex);
                        }
                        break;
                    case PONG:
                        receivedPong = true;
                        break;
                    case PING:
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown message type for " + type);
                }
            }

            @Override
            public void onFailure(@NotNull WebSocket ws, @NotNull Throwable t, @Nullable Response response) {
                if (closed) return;

                LOGGER.warn("An exception occurred. Reconnecting...", t);
                ConnectionHolder.this.close();
            }
        }
    }
}
