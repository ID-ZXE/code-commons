package com.github.mq.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author hangs.zhang
 * @date 2020/03/29 15:24
 * *****************
 * function:
 */
public class RocketConsumerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-group");
        consumer.setNamesrvAddr(RocketMQCommon.NAMES_SRV_ADDR);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("test-topic", "*");

        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                msgs.forEach(msg -> LOGGER.info("topic:{},tags:{},data:{},msgId:{}", msg.getTopic(), msg.getTags(), new String(msg.getBody(), StandardCharsets.UTF_8), msg.getMsgId()));
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        LOGGER.info("Consumer Started");
    }

}
