package com.github.redis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author hangs.zhang
 * @date 2019/3/12.
 * *****************
 * function:
 */
public class JedisTest {

    public static void main(String[] args) {
        Jedis jedis = getConnection();
        jedis.set("jedis:key", "jedis:value");
        closeConnection(jedis);
    }

    private static Jedis getConnection() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        JedisPool pool = new JedisPool(config, "127.0.0.1", 6379, 1000, "zhanghang", false);
        return pool.getResource();
    }

    private static void closeConnection(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
