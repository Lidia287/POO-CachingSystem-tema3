package cachingSystem.classes;

import java.sql.Timestamp;



import java.util.HashMap;

import cachingSystem.interfaces.CacheStalePolicy;
import dataStructures.classes.Node;
import dataStructures.classes.Pair;
import observerPattern.interfaces.CacheListener;

/**
 * The TimeAwareCache offers the same functionality as the LRUCache, but also
 * stores a timestamp for each element. The timestamp is updated after each get
 * / put operation for a key. This functionality allows for time based cache
 * stale policies (e.g. removing entries that are older than 1 second).
 */
public class TimeAwareCache<K, V> extends LRUCache<K, V> {

    protected HashMap<K, Timestamp> timestamps = new HashMap<>();

    /**
     * Adds a timestamp to the corresponding key in the hashmap.
     *
     * @param key
     */
    public void addTimestamps(final K key) {

        Timestamp t = new Timestamp(System.currentTimeMillis());

        timestamps.put(key, t);
    }

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

            int size2 = cache.size();

            for (int i = 0; i < cache.size(); i++) {
                clearStaleEntries();
            }

            int size3 = cache.size();

            if (size3 < size2) {

                while (size2 > size3) {
                    list.removeLast();
                    size2--;
                }

            }

            broadcastListener.onHit(key);
            addTimestamps(key);

            if (this.cache.get(key) != null) {

                Node<K, V> node = this.cache.get(key);
                this.list.moveFirst(node);
                Node<K, V> node2 = this.list.moveFirst(node);
                cache.put(key, node2);

            } else {
                broadcastListener.onMiss(key);

            }

            return this.cache.get(key).getValue();

        } else {


            CacheListener<K, V> broadcastListener = getListener();
            int size2 = cache.size();

            for (int i = 0; i < cache.size(); i++) {
                clearStaleEntries();
            }

            int size3 = cache.size();

            if (size3 < size2) {

                while (size2 > size3) {
                    list.removeLast();
                    size2--;
                }

            }

            broadcastListener.onHit(key);
            broadcastListener.onMiss(key);

            addTimestamps(key);

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
        addTimestamps(key);

        // the key is in the cache
        if (this.cache.containsKey(key)) {

            Node<K, V> node = this.cache.get(key);
            node.setValue(value);
            this.list.moveFirst(node);
            cache.put(key, node);

            int size2 = cache.size();

            for (int i = 0; i < cache.size(); i++) {
                clearStaleEntries();
            }

            int size3 = cache.size();

            if (size3 < size2) {

                while (size2 > size3) {
                    list.removeLast();
                    size2--;
                }

            }

            return;
        }

        // New entry to be put in the cache
        Node<K, V> node = new Node();
        node.setKey(key);
        node.setValue(value);
        Node<K, V> node2 = this.list.add(node);

        this.cache.put(key, node2);

        int size2 = cache.size();

        for (int i = 0; i < cache.size(); i++) {
            clearStaleEntries();
        }

        int size3 = cache.size();

        if (size3 < size2) {

            while (size2 > size3) {
                list.removeLast();
                size2--;
            }

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

    /**
     * Get the timestamp associated with a key, or null if the key is not stored
     * in the cache.
     *
     * @param key
     *            the key
     * @return the timestamp, or null
     */
    public Timestamp getTimestampOfKey(final K key) {

        return timestamps.get(key);

    }

    /**
     * Set a cache stale policy that should remove all elements older
     * than @millisToExpire milliseconds. This is a convenience method for
     * setting a time based policy for the cache.
     *
     * @param millisToExpire
     *            the expiration time, in milliseconds
     */
    public void setExpirePolicy(final long millisToExpire) {

        setStalePolicy(new CacheStalePolicy<K, V>() {
            @Override
            public boolean shouldRemoveEldestEntry(final Pair<K, V> entry) {
                Timestamp t = new Timestamp(System.currentTimeMillis());
                return (t.getTime() - getTimestampOfKey(entry.getKey()).getTime() > millisToExpire);
            }
        });

    }

}
