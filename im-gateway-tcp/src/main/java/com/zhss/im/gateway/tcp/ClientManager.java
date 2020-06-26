package com.zhss.im.gateway.tcp;

import io.netty.channel.socket.SocketChannel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理客户端的组件
 */
public class ClientManager {

    private ClientManager() {

    }

    /**
     * 单例内部类
     */
    static class Singleton {

        public static ClientManager instance = new ClientManager();

    }

    /**
     * 获取单例
     * @return
     */
    public static ClientManager getInstance() {
        return Singleton.instance;
    }

    /**
     * 存储uid到客户端连接的映射
     */
    private ConcurrentHashMap<String, SocketChannel> clients = new ConcurrentHashMap<String, SocketChannel>();
    /**
     * 存储channelId到uid的映射
     */
    private ConcurrentHashMap<String, String> channelId2uid = new ConcurrentHashMap<String, String>();

    /**
     * 添加一个建立好连接的客户端
     * @param uid
     * @param socketChannel
     */
    public void addClient(String uid, SocketChannel socketChannel) {
        channelId2uid.put(socketChannel.id().asLongText(), uid);
        clients.put(uid, socketChannel);
    }

    /**
     * 判断认证过的客户端连接是否存在
     * @param uid
     * @return
     */
    public Boolean isClientConnected(String uid) {
        return clients.containsKey(uid);
    }

    /**
     * 根据用户id获取对应的客户端连接
     * @param uid
     * @return
     */
    public SocketChannel getClient(String uid) {
        return clients.get(uid);
    }

    /**
     * 删除一个客户端的连接
     * @param socketChannel
     */
    public void removeChannel(SocketChannel socketChannel) {
        String uid = channelId2uid.get(socketChannel.id().asLongText());
        channelId2uid.remove(socketChannel.id().asLongText());
        clients.remove(uid);
    }

}
