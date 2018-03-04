package observerPattern.classes;

import java.util.List;

import observerPattern.interfaces.CacheListener;

import java.util.ArrayList;

/**
 * The BroadcastListener broadcasts cache events to other listeners that have
 * been added to it.
 */
public class BroadcastListener<K, V> implements CacheListener<K, V> {



    private List<CacheListener<K, V>> listeners = new ArrayList<CacheListener<K, V>>();

    /**
     * returns the list.
     * @return the list
     */
    public List<CacheListener<K, V>> getList() {
        return this.listeners;
    }


    /**
     * Add a listener to the broadcast list.
     *
     * @param listener
     *            the listener
     */
    public void addListener(final CacheListener<K, V> listener) {
        listeners.add(listener);
    }

    /**
     * this method applies onHit to all the listeners in the list.
     * @param key
     * @return nothing.
     */
    public void onHit(final K key) {
        for (CacheListener<K, V> listener : listeners) {
            listener.onHit(key);
        }
    }

    /**
     * this method applies onMiss to all the listeners in the list.
     * @param key
     * @return nothing.
     */
    public void onMiss(final K key) {
        for (CacheListener<K, V> listener : listeners) {
            listener.onMiss(key);
        }
    }

    /**
     * this method applies onPut to all the listeners in the list.
     * @param key, value
     * @return nothing.
     */
    public void onPut(final K key, final V value) {
        for (CacheListener<K, V> listener : listeners) {
            listener.onPut(key, value);
        }
    }

}
