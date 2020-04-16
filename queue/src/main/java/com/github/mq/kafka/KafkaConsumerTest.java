package com.github.mq.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @author hangs.zhang
 * @date 2019/10/27 15:49
 * *****************
 * function:
 */
@Slf4j
public class KafkaConsumerTest {

    public static void main(String[] args) {
        // 创建消费者
        Properties config = KafkaCommon.createConsumerProperties("test.group");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(config);

        // 订阅主题
        kafkaConsumer.subscribe(Collections.singletonList("test"));

        // 轮训
        try {
            while (true) {
                // poll的参数是一个超时时间, 不管有没有数据都返回
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100L));
                for (ConsumerRecord<String, String> record : records) {
                    log.info("topic:{}, partition:{}, offset:{}, key:{}, value:{}",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value());

                    kafkaConsumer.commitSync();
                }
            }
        } finally {
            kafkaConsumer.close();
        }
    }

}
