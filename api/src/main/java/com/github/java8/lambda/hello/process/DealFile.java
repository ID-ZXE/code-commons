package com.github.java8.lambda.hello.process;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author hangs.zhang
 * @date 2018/11/3
 * *****************
 * function:
 */
public class DealFile {

    public static String processFile(String file, BufferedReaderProcessor processor) throws IOException {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(file))) {
            return processor.process(bufferedReader);
        }
    }

    @Test
    public void test() {
        try {
            String line = processFile("C:\\file\\script\\python\\test\\lambda.py", bufferedReader -> bufferedReader.readLine());
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
