package com.github.javassist;

import javassist.*;

import java.lang.reflect.Method;

/**
 * @author hangs.zhang
 * @date 2020/03/30 00:02
 * *****************
 * function:
 */
public class JavaassistTest {

    public static void main(String[] args) throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get("com.github.bases.javassist.Main");

        ctClass.addField(CtField.make("private String name;\n", ctClass));
        ctClass.addMethod(CtMethod.make("public void setName(java.lang.String name){this.name = name;}\n", ctClass));
        ctClass.addMethod(CtMethod.make("public java.lang.String getName(){return name;}\n", ctClass));
        Class<?> clazz = ctClass.toClass();
        Object obj = clazz.newInstance();
        Method setName = clazz.getMethod("setName", String.class);
        setName.invoke(obj, "hangs.zhang");
        Method getName = clazz.getMethod("getName");
        String name = (String) getName.invoke(obj);
        System.out.println(name);
    }

}
