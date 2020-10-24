package com.github.annotation;

import java.lang.annotation.*;

/**
 * @author hangs.zhang
 * @date 2018/7/28
 * *********************
 * function:
 */
// 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target(ElementType.TYPE)
// 定义注解的作用目标**作用范围字段、枚举的常量/方法
@Retention(RetentionPolicy.RUNTIME)
// 说明该注解将被包含在javadoc中
@Documented
public @interface Controller {

    String value() default "";

}

