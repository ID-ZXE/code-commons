package com.github.list;

/**
 * @author hangs.zhang
 * @date 2020/05/07 00:34
 * *****************
 * function:
 */
public interface List<T> {

    int size();

    boolean isEmpty();

    void add(T t);

    T add(int index, T t);

    T remove(int index);

    T get(int index);

}
