package com.zhss.im.dispathcer;

import redis.clients.jedis.Jedis;

public class JedisTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        jedis.set("session_110", "{'token':'token_110','timestamp':3342222,'isAuthenticated':'true','authenticateTimestamp':2342344,'gatewayId':'192.168.31.190:80090'}");
        System.out.println(jedis.get("session_110"));
    }

}
