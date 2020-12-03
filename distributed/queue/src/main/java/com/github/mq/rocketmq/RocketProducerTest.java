package com.github.mq.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 2020/03/29 15:24
 * *****************
 * function:
 */
public class RocketProducerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void sync() throws Exception {
        // 同步发送
        DefaultMQProducer producer = new DefaultMQProducer("test-group");
        producer.setNamesrvAddr(RocketMQCommon.NAMES_SRV_ADDR);
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message message = new Message("test-topic", "test-tag", "test-key",
                    String.format("hello rocketmq, i`m %d", i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(message);
            LOGGER.info("msgId:{},regionId:{}", sendResult.getMsgId(), sendResult.getRegionId());
        }
        producer.shutdown();
    }

    @Test
    public void async() throws Exception {
        // 异步发送
        DefaultMQProducer producer = new DefaultMQProducer("test-group");
        producer.setNamesrvAddr(RocketMQCommon.NAMES_SRV_ADDR);
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        for (int i = 0; i < 100; i++) {
            final int index = i;
            Message message = new Message("test-topic", "test-tag", "test-key",
                    String.format("hello rocketmq, i`m %d", i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    LOGGER.info("index:{} OK msgId:{}", index, sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    LOGGER.info("index:{} Exception", index, e);
                }
            });
        }
        Thread.sleep(1000);
        producer.shutdown();
    }

}
