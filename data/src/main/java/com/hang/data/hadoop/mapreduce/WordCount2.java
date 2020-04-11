package com.hang.data.hadoop.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * @author hangs.zhang
 * @date 19-5-7
 * *****************
 * function: MapReduce 官方入门教程 使用ToolRunner完成
 */
public class WordCount2 extends Configured implements Tool {

    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

        private final static IntWritable ONE = new IntWritable(1);

        private Text word = new Text();

        /**
         * 在map被生成时调用
         */
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            System.out.println("init");
            super.setup(context);
        }

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            while (itr.hasMoreTokens()) {
                word.set(itr.nextToken());
                context.write(word, ONE);
            }
        }
    }

    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        private IntWritable result = new IntWritable();

        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static class WordLengthPartitioner extends Partitioner<Text, IntWritable> {

        private int LENGTH = 5;

        @Override
        public int getPartition(Text key, IntWritable value, int numPartitions) {
            if (key.getLength() <= LENGTH) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = new Configuration();
        // 如果目录存在 清理
        Path path = new Path(args[1]);
        FileSystem fileSystem = FileSystem.get(conf);
        if (fileSystem.exists(path)) {
            fileSystem.delete(path, true);
            System.out.println("文件已经存在 删除");
        }

        conf.setBoolean("mapreduce.job.ubertask.enable", true);
        Job job = Job.getInstance(conf, "word count 2");
        // 设置job的处理类
        job.setJarByClass(WordCount2.class);
        // map参数设置
        job.setMapperClass(TokenizerMapper.class);
        // combiner 其实就是IntSumReducer 因为逻辑一致
        job.setCombinerClass(IntSumReducer.class);
        // reduce参数设置
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        // partition相关参数设置
        job.setPartitionerClass(WordLengthPartitioner.class);
        // 设置两个reducer 每个分区一个 可以观察输出结果的文件 有两个part-r文件
        job.setNumReduceTasks(2);
        // 路径设置
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        // 提交之后，此时客户端代码就执行完毕，退出
        /// job.submit();

        // 等集群返回结果在退出
        boolean res = job.waitForCompletion(true);
        if (!res) {
            System.err.println("任务执行失败");
        }
        return res ? 0 : -1;
    }

    /**
     * 定义所有Driver：封装MapReduce作业的所有信息
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 自动快速地使用缺省Log4j环境
        BasicConfigurator.configure();
        int status = ToolRunner.run(new WordCount2(), args);
        System.exit(status);
    }
}
