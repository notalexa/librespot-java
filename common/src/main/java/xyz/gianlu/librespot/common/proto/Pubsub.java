// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: pubsub.proto

package xyz.gianlu.librespot.common.proto;

@SuppressWarnings("ALL")
public final class Pubsub {
    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_Subscription_descriptor;
    private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_Subscription_fieldAccessorTable;
    private static com.google.protobuf.Descriptors.FileDescriptor
            descriptor;

    static {
        java.lang.String[] descriptorData = {
                "\n\014pubsub.proto\"@\n\014Subscription\022\013\n\003uri\030\001 " +
                        "\001(\t\022\016\n\006expiry\030\002 \001(\005\022\023\n\013status_code\030\003 \001(\005"
        };
        com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
                new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
                    public com.google.protobuf.ExtensionRegistry assignDescriptors(
                            com.google.protobuf.Descriptors.FileDescriptor root) {
                        descriptor = root;
                        return null;
                    }
                };
        com.google.protobuf.Descriptors.FileDescriptor
                .internalBuildGeneratedFileFrom(descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[]{
                        }, assigner);
        internal_static_Subscription_descriptor =
                getDescriptor().getMessageTypes().get(0);
        internal_static_Subscription_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_Subscription_descriptor,
                new java.lang.String[]{"Uri", "Expiry", "StatusCode",});
    }

    private Pubsub() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions(
                (com.google.protobuf.ExtensionRegistryLite) registry);
    }

    public static com.google.protobuf.Descriptors.FileDescriptor
    getDescriptor() {
        return descriptor;
    }

    public interface SubscriptionOrBuilder extends
            // @@protoc_insertion_point(interface_extends:Subscription)
            com.google.protobuf.MessageOrBuilder {

        /**
         * <code>optional string uri = 1;</code>
         */
        boolean hasUri();

        /**
         * <code>optional string uri = 1;</code>
         */
        java.lang.String getUri();

        /**
         * <code>optional string uri = 1;</code>
         */
        com.google.protobuf.ByteString
        getUriBytes();

        /**
         * <code>optional int32 expiry = 2;</code>
         */
        boolean hasExpiry();

        /**
         * <code>optional int32 expiry = 2;</code>
         */
        int getExpiry();

        /**
         * <code>optional int32 status_code = 3;</code>
         */
        boolean hasStatusCode();

        /**
         * <code>optional int32 status_code = 3;</code>
         */
        int getStatusCode();
    }

    /**
     * Protobuf type {@code Subscription}
     */
    public static final class Subscription extends
            com.google.protobuf.GeneratedMessageV3 implements
            // @@protoc_insertion_point(message_implements:Subscription)
            SubscriptionOrBuilder {
        public static final int URI_FIELD_NUMBER = 1;
        public static final int EXPIRY_FIELD_NUMBER = 2;
        public static final int STATUS_CODE_FIELD_NUMBER = 3;
        @java.lang.Deprecated
        public static final com.google.protobuf.Parser<Subscription>
                PARSER = new com.google.protobuf.AbstractParser<Subscription>() {
            @java.lang.Override
            public Subscription parsePartialFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return new Subscription(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0L;
        // @@protoc_insertion_point(class_scope:Subscription)
        private static final Pubsub.Subscription DEFAULT_INSTANCE;

        static {
            DEFAULT_INSTANCE = new Pubsub.Subscription();
        }

        private int bitField0_;
        private volatile java.lang.Object uri_;
        private int expiry_;
        private int statusCode_;
        private byte memoizedIsInitialized = -1;

        // Use Subscription.newBuilder() to construct.
        private Subscription(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Subscription() {
            uri_ = "";
            expiry_ = 0;
            statusCode_ = 0;
        }

        private Subscription(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new java.lang.NullPointerException();
            }
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10: {
                            com.google.protobuf.ByteString bs = input.readBytes();
                            bitField0_ |= 0x00000001;
                            uri_ = bs;
                            break;
                        }
                        case 16: {
                            bitField0_ |= 0x00000002;
                            expiry_ = input.readInt32();
                            break;
                        }
                        case 24: {
                            bitField0_ |= 0x00000004;
                            statusCode_ = input.readInt32();
                            break;
                        }
                        default: {
                            if (!parseUnknownField(
                                    input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }

        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return Pubsub.internal_static_Subscription_descriptor;
        }

        public static Pubsub.Subscription parseFrom(
                java.nio.ByteBuffer data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Pubsub.Subscription parseFrom(
                java.nio.ByteBuffer data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Pubsub.Subscription parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Pubsub.Subscription parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Pubsub.Subscription parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Pubsub.Subscription parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Pubsub.Subscription parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static Pubsub.Subscription parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Pubsub.Subscription parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input);
        }

        public static Pubsub.Subscription parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Pubsub.Subscription parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static Pubsub.Subscription parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Pubsub.Subscription prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public static Pubsub.Subscription getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static com.google.protobuf.Parser<Subscription> parser() {
            return PARSER;
        }

        @java.lang.Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }

        @java.lang.Override
        protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return Pubsub.internal_static_Subscription_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            Pubsub.Subscription.class, Pubsub.Subscription.Builder.class);
        }

        /**
         * <code>optional string uri = 1;</code>
         */
        public boolean hasUri() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }

        /**
         * <code>optional string uri = 1;</code>
         */
        public java.lang.String getUri() {
            java.lang.Object ref = uri_;
            if (ref instanceof java.lang.String) {
                return (java.lang.String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                java.lang.String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    uri_ = s;
                }
                return s;
            }
        }

        /**
         * <code>optional string uri = 1;</code>
         */
        public com.google.protobuf.ByteString
        getUriBytes() {
            java.lang.Object ref = uri_;
            if (ref instanceof java.lang.String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (java.lang.String) ref);
                uri_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        /**
         * <code>optional int32 expiry = 2;</code>
         */
        public boolean hasExpiry() {
            return ((bitField0_ & 0x00000002) == 0x00000002);
        }

        /**
         * <code>optional int32 expiry = 2;</code>
         */
        public int getExpiry() {
            return expiry_;
        }

        /**
         * <code>optional int32 status_code = 3;</code>
         */
        public boolean hasStatusCode() {
            return ((bitField0_ & 0x00000004) == 0x00000004);
        }

        /**
         * <code>optional int32 status_code = 3;</code>
         */
        public int getStatusCode() {
            return statusCode_;
        }

        @java.lang.Override
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1) return true;
            if (isInitialized == 0) return false;

            memoizedIsInitialized = 1;
            return true;
        }

        @java.lang.Override
        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                com.google.protobuf.GeneratedMessageV3.writeString(output, 1, uri_);
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                output.writeInt32(2, expiry_);
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                output.writeInt32(3, statusCode_);
            }
            unknownFields.writeTo(output);
        }

        @java.lang.Override
        public int getSerializedSize() {
            int size = memoizedSize;
            if (size != -1) return size;

            size = 0;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, uri_);
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeInt32Size(2, expiry_);
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeInt32Size(3, statusCode_);
            }
            size += unknownFields.getSerializedSize();
            memoizedSize = size;
            return size;
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Pubsub.Subscription)) {
                return super.equals(obj);
            }
            Pubsub.Subscription other = (Pubsub.Subscription) obj;

            boolean result = true;
            result = result && (hasUri() == other.hasUri());
            if (hasUri()) {
                result = result && getUri()
                        .equals(other.getUri());
            }
            result = result && (hasExpiry() == other.hasExpiry());
            if (hasExpiry()) {
                result = result && (getExpiry()
                        == other.getExpiry());
            }
            result = result && (hasStatusCode() == other.hasStatusCode());
            if (hasStatusCode()) {
                result = result && (getStatusCode()
                        == other.getStatusCode());
            }
            result = result && unknownFields.equals(other.unknownFields);
            return result;
        }

        @java.lang.Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptor().hashCode();
            if (hasUri()) {
                hash = (37 * hash) + URI_FIELD_NUMBER;
                hash = (53 * hash) + getUri().hashCode();
            }
            if (hasExpiry()) {
                hash = (37 * hash) + EXPIRY_FIELD_NUMBER;
                hash = (53 * hash) + getExpiry();
            }
            if (hasStatusCode()) {
                hash = (37 * hash) + STATUS_CODE_FIELD_NUMBER;
                hash = (53 * hash) + getStatusCode();
            }
            hash = (29 * hash) + unknownFields.hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        @java.lang.Override
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @java.lang.Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE
                    ? new Builder() : new Builder().mergeFrom(this);
        }

        @java.lang.Override
        protected Builder newBuilderForType(
                com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        @java.lang.Override
        public com.google.protobuf.Parser<Subscription> getParserForType() {
            return PARSER;
        }

        @java.lang.Override
        public Pubsub.Subscription getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        /**
         * Protobuf type {@code Subscription}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
                // @@protoc_insertion_point(builder_implements:Subscription)
                Pubsub.SubscriptionOrBuilder {
            private int bitField0_;
            private java.lang.Object uri_ = "";
            private int expiry_;
            private int statusCode_;

            // Construct using Pubsub.Subscription.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return Pubsub.internal_static_Subscription_descriptor;
            }

            @java.lang.Override
            protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internalGetFieldAccessorTable() {
                return Pubsub.internal_static_Subscription_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                Pubsub.Subscription.class, Pubsub.Subscription.Builder.class);
            }

            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessageV3
                        .alwaysUseFieldBuilders) {
                }
            }

            @java.lang.Override
            public Builder clear() {
                super.clear();
                uri_ = "";
                bitField0_ = (bitField0_ & ~0x00000001);
                expiry_ = 0;
                bitField0_ = (bitField0_ & ~0x00000002);
                statusCode_ = 0;
                bitField0_ = (bitField0_ & ~0x00000004);
                return this;
            }

            @java.lang.Override
            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return Pubsub.internal_static_Subscription_descriptor;
            }

            @java.lang.Override
            public Pubsub.Subscription getDefaultInstanceForType() {
                return Pubsub.Subscription.getDefaultInstance();
            }

            @java.lang.Override
            public Pubsub.Subscription build() {
                Pubsub.Subscription result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            @java.lang.Override
            public Pubsub.Subscription buildPartial() {
                Pubsub.Subscription result = new Pubsub.Subscription(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                result.uri_ = uri_;
                if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
                    to_bitField0_ |= 0x00000002;
                }
                result.expiry_ = expiry_;
                if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
                    to_bitField0_ |= 0x00000004;
                }
                result.statusCode_ = statusCode_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            @java.lang.Override
            public Builder clone() {
                return (Builder) super.clone();
            }

            @java.lang.Override
            public Builder setField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    java.lang.Object value) {
                return (Builder) super.setField(field, value);
            }

            @java.lang.Override
            public Builder clearField(
                    com.google.protobuf.Descriptors.FieldDescriptor field) {
                return (Builder) super.clearField(field);
            }

            @java.lang.Override
            public Builder clearOneof(
                    com.google.protobuf.Descriptors.OneofDescriptor oneof) {
                return (Builder) super.clearOneof(oneof);
            }

            @java.lang.Override
            public Builder setRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    int index, java.lang.Object value) {
                return (Builder) super.setRepeatedField(field, index, value);
            }

            @java.lang.Override
            public Builder addRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    java.lang.Object value) {
                return (Builder) super.addRepeatedField(field, value);
            }

            @java.lang.Override
            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof Pubsub.Subscription) {
                    return mergeFrom((Pubsub.Subscription) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(Pubsub.Subscription other) {
                if (other == Pubsub.Subscription.getDefaultInstance()) return this;
                if (other.hasUri()) {
                    bitField0_ |= 0x00000001;
                    uri_ = other.uri_;
                    onChanged();
                }
                if (other.hasExpiry()) {
                    setExpiry(other.getExpiry());
                }
                if (other.hasStatusCode()) {
                    setStatusCode(other.getStatusCode());
                }
                this.mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            @java.lang.Override
            public final boolean isInitialized() {
                return true;
            }

            @java.lang.Override
            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                Pubsub.Subscription parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (Pubsub.Subscription) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            /**
             * <code>optional string uri = 1;</code>
             */
            public boolean hasUri() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }

            /**
             * <code>optional string uri = 1;</code>
             */
            public java.lang.String getUri() {
                java.lang.Object ref = uri_;
                if (!(ref instanceof java.lang.String)) {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    java.lang.String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        uri_ = s;
                    }
                    return s;
                } else {
                    return (java.lang.String) ref;
                }
            }

            /**
             * <code>optional string uri = 1;</code>
             */
            public Builder setUri(
                    java.lang.String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                uri_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>optional string uri = 1;</code>
             */
            public com.google.protobuf.ByteString
            getUriBytes() {
                java.lang.Object ref = uri_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (java.lang.String) ref);
                    uri_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            /**
             * <code>optional string uri = 1;</code>
             */
            public Builder setUriBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                uri_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>optional string uri = 1;</code>
             */
            public Builder clearUri() {
                bitField0_ = (bitField0_ & ~0x00000001);
                uri_ = getDefaultInstance().getUri();
                onChanged();
                return this;
            }

            /**
             * <code>optional int32 expiry = 2;</code>
             */
            public boolean hasExpiry() {
                return ((bitField0_ & 0x00000002) == 0x00000002);
            }

            /**
             * <code>optional int32 expiry = 2;</code>
             */
            public int getExpiry() {
                return expiry_;
            }

            /**
             * <code>optional int32 expiry = 2;</code>
             */
            public Builder setExpiry(int value) {
                bitField0_ |= 0x00000002;
                expiry_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>optional int32 expiry = 2;</code>
             */
            public Builder clearExpiry() {
                bitField0_ = (bitField0_ & ~0x00000002);
                expiry_ = 0;
                onChanged();
                return this;
            }

            /**
             * <code>optional int32 status_code = 3;</code>
             */
            public boolean hasStatusCode() {
                return ((bitField0_ & 0x00000004) == 0x00000004);
            }

            /**
             * <code>optional int32 status_code = 3;</code>
             */
            public int getStatusCode() {
                return statusCode_;
            }

            /**
             * <code>optional int32 status_code = 3;</code>
             */
            public Builder setStatusCode(int value) {
                bitField0_ |= 0x00000004;
                statusCode_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>optional int32 status_code = 3;</code>
             */
            public Builder clearStatusCode() {
                bitField0_ = (bitField0_ & ~0x00000004);
                statusCode_ = 0;
                onChanged();
                return this;
            }

            @java.lang.Override
            public final Builder setUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.setUnknownFields(unknownFields);
            }

            @java.lang.Override
            public final Builder mergeUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.mergeUnknownFields(unknownFields);
            }


            // @@protoc_insertion_point(builder_scope:Subscription)
        }

    }

    // @@protoc_insertion_point(outer_class_scope)
}