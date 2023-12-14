package com.github.retransform;

import com.alibaba.deps.org.objectweb.asm.ClassReader;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.RandomAccessFile;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.*;

@Slf4j
public class RetransformAgent {

    private static List<String> paths = Lists.newArrayList();

    private static List<RetransformEntry> allRetransformEntries = Lists.newArrayList();

    public static void agentmain(String agentArgs, Instrumentation inst)
            throws UnmodifiableClassException {
        paths.add("/Users/zhanghang/Files/temp/FoobarController.class");
        paths.add("/Users/zhanghang/Files/temp/StatisticsAdvice.class");
        paths.add("/Users/zhanghang/Files/temp/EnvController.class");
        inst.addTransformer(new RetransformClassFileTransformer(allRetransformEntries), true);
        process(inst);
    }

    private static void process(Instrumentation inst) {
        Map<String, byte[]> bytesMap = new HashMap<>();
        for (String path : paths) {
            // 加载外部的class文件的字节流
            RandomAccessFile f = null;
            try {
                f = new RandomAccessFile(path, "r");
                final byte[] bytes = new byte[(int) f.length()];
                f.readFully(bytes);
                // 通过repackage-asm的依赖能够解析字节码获取类名
                final String clazzName = readClassName(bytes);
                System.out.println("read class file:" + clazzName);
                bytesMap.put(clazzName, bytes);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (Objects.nonNull(f)) {
                        f.close();
                    }
                } catch (Exception e) {
                    log.error("close io error", e);
                }
            }
        }

        List<Class<?>> classList = new ArrayList<Class<?>>();
        // 通过inst获取已加载的类并和指定重载的类进行比较，存在的才重新加载
        for (Class<?> clazz : inst.getAllLoadedClasses()) {

            if (bytesMap.containsKey(clazz.getName())) {
                RetransformEntry retransformEntry = new RetransformEntry(clazz.getName(), bytesMap.get(clazz.getName()));
                allRetransformEntries.add(retransformEntry);
                // 保存待重新加载的类
                classList.add(clazz);
            }
        }

        try {
            // 重新触发字节类重新加载
            inst.retransformClasses(classList.toArray(new Class[0]));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static String readClassName(final byte[] bytes) {
        return new ClassReader(bytes).getClassName().replace('/', '.');
    }

}
