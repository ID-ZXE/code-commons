package com.hang.data.flink.datasource;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/**
 * @author hangs.zhang
 * @date 2019/10/28 23:17
 * *****************
 * function: 从kafka接收数据
 */
public class KafkaDataSource {

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("zookeeper.connect", "127.0.0.1:2181");
        properties.setProperty("group.id", "flink");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> streamSource = env.addSource(new FlinkKafkaConsumer<>("test", new SimpleStringSchema(), properties));
        DataStream<String> stream = streamSource.map(new MapFunction<String, String>() {
            @Override
            public String map(String s) throws Exception {
                return s.toUpperCase();
            }
        }).filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String str) throws Exception {
                return str.length() > 5;
            }
        });

        stream.print();
        env.execute();
    }

}
