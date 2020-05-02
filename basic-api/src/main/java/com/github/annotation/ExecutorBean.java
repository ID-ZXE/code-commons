package com.github.annotation;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author hangs.zhang
 * @date 2018/7/28
 * *********************
 * function:
 */
@Data
public class ExecutorBean {

    private Object object;

    private Method method;

}
