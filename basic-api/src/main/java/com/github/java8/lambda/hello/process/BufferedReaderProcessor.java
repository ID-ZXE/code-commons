package com.github.java8.lambda.hello.process;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author hangs.zhang
 * @date 2018/11/3
 * *****************
 * function:
 */
@FunctionalInterface
public interface BufferedReaderProcessor {

    String process(BufferedReader bufferedReader) throws IOException;

}
