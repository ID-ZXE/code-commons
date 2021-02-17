package com.github.redis.utils;

import io.lettuce.core.ScriptOutputType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author zhanghang
 * @date 2021/1/10 11:29 下午
 * *****************
 * function:
 */
public class RedisUtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String NEW_LINE = "\n";

    public String buildScript() {
        StringBuilder script = new StringBuilder();
        script.append("local key = KEYS[1]").append(NEW_LINE);
        script.append("local limit = ARGV[1]").append(NEW_LINE);
        script.append("local expireTime = ARGV[2]").append(NEW_LINE);
        script.append("local pushData = ARGV[3]").append(NEW_LINE);
        script.append("local permit = ARGV[4]").append(NEW_LINE);

        script.append("local len = redis.call('llen', key)").append(NEW_LINE);
        // len大于limit
        script.append("if tonumber(len) + tonumber(permit) > tonumber(limit) then").append(NEW_LINE);
        script.append("return redis.call('ttl', key)").append(NEW_LINE);
        script.append("end").append(NEW_LINE);

        // 当key不存在时, push一个数据进去, 然后设置过期时间
        script.append("if tonumber(len) == 0 then").append(NEW_LINE);
        // 放入permit数量的数据
        script.append("for i = 0, permit - 1, 1 do").append(NEW_LINE);
        script.append("redis.call('rpush', key, pushData)").append(NEW_LINE);
        script.append("end").append(NEW_LINE);

        script.append("redis.call('expire', key, expireTime)").append(NEW_LINE);
        script.append("return -1").append(NEW_LINE);
        script.append("end").append(NEW_LINE);

        // key存在, push一个数据进去
        // 放入permit数量的数据
        script.append("for i = 0, permit - 1, 1 do").append(NEW_LINE);
        script.append("redis.call('rpush', key, pushData)").append(NEW_LINE);
        script.append("end").append(NEW_LINE);
        script.append("return -1").append(NEW_LINE);

        return script.toString();
    }

    @Test
    public void testLua() throws InterruptedException {
        String script = buildScript();
        for (int i = 0; i < 100; i++) {
            LOGGER.info("result : {}", RedisUtils.eval(script, new String[]{"testLua"}, new String[]{"5", "20", "VALUE", "2"}, ScriptOutputType.INTEGER));
            Thread.sleep(20);
            // LOGGER.info("result : {}", RedisUtils.eval(buildScript(), new String[]{"testLua"}, new String[]{"3", "100", "VALUE"}));
        }
    }

}
