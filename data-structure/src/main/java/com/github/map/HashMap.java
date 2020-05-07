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

    private transient int size;

    private transient int modCount;

    private final float loadFactor;

    private int threshold;

    private Node<K, V>[] tables;

    public HashMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public HashMap(int initialCapacity) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.size = 0;
        this.tables = new Node[tableSizeFor(initialCapacity)];
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
            if (o instanceof Map.Entry) {
                java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>) o;
                return Objects.equals(key, e.getKey()) && Objects.equals(value, e.getValue());
            }
            return false;
        }

    }

    /**
     * int 32位
     * ^ 异或
     * hash 是由键的 hashCode产生。计算余数时，由于n（数组长度）比较小，hash只有低位参与了计算，高位的计算可以认为是无效的。
     * 这样导致了计算结果只与低位信息有关，高位数据没发挥作用。为了处理这个缺陷，我们可以上图中的hash高16位数据与低16位数据进行异或运算，
     * 即 hash ^ (hash >>> 16) 通过这种方式，让高位数据与低位数据进行异或，以此加大低位信息的随机性，变相的让高位数据参与到计算中。
     */
    private static int hash(Object key) {
        int h;
        // 使得高位参与运算
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

    /**
     * 大于输入参数且最近的2的整数次幂的数
     * >>> 移位
     */
    private int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= Integer.MAX_VALUE) ? Integer.MAX_VALUE : n + 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V get(K k) {
        int hash = hash(k);
        int index = indexFor(hash, tables.length);

        if (Objects.isNull(tables[index])) return null;
        Node<K, V> node = getNode(tables[index], k);
        if (Objects.isNull(node)) return null;
        return node.getValue();
    }

    @Override
    public V put(K k, V v) {
        ensureCapacity();
        int hash = hash(k);
        int index = indexFor(hash, tables.length);
        Node<K, V> oldNode;
        Node<K, V> newNode = new Node<>(hash, k, v, null);
        oldNode = putVal(tables, newNode, index);
        if (Objects.isNull(oldNode)) size++;

        return oldNode == null ? null : oldNode.value;
    }

    @Override
    public V remove(K k) {
        int hash = hash(k);
        int index = indexFor(hash, tables.length);
        Node<K, V> head = tables[index];

        if (Objects.isNull(head)) return null;
        if (Objects.isNull(head.next)) {
            size--;
            tables[index] = null;
            return head.getValue();
        }

        Node<K, V> node = head;
        if (node.key.equals(k)) {
            tables[index] = node.next;
            size--;
            return node.getValue();
        }
        Node<K, V> prev = head;
        while (node != null) {
            if (node.key.equals(k)) {
                prev.next = node.next;
                size--;
                return node.getValue();
            }
            prev = node;
            node = node.next;
        }
        return null;
    }

    // 尾部插入
    private Node<K, V> replaceOrInsert(Node<K, V> head, Node<K, V> newNode) {
        if (head == null) throw new RuntimeException();
        Node<K, V> node = head;
        Node<K, V> tail = head;
        while (node != null) {
            if (node.key.equals(newNode.key)) {
                Node<K, V> oldNode = new Node<>(node.hash, node.key, node.value, null);
                node.setValue(newNode.getValue());
                return oldNode;
            }
            tail = node;
            node = node.next;
        }
        tail.next = newNode;
        return null;
    }

    private Node<K, V> getNode(Node<K, V> head, K k) {
        Node<K, V> node = head;
        while (node != null) {
            if (node.key.equals(k)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    private void ensureCapacity() {
        if (size > tables.length * loadFactor) {
            Node<K, V>[] newTables = new Node[(tables.length << 1)];
            for (Node<K, V> node : tables) {
                if (!Objects.isNull(node)) {
                    while (node != null) {
                        putVal(newTables, new Node(node.hash, node.key, node.value, null), indexFor(node.hash, newTables.length));
                        node = node.next;
                    }
                }
            }
            tables = newTables;
        }
    }

    private Node<K, V> putVal(Node[] tables, Node<K, V> node, int index) {
        if (Objects.isNull(tables[index])) {
            tables[index] = node;
            return null;
        } else {
            return replaceOrInsert(tables[index], node);
        }
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

}
