package com.github.common.extension;

import java.lang.annotation.*;


// 某个接口上加上@SPI注解后，表明该接口为可扩展接口
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {

    /**
     * default extension name
     */
    String value() default "";

}