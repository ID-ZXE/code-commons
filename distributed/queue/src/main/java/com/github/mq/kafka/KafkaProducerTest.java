package com.github.mq.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;

import java.util.Objects;
import java.util.Properties;

/**
 * @author hangs.zhang
 * @date 2019/10/27 15:09
 * *****************
 * function:
 */
@Slf4j
public class KafkaProducerTest {

    public static void main(String[] args) throws InterruptedException {
        // 生成producer
        Properties config = KafkaCommon.createProducerProperties();
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(config);

        // 创建消息
        ProducerRecord<String, String> record = new ProducerRecord<>("test", "key", "this is message");

        // 发送
        // 同步发送
        try {
            kafkaProducer.send(record).get();
        } catch (Exception e) {
            log.error("send kafka message error", e);
        }

        // 异步发送
        kafkaProducer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (Objects.isNull(e)) {
                    log.info("发送完毕");
                } else {
                    log.error("发送异常");
                }
            }
        });

        Thread.sleep(10000);
    }

}
