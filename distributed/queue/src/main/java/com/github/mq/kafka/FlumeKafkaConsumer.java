package com.github.mq.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author hangs.zhang
 * @date 2020/3/24 下午7:52
 * *********************
 * function: 消费flume中的数据
 */
@Slf4j
public class FlumeKafkaConsumer {

    public static void main(String[] args) {
        Properties config = KafkaCommon.createConsumerProperties("flume-schedule");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(config);
        // 订阅主题
        kafkaConsumer.subscribe(Collections.singletonList("task.log"));

        Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();

        // 轮训
        try {
            while (true) {
                // poll的参数是一个超时时间, 不管有没有数据都返回
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100L));
                for (ConsumerRecord<String, String> record : records) {
                    log.info("topic:{}, partition:{}, offset:{}, key:{}, value:{}",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value());
                    // 手动设置offset
                    offsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1));
                    kafkaConsumer.commitSync(offsets);
                }
                kafkaConsumer.commitSync();
            }
        } finally {
            kafkaConsumer.close();
        }
    }

}
