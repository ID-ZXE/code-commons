package com.github.mq.kafka;

import java.util.Properties;

/**
 * @author hangs.zhang
 * @date 2020/3/24 下午7:56
 * *********************
 * function:
 */
public class KafkaCommon {

    private static final String BOOT_STRAP_SERVERS = "middleware-server-1:9092";

    public static Properties createConsumerProperties(String groupName) {
        Properties config = new Properties();
        config.put("bootstrap.servers", BOOT_STRAP_SERVERS);
        // 消费者群组
        config.put("group.id", groupName);
        config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // 关闭自动提交偏移量
        config.put("auto.commit.offset", "false");
        return config;
    }

    public static Properties createProducerProperties() {
        Properties config = new Properties();
        config.put("bootstrap.servers", BOOT_STRAP_SERVERS);
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return config;
    }

}
