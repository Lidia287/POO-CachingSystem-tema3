package cachingSystem.classes;

import java.util.LinkedHashMap;
import java.util.Map;

import cachingSystem.interfaces.Cache;
import dataStructures.classes.Pair;

/**
 * The FIFOCache class should be considered a blackbox. All you need is its API!
 */
public class FIFOCache<K, V> implements Cache<K, V> {

    private LinkedHashMap<K, V> cache;

    public FIFOCache() {
        cache = new LinkedHashMap<>();
    }


    /**
     * Get the value linked to the specified key from the cache.
     *
     * @param key
     * @return value
     */
    @Override
    public V get(final K key) {
        return cache.get(key);
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
        cache.put(key, value);
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
        return cache.remove(key);
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

        Map.Entry<K, V> eldest = cache.entrySet().iterator().next();

        return new Pair<K, V>(eldest.getKey(), eldest.getValue());
    }
}
