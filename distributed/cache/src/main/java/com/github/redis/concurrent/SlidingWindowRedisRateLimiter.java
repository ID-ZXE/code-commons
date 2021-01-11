package com.github.redis.concurrent;

import com.github.redis.utils.RedisUtils;
import io.lettuce.core.ScriptOutputType;

import java.util.Objects;
import java.util.UUID;

/**
 * @author zhanghang
 * @date 2021/1/10 10:00 下午
 * *****************
 * function:
 */
public class SlidingWindowRedisRateLimiter extends RateLimiter {

    private static final String NEW_LINE = "\n";

    private static final String LUA_SCRIPT;

    // 越短越好
    private static final String PUSH_DATA = "VALUE";

    private static final long SUCCESS_VAL = -1;

    // 容错时间
    private static final long REDIS_NET_TIME = 3;

    static {
        StringBuilder sb = new StringBuilder();
        // -1表示成功, 因为无法区分llen为0是不存在了, 还是已经到0s了
        String returnSuccess = "return " + SUCCESS_VAL;

        // 数据定义
        sb.append("local key = KEYS[1]").append(NEW_LINE);
        sb.append("local limit = ARGV[1]").append(NEW_LINE);
        sb.append("local expireTime = ARGV[2]").append(NEW_LINE);
        sb.append("local pushData = ARGV[3]").append(NEW_LINE);
        sb.append("local permit = ARGV[4]").append(NEW_LINE);

        // len+permit大于limit
        sb.append("local len = redis.call('llen', key)").append(NEW_LINE);
        sb.append("if tonumber(len) + tonumber(permit) > tonumber(limit) then").append(NEW_LINE);
        sb.append("return redis.call('pttl', key)").append(NEW_LINE);
        sb.append("end").append(NEW_LINE);

        // 当key不存在时, 放入permit数量的数据, 然后设置过期时间
        sb.append("if tonumber(len) == 0 then").append(NEW_LINE);
        sb.append("for i = 0, permit - 1, 1 do").append(NEW_LINE);
        sb.append("redis.call('rpush', key, pushData)").append(NEW_LINE);
        sb.append("end").append(NEW_LINE);

        sb.append("redis.call('expire', key, expireTime)").append(NEW_LINE);
        sb.append(returnSuccess).append(NEW_LINE);
        sb.append("end").append(NEW_LINE);

        // key存在, 放入permit数量的数据
        sb.append("for i = 0, permit - 1, 1 do").append(NEW_LINE);
        sb.append("redis.call('rpush', key, pushData)").append(NEW_LINE);
        sb.append("end").append(NEW_LINE);
        sb.append(returnSuccess).append(NEW_LINE);
        LUA_SCRIPT = sb.toString();
    }

    public SlidingWindowRedisRateLimiter(int permitsPerSecond) {
        this(UUID.randomUUID().toString(), permitsPerSecond);
    }

    public SlidingWindowRedisRateLimiter(String name, int permitsPerSecond) {
        this.permitsPerSecond = permitsPerSecond;
        this.name = name;
        this.prefix = "S:RATE_LIMIT:";
    }

    private String getRedisKey() {
        return String.format("%s:%s", prefix, name);
    }

    @Override
    public long tryAcquire() {
        return tryAcquire(1);
    }

    @Override
    public long tryAcquire(int permits) {
        String[] keys = new String[]{getRedisKey()};
        String[] args = new String[]{String.valueOf(permitsPerSecond), String.valueOf(1), PUSH_DATA, String.valueOf(permits)};
        Long result = RedisUtils.eval(LUA_SCRIPT, keys, args, ScriptOutputType.INTEGER);
        if (Objects.equals(result, SUCCESS_VAL)) return 0;
        // 减去Redis可能得网络开销时间
        return result - REDIS_NET_TIME;
    }

}
