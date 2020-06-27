package com.zhss.im.gateway.tcp.dispatcher;

import com.zhss.im.common.Constants;
import com.zhss.im.common.Request;
import com.zhss.im.protocol.AuthenticateRequestProto;
import com.zhss.im.protocol.AuthenticateResponseProto;
import io.netty.channel.socket.SocketChannel;

/**
 * 分发系统实例
 */
public class DispatcherInstance {

    private SocketChannel socketChannel;

    public DispatcherInstance(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    /**
     * 向分发系统发送认证请求
     * @param authenticateRequest
     * @return
     */
    public void authenticate(AuthenticateRequestProto.AuthenticateRequest authenticateRequest) {
        Request request = new Request(
                Constants.APP_SDK_VERSION_1,
                Constants.REQUEST_TYPE_AUTHENTICATE,
                Constants.SEQUENCE_DEFAULT,
                authenticateRequest.toByteArray());
        socketChannel.writeAndFlush(request.getBuffer());
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
}
