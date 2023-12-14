package com.github.retransform;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.ListIterator;

public class RetransformClassFileTransformer implements ClassFileTransformer {

    List<RetransformEntry> allRetransformEntries;

    public RetransformClassFileTransformer(List<RetransformEntry> allRetransformEntries) {
        this.allRetransformEntries = allRetransformEntries;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if (className == null) {
            return null;
        }

        className = className.replace('/', '.');
        // 倒序，因为要执行的配置生效
        ListIterator<RetransformEntry> listIterator = allRetransformEntries
                .listIterator(allRetransformEntries.size());
        while (listIterator.hasPrevious()) {
            RetransformEntry retransformEntry = listIterator.previous();
            int id = retransformEntry.getId();
            // 判断类名是否一致
            boolean updateFlag = false;
            // 类名一致，则看是否要比较 loader，如果不需要比较 loader，则认为成功
            System.out.println(className);
            System.out.println(retransformEntry.getClassName());
            if (className.equals(retransformEntry.getClassName())) {
                updateFlag = true;
            }

            if (updateFlag) {
                System.out.println("reload class:" + className);
                retransformEntry.incTransformCount();
                // 返回待重新加载的字节码
                return retransformEntry.getBytes();
            }

        }
        return null;
    }

}
