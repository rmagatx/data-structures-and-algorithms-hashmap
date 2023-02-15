package hashmap;

import java.util.ArrayList;
import java.util.Iterator;

interface Map<K, V> {
    int size();
    boolean isEmpty();
    void clear();
    V get(K key);
    V put(K key, V value);
    V remove(K key);
    Iterator<K> keys();
    Iterator<V> values();
}

public class HashMap<K, V> implements Map<K, V> {
    private final static int CAPACITY = 11;
    private final static double LOAD_FACTOR = 0.75;
    public Entry<K, V>[] table;
    private double loadFactor;
    private int threshold;
    private int size;
    private int placeHolderSize;

    public Entry<K, V>[] getTable() {
        return this.table;
    }

    public HashMap() {
        this(CAPACITY);
    }
    public HashMap(int initialCapacity) {
        this(initialCapacity, LOAD_FACTOR);
    }
    public HashMap(int initialCapacity, double loadFactor) {
        if (initialCapacity == 0 || initialCapacity < 0) {
            throw new IllegalArgumentException();
        }

        if (loadFactor < 0 || loadFactor > 1) {
            throw new IllegalArgumentException();
        }

        this.table = (Entry<K, V>[]) new Entry[initialCapacity];
        this.loadFactor = loadFactor;
    }

    public int size() { return this.size; }

    public boolean isEmpty() { return this.size == 0; }

    public void clear() {
        this.table = null;
        this.size = 0;
        this.placeHolderSize = 0;
    }

    private int findStartingBucket(K key) { return key.hashCode() % this.table.length; }

    public int getMatchingOrNextAvailableBucket(K key) {
        int index = findStartingBucket(key);

        while (this.table[index] != null && !this.table[index].getKey().equals(key)) {
            index++;

            if (index == this.table.length - 1) {
                index = 0;
            }
        }

        return index;
    }

    public V get(K key) {
        V value = null;

        if (!isEmpty()) {
            int index = getMatchingOrNextAvailableBucket(key);

            if (this.table[index] != null && this.table[index].getKey().equals(key)) {
                value = this.table[index].getValue();
            }
        }

        return value;
    }

    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }

        this.threshold = (int) (this.table.length * this.loadFactor);
        int index = getMatchingOrNextAvailableBucket(key);
        V oldBucket = null;

        if (this.table[index] == null) {
            if ((this.size + this.placeHolderSize + 1) == this.threshold) {
                rehash();
                index = getMatchingOrNextAvailableBucket(key);
            }

            this.table[index] = new Entry<>(key, value);
            this.size++;
        } else {
            oldBucket = this.table[index].getValue();
            this.table[index] = new Entry<>(key, value);
        }

        return oldBucket;
    }

    public V remove(K key) {
        if (key == null) {
            throw new NullPointerException();
        }

        int index = getMatchingOrNextAvailableBucket(key);
        V removedValue = null;

        if (this.table[index] != null && this.table[index].getKey().equals(key) && this.table[index].getValue() != null) {
            removedValue = this.table[index].getValue();
            this.table[index] = new Entry<>(this.table[index].getKey(), null);

            this.placeHolderSize++;
            this.size--;
        }

        return removedValue;
    }

    private int resize() {
        int size = (this.table.length * 2) + 1;

        for (int i = 2; i < 10; i++) {
            if (size % i == 0) {
                size += 2;
            }
        }

        return size;
    }

    public void rehash() {
        Entry<K, V>[] oldTable = this.table;
        this.table = (Entry<K, V>[]) new Entry[resize()];

        for (Entry<K, V> entry : oldTable) {
            if (entry != null && entry.getValue() != null) {
                int index = getMatchingOrNextAvailableBucket(entry.getKey());
                this.table[index] = new Entry<>(entry.getKey(), entry.getValue());
            }
        }
    }

    public Iterator<V> values() {
        ArrayList<V> arrayListValues = new ArrayList<>();

        for (Entry<K, V> entry : this.table) {
            if (entry != null && entry.getKey() != null) {
                arrayListValues.add(entry.getValue());
            }
        }

        return arrayListValues.iterator();
    }

    public Iterator<K> keys() {
        ArrayList<K> arrayListKeys = new ArrayList<>();

        for (Entry<K, V> entry : this.table) {
            if (entry != null && entry.getKey() != null) {
                arrayListKeys.add(entry.getKey());
            }
        }

        return arrayListKeys.iterator();
    }
}
