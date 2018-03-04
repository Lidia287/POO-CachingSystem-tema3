package cachingSystem.classes;

import java.util.HashMap;


import dataStructures.classes.DoublyLinkedList;
import dataStructures.classes.Node;
import dataStructures.classes.Pair;
import observerPattern.interfaces.CacheListener;

/**
 * This cache is very similar to the FIFOCache, but guarantees O(1) complexity
 * for the get, put and remove operations.
 */
public class LRUCache<K, V> extends ObservableCache<K, V> {


    protected HashMap<K, Node<K, V>> cache = new HashMap<>();

    protected DoublyLinkedList<K, V> list = new DoublyLinkedList<>();

    /**
     * Get the value linked to the specified key from the cache.
     *
     * @param key
     * @return value
     */
    @Override
    public V get(final K key) {


        if (this.cache.containsKey(key)) {

            CacheListener<K, V> broadcastListener = getListener();

            broadcastListener.onHit(key);


            Node<K, V> node = this.cache.get(key); // get the node from cache
            Node<K, V> node2 = this.list.moveFirst(node); // move it first in the list
            cache.put(key, node2); //  put in the cache the node that resulted from the
                                    // moveFirst operations because it has the right prev and next


            return node.getValue();
        } else {

            CacheListener<K, V> broadcastListener = getListener();

            broadcastListener.onHit(key);
            broadcastListener.onMiss(key);

            Node<K, V> node = this.cache.get(key);
            Node<K, V> node2 = this.list.moveFirst(node);
            cache.put(key, node2);

            return node.getValue();

        }

    }

    /**
     * Put an element into the cache.
     *
     * @param key
     * @param value
     * @return nothing
     */
    @Override
    public void put(final K key, final V value) {

        CacheListener<K, V> broadcastListener = getListener();

        broadcastListener.onPut(key, value);

        // the key is in the cache
        if (this.cache.containsKey(key)) {

            Node<K, V> node = this.cache.get(key);
            node.setValue(value);
            this.list.moveFirst(node);
            cache.put(key, node);
            return;
        }

        // New entry to be put in the cache
        Node<K, V> node = new Node();
        node.setKey(key);
        node.setValue(value);
        Node<K, V> node2 = this.list.add(node);

        this.cache.put(key, node2);

        int size2 = cache.size();

        clearStaleEntries();

        int size3 = cache.size();
//if the size has been decreased , i remove the last element of the linked list
        if (size3 < size2) {

            list.removeLast();

        }

    }

    /**
     * Get the size of the cache.
     * @return int
     */
    @Override
    public int size() {
        return cache.size();
    }

    /**
     * Checks if the cache is empty.
     * @return boolean
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Removes an element from the cache.
     * @param key
     * @return value
     */
    @Override
    public V remove(final K key) {
        return cache.remove(key).getValue();
    }

    /**
     * clearAll() method.
     * @return nothing
     */
    @Override
    public void clearAll() {
        cache.clear();
    }

    /**
     * Return the eldestEntry that will be removed if the size is exceeded.
     * @return Pair<K, V>
     */
    @Override
    public Pair<K, V> getEldestEntry() {
        if (isEmpty()) {
            return null;
        }

        Pair<K, V> pair = list.getLRU();

        return pair;

    }

}
