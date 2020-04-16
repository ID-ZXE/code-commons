package com.github.data.flink.datasource;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author hangs.zhang
 * @date 2019/10/27 22:52
 * *****************
 * function: 从枚举中获取数据源
 */
public class WordCount {

    private static final String SPLINTER = " ";

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
        DataStreamSource<String> elements = env.fromElements("zhang hang", "hang zhang", "xxx", "yyy", "zzz", "xxx", "yyy", "zzz");
        DataStream<Tuple2<String, Integer>> dataStream = elements.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                for (String word : s.trim().split(SPLINTER)) {
                    collector.collect(new Tuple2<String, Integer>(word.trim(), 1));
                }
            }
        }).filter(new FilterFunction<Tuple2<String, Integer>>() {
            @Override
            public boolean filter(Tuple2<String, Integer> tuple2) throws Exception {
                return StringUtils.isNotBlank(tuple2.f0);
            }
        }).keyBy(0).sum(1);

        dataStream.print();
        env.execute();
    }

}
