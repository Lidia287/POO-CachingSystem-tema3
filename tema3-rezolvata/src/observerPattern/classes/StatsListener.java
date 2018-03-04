package observerPattern.classes;

import observerPattern.interfaces.CacheListener;

/**
 * The StatsListener collects hit / miss / update stats for a cache.
 *
 * @param <K>
 * @param <V>
 */
public class StatsListener<K, V> implements CacheListener<K, V> {

    private int hits;
    private int misses;
    private int updates;

    public StatsListener() {

        this.hits = 0;
        this.misses = 0;
        this.updates = 0;
    }

    /**
     * Get the number of hits for the cache.
     *
     * @return number of hits
     */
    public int getHits() {
        return this.hits;
    }

    /**
     * Get the number of misses for the cache.
     *
     * @return number of misses
     */
    public int getMisses() {
        return this.misses;
    }

    /**
     * Get the number of updates (put operations) for the cache.
     *
     * @return number of updates
     */
    public int getUpdates() {
        return this.updates;
    }

    /**
     * this method adds to the number of hits.
     * @param key
     * @return nothing.
     */
    public void onHit(final K key) {
        this.hits = this.hits + 1;
    }

    /**
     * this method adds to the number of misses.
     * @param key
     * @return nothing.
     */
    public void onMiss(final K key) {
        this.misses = this.misses + 1;
    }

    /**
     * this method adds to the number of updates.
     * @param key, value
     * @return nothing.
     */
    public void onPut(final K key, final V value) {
        this.updates = this.updates + 1;
    }
}

