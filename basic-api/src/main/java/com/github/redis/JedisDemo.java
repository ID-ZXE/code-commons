package com.github.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author hangs.zhang
 * @date 2019/3/12.
 * *****************
 * function:
 */
public class JedisDemo {

    public static void main(String[] args) {
        Jedis jedis = getJedisConnection();
        jedis.set("jedis:key", "jedis:value");
        closeJedisConnection(jedis);
    }

    private static Jedis getJedisConnection() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        JedisPool pool = new JedisPool(config, "127.0.0.1", 6379, 1000, "redis", false);
        return pool.getResource();
    }

    private static void closeJedisConnection(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
