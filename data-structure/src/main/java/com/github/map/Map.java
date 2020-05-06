package com.github.map;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * @author hangs.zhang
 * @date 2020/5/6 下午1:08
 * *********************
 * function:
 */
public interface Map<K, V> {

    int size();

    boolean isEmpty();

    V get(K k);

    void put(K k, V v);

    void putAll(Map<? extends K, ? extends V> m);

    boolean containsKey(K k);

    boolean containsValue(V v);

    void clear();

    Set<K> keySet();

    Collection<V> values();

    Set<Entry<K, V>> entrySet();

    V remove(Object key);

    default boolean remove(K key, V value) {
        V curValue = get(key);
        if (!Objects.equals(curValue, value) ||
                (curValue == null && !containsKey(key))) {
            return false;
        }
        remove(key);
        return true;
    }

    default V getOrDefault(K key, V defaultValue) {
        V v;
        return (((v = get(key)) != null) || containsKey(key)) ? v : defaultValue;
    }

    interface Entry<K, V> {

        K getKey();

        V getValue();

        V setValue(V value);

        boolean equals(Object o);

        int hashCode();

    }
}
