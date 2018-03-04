package cachingSystem.classes;



import dataStructures.classes.Pair;

import observerPattern.interfaces.CacheListener;

/**
 * Class that adapts the FIFOCache class to the ObservableCache abstract class.
 */
public class ObservableFIFOCache<K, V> extends ObservableCache<K, V> {


    // composition
    private FIFOCache<K, V> fifoCache = new FIFOCache<K, V>();

    /**
     * Get the value linked to the specified key from the cache.
     *
     * @param key
     * @return value
     */
    @Override
    public V get(final K key) {
        if (fifoCache.get(key) != null) {

            CacheListener<K, V> broadcastListener = getListener();

            broadcastListener.onHit(key);

            return fifoCache.get(key);
        } else {


            CacheListener<K, V> broadcastListener = getListener();

            broadcastListener.onHit(key);
            broadcastListener.onMiss(key);

            return fifoCache.get(key);

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


        fifoCache.put(key, value);

        clearStaleEntries();
    }

    /**
     * Get the size of the cache.
     * @return int
     */
    @Override
    public int size() {
        return fifoCache.size();
    }

    /**
     * Checks if the cache is empty.
     * @return boolean
     */
    @Override
    public boolean isEmpty() {
        return fifoCache.isEmpty();
    }

    /**
     * Removes an element from the cache.
     * @param key
     * @return value
     */
    @Override
    public V remove(final K key) {
        return fifoCache.remove(key);
    }

    /**
     * clearAll() method.
     * @return nothing
     */
    @Override
    public void clearAll() {
        fifoCache.clearAll();
    }

    /**
     * Return the eldestEntry that will be removed if the size is exceeded.
     * @return Pair<K, V>
     */
    @Override
    public Pair<K, V> getEldestEntry() {
        return fifoCache.getEldestEntry();
    }

}
