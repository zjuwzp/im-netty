package com.zhss.im.protocol;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: im/proto/AuthenticateRequest.proto

public final class AuthenticateRequestProto {
  private AuthenticateRequestProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface AuthenticateRequestOrBuilder extends
      // @@protoc_insertion_point(interface_extends:AuthenticateRequest)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string uid = 1;</code>
     */
    String getUid();
    /**
     * <code>string uid = 1;</code>
     */
    com.google.protobuf.ByteString
        getUidBytes();

    /**
     * <code>string token = 2;</code>
     */
    String getToken();
    /**
     * <code>string token = 2;</code>
     */
    com.google.protobuf.ByteString
        getTokenBytes();

    /**
     * <code>int64 timestamp = 3;</code>
     */
    long getTimestamp();
  }
  /**
   * Protobuf type {@code AuthenticateRequest}
   */
  public  static final class AuthenticateRequest extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:AuthenticateRequest)
      AuthenticateRequestOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use AuthenticateRequest.newBuilder() to construct.
    private AuthenticateRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private AuthenticateRequest() {
      uid_ = "";
      token_ = "";
    }

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
        UnusedPrivateParameter unused) {
      return new AuthenticateRequest();
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private AuthenticateRequest(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new NullPointerException();
      }
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
              String s = input.readStringRequireUtf8();

              uid_ = s;
              break;
            }
            case 18: {
              String s = input.readStringRequireUtf8();

              token_ = s;
              break;
            }
            case 24: {

              timestamp_ = input.readInt64();
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
      return AuthenticateRequestProto.internal_static_AuthenticateRequest_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return AuthenticateRequestProto.internal_static_AuthenticateRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              AuthenticateRequest.class, Builder.class);
    }

    public static final int UID_FIELD_NUMBER = 1;
    private volatile Object uid_;
    /**
     * <code>string uid = 1;</code>
     */
    public String getUid() {
      Object ref = uid_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        uid_ = s;
        return s;
      }
    }
    /**
     * <code>string uid = 1;</code>
     */
    public com.google.protobuf.ByteString
        getUidBytes() {
      Object ref = uid_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        uid_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TOKEN_FIELD_NUMBER = 2;
    private volatile Object token_;
    /**
     * <code>string token = 2;</code>
     */
    public String getToken() {
      Object ref = token_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        token_ = s;
        return s;
      }
    }
    /**
     * <code>string token = 2;</code>
     */
    public com.google.protobuf.ByteString
        getTokenBytes() {
      Object ref = token_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        token_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TIMESTAMP_FIELD_NUMBER = 3;
    private long timestamp_;
    /**
     * <code>int64 timestamp = 3;</code>
     */
    public long getTimestamp() {
      return timestamp_;
    }

    private byte memoizedIsInitialized = -1;
    @Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!getUidBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, uid_);
      }
      if (!getTokenBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, token_);
      }
      if (timestamp_ != 0L) {
        output.writeInt64(3, timestamp_);
      }
      unknownFields.writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getUidBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, uid_);
      }
      if (!getTokenBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, token_);
      }
      if (timestamp_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(3, timestamp_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof AuthenticateRequest)) {
        return super.equals(obj);
      }
      AuthenticateRequest other = (AuthenticateRequest) obj;

      if (!getUid()
          .equals(other.getUid())) return false;
      if (!getToken()
          .equals(other.getToken())) return false;
      if (getTimestamp()
          != other.getTimestamp()) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + UID_FIELD_NUMBER;
      hash = (53 * hash) + getUid().hashCode();
      hash = (37 * hash) + TOKEN_FIELD_NUMBER;
      hash = (53 * hash) + getToken().hashCode();
      hash = (37 * hash) + TIMESTAMP_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getTimestamp());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static AuthenticateRequest parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AuthenticateRequest parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AuthenticateRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AuthenticateRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AuthenticateRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AuthenticateRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AuthenticateRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static AuthenticateRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static AuthenticateRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static AuthenticateRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static AuthenticateRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static AuthenticateRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(AuthenticateRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code AuthenticateRequest}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:AuthenticateRequest)
        AuthenticateRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return AuthenticateRequestProto.internal_static_AuthenticateRequest_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return AuthenticateRequestProto.internal_static_AuthenticateRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                AuthenticateRequest.class, Builder.class);
      }

      // Construct using AuthenticateRequestProto.AuthenticateRequest.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @Override
      public Builder clear() {
        super.clear();
        uid_ = "";

        token_ = "";

        timestamp_ = 0L;

        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return AuthenticateRequestProto.internal_static_AuthenticateRequest_descriptor;
      }

      @Override
      public AuthenticateRequest getDefaultInstanceForType() {
        return AuthenticateRequest.getDefaultInstance();
      }

      @Override
      public AuthenticateRequest build() {
        AuthenticateRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public AuthenticateRequest buildPartial() {
        AuthenticateRequest result = new AuthenticateRequest(this);
        result.uid_ = uid_;
        result.token_ = token_;
        result.timestamp_ = timestamp_;
        onBuilt();
        return result;
      }

      @Override
      public Builder clone() {
        return super.clone();
      }
      @Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.setField(field, value);
      }
      @Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.addRepeatedField(field, value);
      }
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof AuthenticateRequest) {
          return mergeFrom((AuthenticateRequest)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(AuthenticateRequest other) {
        if (other == AuthenticateRequest.getDefaultInstance()) return this;
        if (!other.getUid().isEmpty()) {
          uid_ = other.uid_;
          onChanged();
        }
        if (!other.getToken().isEmpty()) {
          token_ = other.token_;
          onChanged();
        }
        if (other.getTimestamp() != 0L) {
          setTimestamp(other.getTimestamp());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @Override
      public final boolean isInitialized() {
        return true;
      }

      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        AuthenticateRequest parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (AuthenticateRequest) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private Object uid_ = "";
      /**
       * <code>string uid = 1;</code>
       */
      public String getUid() {
        Object ref = uid_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          uid_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string uid = 1;</code>
       */
      public com.google.protobuf.ByteString
          getUidBytes() {
        Object ref = uid_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          uid_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string uid = 1;</code>
       */
      public Builder setUid(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        uid_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string uid = 1;</code>
       */
      public Builder clearUid() {
        
        uid_ = getDefaultInstance().getUid();
        onChanged();
        return this;
      }
      /**
       * <code>string uid = 1;</code>
       */
      public Builder setUidBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        uid_ = value;
        onChanged();
        return this;
      }

      private Object token_ = "";
      /**
       * <code>string token = 2;</code>
       */
      public String getToken() {
        Object ref = token_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          token_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string token = 2;</code>
       */
      public com.google.protobuf.ByteString
          getTokenBytes() {
        Object ref = token_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          token_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string token = 2;</code>
       */
      public Builder setToken(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        token_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string token = 2;</code>
       */
      public Builder clearToken() {
        
        token_ = getDefaultInstance().getToken();
        onChanged();
        return this;
      }
      /**
       * <code>string token = 2;</code>
       */
      public Builder setTokenBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        token_ = value;
        onChanged();
        return this;
      }

      private long timestamp_ ;
      /**
       * <code>int64 timestamp = 3;</code>
       */
      public long getTimestamp() {
        return timestamp_;
      }
      /**
       * <code>int64 timestamp = 3;</code>
       */
      public Builder setTimestamp(long value) {
        
        timestamp_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 timestamp = 3;</code>
       */
      public Builder clearTimestamp() {
        
        timestamp_ = 0L;
        onChanged();
        return this;
      }
      @Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:AuthenticateRequest)
    }

    // @@protoc_insertion_point(class_scope:AuthenticateRequest)
    private static final AuthenticateRequest DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new AuthenticateRequest();
    }

    public static AuthenticateRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<AuthenticateRequest>
        PARSER = new com.google.protobuf.AbstractParser<AuthenticateRequest>() {
      @Override
      public AuthenticateRequest parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new AuthenticateRequest(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<AuthenticateRequest> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<AuthenticateRequest> getParserForType() {
      return PARSER;
    }

    @Override
    public AuthenticateRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_AuthenticateRequest_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_AuthenticateRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\"im/proto/AuthenticateRequest.proto\"D\n\023" +
      "AuthenticateRequest\022\013\n\003uid\030\001 \001(\t\022\r\n\005toke" +
      "n\030\002 \001(\t\022\021\n\ttimestamp\030\003 \001(\003B\032B\030Authentica" +
      "teRequestProtob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_AuthenticateRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_AuthenticateRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_AuthenticateRequest_descriptor,
        new String[] { "Uid", "Token", "Timestamp", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
