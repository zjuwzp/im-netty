package com.zhss.im.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Message {

    /**
     * 消息头长度
     */
    protected int headerLength;
    /**
     * 客户端SDK版本号
     */
    protected int appSdkVersion;
    /**
     * 消息类型：请求 / 响应
     */
    protected int messageType;
    /**
     * 请求类型
     */
    protected int requestType;
    /**
     * 请求顺序
     */
    protected int sequence;
    /**
     * 消息体长度
     */
    protected int bodyLength;
    /**
     * 消息体
     */
    protected byte[] body;
    /**
     * 封装了完整的请求消息
     */
    protected ByteBuf buffer;

    public Message(int headerLength, int appSdkVersion, int messageType, int requestType, int sequence, int bodyLength, byte[] body, ByteBuf buffer) {
        this.headerLength = headerLength;
        this.appSdkVersion = appSdkVersion;
        this.messageType = messageType;
        this.requestType = requestType;
        this.sequence = sequence;
        this.bodyLength = bodyLength;
        this.body = body;
        this.buffer = buffer;
    }

    public Message() {

    }

    public Message(int appSdkVersion, int messageType, int requestType, int sequence, byte[] body) {
        this.headerLength = Constants.HEADER_LENGTH;
        this.appSdkVersion = appSdkVersion;
        this.messageType = messageType;
        this.requestType = requestType;
        this.sequence = sequence;
        this.bodyLength = body.length;
        this.body = body;

        // 封装完整的带消息头的认证请求
        this.buffer = Unpooled.buffer(
                Constants.HEADER_LENGTH + body.length + Constants.DELIMITER.length);
        this.buffer.writeInt(Constants.HEADER_LENGTH);
        this.buffer.writeInt(appSdkVersion);
        this.buffer.writeInt(messageType);
        this.buffer.writeInt(requestType);
        this.buffer.writeInt(sequence);
        this.buffer.writeInt(body.length);
        this.buffer.writeBytes(body);
        this.buffer.writeBytes(Constants.DELIMITER);
    }

    public Message(ByteBuf buffer) {
        this.headerLength = buffer.readInt();
        this.appSdkVersion = buffer.readInt();
        this.messageType = buffer.readInt();
        this.requestType = buffer.readInt();
        this.sequence = buffer.readInt();
        this.bodyLength = buffer.readInt();
        this.body = new byte[buffer.readableBytes()];
        buffer.readBytes(body);
        this.buffer = buffer;
    }

    public Request toRequest() {
        return new Request(headerLength, appSdkVersion, messageType, requestType, sequence, bodyLength, body, buffer);
    }

    public Response toResponse() {
        return new Response(headerLength, appSdkVersion, messageType, requestType, sequence, bodyLength, body, buffer);
    }

    public int getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(int headerLength) {
        this.headerLength = headerLength;
    }

    public int getAppSdkVersion() {
        return appSdkVersion;
    }

    public void setAppSdkVersion(int appSdkVersion) {
        this.appSdkVersion = appSdkVersion;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public ByteBuf getBuffer() {
        return buffer;
    }

    public void setBuffer(ByteBuf buffer) {
        this.buffer = buffer;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
