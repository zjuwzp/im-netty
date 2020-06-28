package com.zhss.im.dispathcer;

import redis.clients.jedis.Jedis;

/**
 * Jedis实例管理组件
 */
public class JedisManager {

    private JedisManager() {

    }

    private Jedis jedis = new Jedis("localhost");

    static class Singleton {

        static JedisManager instance = new JedisManager();

    }

    public static JedisManager getInstance() {
        return Singleton.instance;
    }

    public Jedis getJedis() {
        return jedis;
    }

}
