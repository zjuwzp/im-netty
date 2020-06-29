package com.zhss.im.sdk.android;

import com.zhss.im.common.Constants;
import com.zhss.im.common.Request;
import com.zhss.im.protocol.AuthenticateRequestProto;
import com.zhss.im.protocol.MessageSendRequestProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

// 如果你的APP要跟一台机器建立一个连接
// 此时就可以新建一个ImClient，这个Client就代表跟机器的一个连接就可以了
public class ImClient {

    // 代表的是Netty客户端中的线程池
    private EventLoopGroup threadGroup;
    // 代表的是Netty客户端
    private Bootstrap client;
    // 代表的是客户端APP跟TCP接入系统的某台机器的长连接
    private SocketChannel socketChannel;
    // 是否完成认证后的连接
    private volatile Boolean isConnected = false;

    /**
     * 跟机器建立连接
     * @param host
     * @param port
     * @throws Exception
     */
    public void connect(String host, int port) throws Exception {
        this.threadGroup  = new NioEventLoopGroup();

        this.client = new Bootstrap();

        client.group(threadGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(4096, delimiter));
                        socketChannel.pipeline().addLast(new ImClientHandler(ImClient.this));
                    }
                });

        ChannelFuture channelFuture = client.connect(host, port); // 尝试发起连接
        System.out.println("发起对TCP接入系统的连接......");

        channelFuture.addListener(new ChannelFutureListener() { // 给异步化的连接请求加入监听器
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()) {
                    socketChannel = (SocketChannel) channelFuture.channel();
                    System.out.println("已经跟TCP接入系统建立连接，TCP接入系统地址为：" + socketChannel);
                } else {
                    channelFuture.channel().close();
                    threadGroup.shutdownGracefully();
                }
            }
        });

        channelFuture.sync();
    }

    public void reconnect() throws Exception {
        // 重新调用iplist服务获取一个其他的接入系统的实例的地址
        String uid = "";
        String token = "";
        String host = "";
        int port = -1;

        connect(host, port);
        authenticate(uid, token);
    }

    /**
     * 发起token认证
     * @param uid
     * @param token
     * @throws Exception
     */
    public void authenticate(String uid, String token) throws Exception {
        // 封装认证请求的消息体
        AuthenticateRequestProto.AuthenticateRequest.Builder builder =
                AuthenticateRequestProto.AuthenticateRequest.newBuilder();
        builder.setUid(uid);
        builder.setToken(token);
        builder.setTimestamp(System.currentTimeMillis());
        AuthenticateRequestProto.AuthenticateRequest authenticateRequest = builder.build();

        // 封装一个完整的请求消息
        Request request = new Request(
                Constants.APP_SDK_VERSION_1,
                Constants.REQUEST_TYPE_AUTHENTICATE,
                Constants.SEQUENCE_DEFAULT,
                authenticateRequest.toByteArray());

        System.out.println("向TCP接入系统发起用户认证请求，请求大小为：" + authenticateRequest.toByteArray().length);
        
        // 将认证请求发送给TCP接入系统
        socketChannel.writeAndFlush(request.getBuffer());

        while(!isConnected) {
            Thread.sleep(100);
        }
    }

    /**
     * 关闭跟机器的连接
     * @throws Exception
     */
    public void close() throws Exception {
        this.socketChannel.close();
        this.threadGroup.shutdownGracefully();
    }

    /**
     * 发送单聊消息
     * @param senderId
     * @param receiverId
     * @param content
     */
    public void sendMessage(String senderId, String receiverId, String content) {
        MessageSendRequestProto.MessageSendRequest.Builder builder =
                MessageSendRequestProto.MessageSendRequest.newBuilder();
        builder.setSenderId(senderId);
        builder.setReceiverId(receiverId);
        builder.setContent(content);
        MessageSendRequestProto.MessageSendRequest messageSendRequest = builder.build();

        Request request = new Request(
                Constants.APP_SDK_VERSION_1,
                Constants.REQUEST_TYPE_SEND_MESSAGE,
                Constants.SEQUENCE_DEFAULT,
                messageSendRequest.toByteArray()
        );

        System.out.println("客户端向接入系统发送一条单聊消息，" + messageSendRequest);

        socketChannel.writeAndFlush(request.getBuffer());
    }

    /**
     * 设置为已经建立好连接
     */
    public void connected() {
        this.isConnected = true;
    }

}
