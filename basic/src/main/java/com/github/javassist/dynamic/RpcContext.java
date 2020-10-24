package com.github.javassist.dynamic;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author hangs.zhang
 * @date 2020/05/04 18:46
 * *****************
 * function:
 */
@Data
public class RpcContext {

    private AppContext appContext;

    private HashMap<Integer, Method> methodMap = new HashMap<>();

    private HashMap<Integer, DynamicInvoker> dynamicInvokerMap = new HashMap<>();

    public Method getMethod(Integer id) {
        return methodMap.get(id);
    }

    public void addMethod(Integer id, Method method) {
        methodMap.put(id, method);
    }

    public AppContext getApplicationContext() {
        return appContext;
    }

    public void putDynamicInvoker(Integer id, DynamicInvoker dynamicInvoker) {
        dynamicInvokerMap.put(id, dynamicInvoker);
    }

    public DynamicInvoker getDynamicInvoker(Integer id) {
        return dynamicInvokerMap.get(id);
    }

}
