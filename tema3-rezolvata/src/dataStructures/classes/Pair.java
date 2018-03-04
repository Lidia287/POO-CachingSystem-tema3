package dataStructures.classes;

/**
 * The Pair class is a convenient way of storing key-value pairs.
 *
 * @param <K>
 * @param <V>
 */
public class Pair<K, V> {
    private K key;
    private V value;

    public Pair(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * returns the key of the node.
     * @return K
     */
    public K getKey() {
        return key;
    }

    /**
     * sets the key of the node.
     * @param key
     * @return nothing
     */
    public void setKey(final K key) {
        this.key = key;
    }

    /**
     * returns the value of the node.
     * @return V
     */
    public V getValue() {
        return value;
    }

    /**
     * sets the value of the node.
     * @param value
     * @return nothing
     */
    public void setValue(final V value) {
        this.value = value;
    }
}
