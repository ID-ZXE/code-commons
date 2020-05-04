package com.github.javassist.dynamic;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2020/05/04 18:42
 * *****************
 * function:
 */
public class AppContext {

    private Map<String, Object> objs = Maps.newHashMap();

    public void setBean(String id, Object obj) {
        objs.put(id, obj);
    }

    public Object getBean(String id) {
        return objs.get(id);
    }

}
