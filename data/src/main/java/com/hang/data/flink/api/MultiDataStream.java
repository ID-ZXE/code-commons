package com.hang.data.flink.api;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;
import org.apache.flink.util.Collector;
import org.junit.Test;

/**
 * @author hangs.zhang
 * @date 2019/10/29 下午1:37
 * *********************
 * function:
 */
public class MultiDataStream {

    @Test
    public void union() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source1 = env.fromElements("zh", "hz", "xx", "yy");
        DataStreamSource<String> source2 = env.fromElements("zh", "hz", "xx", "yy");
        DataStream<String> unionSource = source1.union(source2);
        unionSource.print();
        env.execute();
    }

    @Test
    public void connect() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source1 = env.fromElements("zh", "hz", "xx", "yy");
        DataStreamSource<Integer> source2 = env.fromElements(1, 2, 3);
        DataStream<String> connectSource = source1.connect(source2)
                .map(new CoMapFunction<String, Integer, String>() {
                    @Override
                    public String map1(String str) throws Exception {
                        return str.toUpperCase();
                    }

                    @Override
                    public String map2(Integer i) throws Exception {
                        return String.valueOf(i);
                    }
                });
        connectSource.print();

        env.execute();
    }

    @Test
    public void coFlatMap() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source1 = env.fromElements("zh", "hz", "xx", "yy");
        DataStreamSource<Integer> source2 = env.fromElements(1, 2, 3);
        source1.connect(source2).flatMap(new CoFlatMapFunction<String, Integer, String>() {
            @Override
            public void flatMap1(String value, Collector<String> out) throws Exception {
                out.collect(value.toUpperCase());
            }

            @Override
            public void flatMap2(Integer value, Collector<String> out) throws Exception {
                out.collect(String.valueOf(value));
            }
        }).print();

        env.execute();
    }

}
