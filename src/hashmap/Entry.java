package hashmap;

public class Entry<K, V> {
    private K key;
    private V value;

    public K getKey() { return this.key; }

    public V getValue() { return this.value; }

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
