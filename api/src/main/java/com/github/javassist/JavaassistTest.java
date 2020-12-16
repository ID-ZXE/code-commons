package com.github.javassist;

import javassist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

/**
 * @author hangs.zhang
 * @date 2020/03/30 00:02
 * *****************
 * function:
 */
@SuppressWarnings("all")
public class JavaassistTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get("com.github.javassist.Template");

        ctClass.addField(CtField.make("private String name;\n", ctClass));
        ctClass.addMethod(CtMethod.make("public void setName(java.lang.String name){this.name = name;}\n", ctClass));
        ctClass.addMethod(CtMethod.make("public java.lang.String getName(){return name;}\n", ctClass));
        Class<?> clazz = ctClass.toClass();
        Object obj = clazz.newInstance();
        Method setName = clazz.getMethod("setName", String.class);
        setName.invoke(obj, "hangs.zhang");
        Method getName = clazz.getMethod("getName");
        String name = (String) getName.invoke(obj);
        LOGGER.info("name : {}", name);
    }

}
