package com.zhss.im.protocol;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: im/proto/AuthenticateResponse.proto

public final class AuthenticateResponseProto {
  private AuthenticateResponseProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface AuthenticateResponseOrBuilder extends
      // @@protoc_insertion_point(interface_extends:AuthenticateResponse)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 status = 1;</code>
     */
    int getStatus();

    /**
     * <code>int32 errorCode = 2;</code>
     */
    int getErrorCode();

    /**
     * <code>string errorMessage = 3;</code>
     */
    String getErrorMessage();
    /**
     * <code>string errorMessage = 3;</code>
     */
    com.google.protobuf.ByteString
        getErrorMessageBytes();
  }
  /**
   * Protobuf type {@code AuthenticateResponse}
   */
  public  static final class AuthenticateResponse extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:AuthenticateResponse)
      AuthenticateResponseOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use AuthenticateResponse.newBuilder() to construct.
    private AuthenticateResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private AuthenticateResponse() {
      errorMessage_ = "";
    }

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
        UnusedPrivateParameter unused) {
      return new AuthenticateResponse();
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private AuthenticateResponse(
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
            case 8: {

              status_ = input.readInt32();
              break;
            }
            case 16: {

              errorCode_ = input.readInt32();
              break;
            }
            case 26: {
              String s = input.readStringRequireUtf8();

              errorMessage_ = s;
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
      return AuthenticateResponseProto.internal_static_AuthenticateResponse_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return AuthenticateResponseProto.internal_static_AuthenticateResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              AuthenticateResponse.class, Builder.class);
    }

    public static final int STATUS_FIELD_NUMBER = 1;
    private int status_;
    /**
     * <code>int32 status = 1;</code>
     */
    public int getStatus() {
      return status_;
    }

    public static final int ERRORCODE_FIELD_NUMBER = 2;
    private int errorCode_;
    /**
     * <code>int32 errorCode = 2;</code>
     */
    public int getErrorCode() {
      return errorCode_;
    }

    public static final int ERRORMESSAGE_FIELD_NUMBER = 3;
    private volatile Object errorMessage_;
    /**
     * <code>string errorMessage = 3;</code>
     */
    public String getErrorMessage() {
      Object ref = errorMessage_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        errorMessage_ = s;
        return s;
      }
    }
    /**
     * <code>string errorMessage = 3;</code>
     */
    public com.google.protobuf.ByteString
        getErrorMessageBytes() {
      Object ref = errorMessage_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        errorMessage_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
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
      if (status_ != 0) {
        output.writeInt32(1, status_);
      }
      if (errorCode_ != 0) {
        output.writeInt32(2, errorCode_);
      }
      if (!getErrorMessageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, errorMessage_);
      }
      unknownFields.writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (status_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, status_);
      }
      if (errorCode_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, errorCode_);
      }
      if (!getErrorMessageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, errorMessage_);
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
      if (!(obj instanceof AuthenticateResponse)) {
        return super.equals(obj);
      }
      AuthenticateResponse other = (AuthenticateResponse) obj;

      if (getStatus()
          != other.getStatus()) return false;
      if (getErrorCode()
          != other.getErrorCode()) return false;
      if (!getErrorMessage()
          .equals(other.getErrorMessage())) return false;
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
      hash = (37 * hash) + STATUS_FIELD_NUMBER;
      hash = (53 * hash) + getStatus();
      hash = (37 * hash) + ERRORCODE_FIELD_NUMBER;
      hash = (53 * hash) + getErrorCode();
      hash = (37 * hash) + ERRORMESSAGE_FIELD_NUMBER;
      hash = (53 * hash) + getErrorMessage().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static AuthenticateResponse parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AuthenticateResponse parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AuthenticateResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AuthenticateResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AuthenticateResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AuthenticateResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AuthenticateResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static AuthenticateResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static AuthenticateResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static AuthenticateResponse parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static AuthenticateResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static AuthenticateResponse parseFrom(
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
    public static Builder newBuilder(AuthenticateResponse prototype) {
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
     * Protobuf type {@code AuthenticateResponse}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:AuthenticateResponse)
        AuthenticateResponseOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return AuthenticateResponseProto.internal_static_AuthenticateResponse_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return AuthenticateResponseProto.internal_static_AuthenticateResponse_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                AuthenticateResponse.class, Builder.class);
      }

      // Construct using AuthenticateResponseProto.AuthenticateResponse.newBuilder()
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
        status_ = 0;

        errorCode_ = 0;

        errorMessage_ = "";

        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return AuthenticateResponseProto.internal_static_AuthenticateResponse_descriptor;
      }

      @Override
      public AuthenticateResponse getDefaultInstanceForType() {
        return AuthenticateResponse.getDefaultInstance();
      }

      @Override
      public AuthenticateResponse build() {
        AuthenticateResponse result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public AuthenticateResponse buildPartial() {
        AuthenticateResponse result = new AuthenticateResponse(this);
        result.status_ = status_;
        result.errorCode_ = errorCode_;
        result.errorMessage_ = errorMessage_;
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
        if (other instanceof AuthenticateResponse) {
          return mergeFrom((AuthenticateResponse)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(AuthenticateResponse other) {
        if (other == AuthenticateResponse.getDefaultInstance()) return this;
        if (other.getStatus() != 0) {
          setStatus(other.getStatus());
        }
        if (other.getErrorCode() != 0) {
          setErrorCode(other.getErrorCode());
        }
        if (!other.getErrorMessage().isEmpty()) {
          errorMessage_ = other.errorMessage_;
          onChanged();
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
        AuthenticateResponse parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (AuthenticateResponse) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int status_ ;
      /**
       * <code>int32 status = 1;</code>
       */
      public int getStatus() {
        return status_;
      }
      /**
       * <code>int32 status = 1;</code>
       */
      public Builder setStatus(int value) {
        
        status_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 status = 1;</code>
       */
      public Builder clearStatus() {
        
        status_ = 0;
        onChanged();
        return this;
      }

      private int errorCode_ ;
      /**
       * <code>int32 errorCode = 2;</code>
       */
      public int getErrorCode() {
        return errorCode_;
      }
      /**
       * <code>int32 errorCode = 2;</code>
       */
      public Builder setErrorCode(int value) {
        
        errorCode_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 errorCode = 2;</code>
       */
      public Builder clearErrorCode() {
        
        errorCode_ = 0;
        onChanged();
        return this;
      }

      private Object errorMessage_ = "";
      /**
       * <code>string errorMessage = 3;</code>
       */
      public String getErrorMessage() {
        Object ref = errorMessage_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          errorMessage_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string errorMessage = 3;</code>
       */
      public com.google.protobuf.ByteString
          getErrorMessageBytes() {
        Object ref = errorMessage_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          errorMessage_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string errorMessage = 3;</code>
       */
      public Builder setErrorMessage(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        errorMessage_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string errorMessage = 3;</code>
       */
      public Builder clearErrorMessage() {
        
        errorMessage_ = getDefaultInstance().getErrorMessage();
        onChanged();
        return this;
      }
      /**
       * <code>string errorMessage = 3;</code>
       */
      public Builder setErrorMessageBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        errorMessage_ = value;
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


      // @@protoc_insertion_point(builder_scope:AuthenticateResponse)
    }

    // @@protoc_insertion_point(class_scope:AuthenticateResponse)
    private static final AuthenticateResponse DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new AuthenticateResponse();
    }

    public static AuthenticateResponse getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<AuthenticateResponse>
        PARSER = new com.google.protobuf.AbstractParser<AuthenticateResponse>() {
      @Override
      public AuthenticateResponse parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new AuthenticateResponse(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<AuthenticateResponse> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<AuthenticateResponse> getParserForType() {
      return PARSER;
    }

    @Override
    public AuthenticateResponse getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_AuthenticateResponse_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_AuthenticateResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n#im/proto/AuthenticateResponse.proto\"O\n" +
      "\024AuthenticateResponse\022\016\n\006status\030\001 \001(\005\022\021\n" +
      "\terrorCode\030\002 \001(\005\022\024\n\014errorMessage\030\003 \001(\tB\033" +
      "B\031AuthenticateResponseProtob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_AuthenticateResponse_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_AuthenticateResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_AuthenticateResponse_descriptor,
        new String[] { "Status", "ErrorCode", "ErrorMessage", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
