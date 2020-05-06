package com.github.map;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * @author hangs.zhang
 * @date 2020/04/20 00:07
 * *****************
 * function: 参考JDK HashMap实现
 */
public class HashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    transient int size;

    transient int modCount;

    final float loadFactor;

    private Node<K, V>[] tables;

    public HashMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public HashMap(int size) {
        this.tables = new Node[size];
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    static class Node<K, V> implements Entry<K, V> {

        final int hash;

        final K key;

        V value;

        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public final K getKey() {
            return key;
        }

        @Override
        public final V getValue() {
            return value;
        }

        @Override
        public final String toString() {
            return key + "=" + value;
        }

        @Override
        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        @Override
        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof java.util.Map.Entry) {
                java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>) o;
                return Objects.equals(key, e.getKey()) && Objects.equals(value, e.getValue());
            }
            return false;
        }

    }

    /**
     * hash 是由键的 hashCode产生。计算余数时，由于n（数组长度）比较小，hash只有低4位参与了计算，高位的计算可以认为是无效的。
     * 这样导致了计算结果只与低位信息有关，高位数据没发挥作用。为了处理这个缺陷，我们可以上图中的hash高4位数据与低4位数据进行异或运算，
     * 即 hash ^ (hash >>> 16) 通过这种方式，让高位数据与低位数据进行异或，以此加大低位信息的随机性，变相的让高位数据参与到计算中。
     */
    private static int hash(Object key) {
        int h;
        // 高位参与运算
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * 当length总是2的n次方时, h & (length-1)运算等价于h%length. 因为2的n次方减一时,低位都是1
     * & 比 % 效率高, &运算, 都是1, 才是1
     * 以16为例,16-1=15,二进制为1111. 15-1=14,二进制位1110. 当有hash值过来做&运算时,如果是15-1会浪费最后一位的元素
     * 8 1000
     * 9 1001
     * 16-1 1111
     * 15-1 1110
     * <p>
     * 8 & 16-1 = 8
     * 9 & 16-1 = 9
     * 8 & 15-1 = 8
     * 8 & 15-1 = 8
     */
    private int indexFor(int h, int length) {
        return h & length - 1;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public V get(K k) {
        int hash = hash(k);
        int index = indexFor(hash, tables.length);
        return tables[index].value;
    }

    @Override
    public V put(K k, V v) {
        int hash = hash(k);
        int index = indexFor(hash, tables.length);
        Node<K, V> oldNode = tables[index];
        Node<K, V> node = new Node<K, V>(hash, k, v, null);
        if (Objects.isNull(tables[index])) {
            tables[index] = node;
        } else {

        }
        return oldNode == null ? null : oldNode.value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public boolean containsKey(K k) {
        return false;
    }

    @Override
    public boolean containsValue(V v) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

}
