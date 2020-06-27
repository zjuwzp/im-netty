package com.zhss.im.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 网络请求
 */
public class Request extends Message {

    public Request(int appSdkVersion, int requestType, int sequence, byte[] body) {
        super(appSdkVersion, Constants.MESSAGE_TYPE_REQUEST, requestType, sequence, body);
    }

    public Request(ByteBuf buffer) {
        super(buffer);
    }

    public Request(int headerLength, int appSdkVersion, int messageType, int requestType, int sequence, int bodyLength, byte[] body, ByteBuf buffer) {
        super(headerLength, appSdkVersion, messageType, requestType, sequence, bodyLength, body, buffer);
    }

}
