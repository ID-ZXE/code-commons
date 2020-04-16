package com.github.annotation;

import java.lang.reflect.Method;

/**
 * @author hangs.zhang
 * @date 2018/7/28
 * *********************
 * function:
 */
public class ExecutorBean {
    private Object object;

    private Method method;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

}
