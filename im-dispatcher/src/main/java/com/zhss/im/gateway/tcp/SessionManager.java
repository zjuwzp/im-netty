package com.zhss.im.gateway.tcp;

import io.netty.channel.socket.SocketChannel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理客户端的组件
 */
public class SessionManager {

    private SessionManager() {

    }

    /**
     * 单例内部类
     */
    static class Singleton {

        public static SessionManager instance = new SessionManager();

    }

    /**
     * 获取单例
     * @return
     */
    public static SessionManager getInstance() {
        return Singleton.instance;
    }

    /**
     * 存储uid到客户端连接的映射
     */
    private ConcurrentHashMap<String, SocketChannel> sessions = new ConcurrentHashMap<String, SocketChannel>();
    /**
     * 存储channelId到uid的映射
     */
    private ConcurrentHashMap<String, String> channelId2uid = new ConcurrentHashMap<String, String>();

    /**
     * 添加一个建立好连接的客户端
     * @param uid
     * @param socketChannel
     */
    public void addSession(String uid, SocketChannel socketChannel) {
        String channelId = socketChannel.remoteAddress().getHostName() + ":"
                + socketChannel.remoteAddress().getPort();
        channelId2uid.put(channelId, uid);
        sessions.put(uid, socketChannel);
        System.out.println("设置本地Session：" + sessions);
    }

    /**
     * 判断认证过的客户端连接是否存在
     * @param uid
     * @return
     */
    public Boolean isConnected(String uid) {
        return sessions.containsKey(uid);
    }

    /**
     * 根据用户id获取对应的客户端连接
     * @param uid
     * @return
     */
    public SocketChannel getSession(String uid) {
        return sessions.get(uid);
    }

    /**
     * 删除一个客户端的连接
     * @param socketChannel
     */
    public void removeSession(SocketChannel socketChannel) {
        String channelId = socketChannel.remoteAddress().getHostName() + ":"
                + socketChannel.remoteAddress().getPort();
        String uid = channelId2uid.get(channelId);
        channelId2uid.remove(socketChannel.id().asLongText());
        sessions.remove(uid);
    }

}
