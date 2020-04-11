package com.hang.apache.commons.io;

import org.apache.commons.io.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * @author hangs.zhang
 * @date 2020/04/10 23:36
 * *****************
 * function: apache-commons-io包
 * IOUtils FileUtils FilenameUtils
 */
public class CommonsIOTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String dir = System.getProperty("user.dir");

    @Test
    public void toStringTest() throws IOException {
        File file = new File(dir, "/src/main/java/com/hang/bases/io/CommonsIOTest.java");
        String text = IOUtils.toString(new FileReader(file));
        LOGGER.info("text:{}", text);
    }

    @Test
    public void writeTest() throws IOException {
        String dir = System.getProperty("user.dir");
        File file = new File(dir, "temp");
        FileWriter writer = new FileWriter(file);
        String text = "text text text text text text";
        IOUtils.write(text, writer);
        // writer.flush();
        // close自动刷新
        writer.close();
    }

    @Test
    public void read() throws IOException {
        File file = new File(dir, "/src/main/java/com/hang/bases/io/CommonsIOTest.java");

        FileReader reader = new FileReader(file);
        List<String> lines = IOUtils.readLines(reader);
        lines.stream().limit(10).forEach(LOGGER::info);
        reader.close();

        LOGGER.info("********");

        FileReader reader2 = new FileReader(file);
        LineIterator iterator = IOUtils.lineIterator(reader2);
        while (iterator.hasNext()) {
            LOGGER.info(iterator.nextLine());
        }
        reader2.close();
        iterator.close();
    }

}
