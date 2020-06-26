package com.dfdx.im.sdk.android;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

// 如果你的APP要跟一台机器建立一个连接
// 此时就可以新建一个ImClient，这个Client就代表跟机器的一个连接就可以了
public class ImClient {

    // 代表的是Netty客户端中的线程池
    private EventLoopGroup threadGroup;
    // 代表的是Netty客户端
    private Bootstrap client;
    // 代表的是客户端APP跟TCP接入系统的某台机器的长连接
    private SocketChannel socketChannel;

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
                        //这两行处理粘包
                        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));

                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new ImClientHandler());
                    }
                });

        System.out.println("完成Netty客户端的配置");

        ChannelFuture channelFuture = client.connect(host, port); // 尝试发起连接
        System.out.println("发起对TCP接入系统的连接");

        channelFuture.addListener(new ChannelFutureListener() { // 给异步化的连接请求加入监听器
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()) {
                    socketChannel = (SocketChannel) channelFuture.channel();
                    System.out.println("跟TCP接入系统完成长连接的建立");
                } else {
                    channelFuture.channel().close();
                    threadGroup.shutdownGracefully();
                }
            }
        });
        //注意：如果没有这个阻塞等待，则执行下面的发送数据时socketChannel可能还没有赋值（在上面的监听器中赋值），会出现空指针异常
        channelFuture.sync();
    }

    /**
     * 发起token认证
     * @param userId
     * @param token
     * @throws Exception
     */
    public void authenticate(String userId, String token) throws Exception {
        //结尾特殊符号粘包
        byte[] messageBytes = ("发起用户认证|" + userId + "|" + token + "$_").getBytes();
        ByteBuf messageBuffer = Unpooled.buffer(messageBytes.length);
        messageBuffer.writeBytes(messageBytes);
        socketChannel.writeAndFlush(messageBuffer);

        System.out.println("向TCP接入系统发起用户认证请求");
    }

    /**
     * 向机器发送消息过去
     * @param message
     * @throws Exception
     */
    public void send(String userId, String message) throws Exception {
        byte[] messageBytes = (message + "|" + userId + "$_").getBytes();
        ByteBuf messageBuffer = Unpooled.buffer(messageBytes.length);
        messageBuffer.writeBytes(messageBytes);
        socketChannel.isWritable();
        socketChannel.writeAndFlush(messageBuffer);

        System.out.println("向TCP接入系统发送第一条消息，推送给test002用户");
    }

    /**
     * 关闭跟机器的连接
     * @throws Exception
     */
    public void close() throws Exception {
        this.socketChannel.close();
        this.threadGroup.shutdownGracefully();
    }

}
