package observerPattern.classes;

import java.util.LinkedHashMap;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;

import observerPattern.interfaces.CacheListener;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Collections;

/**
 * The KeyStatsListener collects key-level stats for cache operations.
 *
 * @param <K>
 * @param <V>
 */
public class KeyStatsListener<K, V> implements CacheListener<K, V> {

    //keyStats used to map on a key an array of integer
    //on position 0 - the number of misses
    //on position 1 - the number of hits
    //on position 2 - the number of updates
    private LinkedHashMap<K, ArrayList<Integer>> keyStats;

    /**
     * returns the keyStats hashmap.
     * @return hashmap
     */
    public LinkedHashMap<K, ArrayList<Integer>> getKeyStats() {
        return this.keyStats;
    }

    /**
     * adds an entry to the hashmap to the specified key.
     * @param key
     * @return nothing
     */
    public void addKeyStats(final K key) {
        final int three = 3;
        ArrayList<Integer> list = new ArrayList<Integer>(three);

        list.add(0); //  misses
        list.add(0); //  hits
        list.add(0); //  updates
        keyStats.put(key, list);
    }

    /**
     * adds to the number of misses in the array of the key.
     * @param key
     * @return nothing
     */
    public void setKeyStatsMisses(final K key) {

        if (getKeyStats().get(key) == null) {
            addKeyStats(key);
        }

        ArrayList<Integer> l = this.keyStats.get(key);
        int v = (Integer) l.get(0);
        v = v + 1;
        l.set(0, v);

    }

    /**
     * adds to the number of hits in the array of the key.
     * @param key
     * @return nothing
     */
    public void setKeyStatsHits(final K key) {

        if (getKeyStats().get(key) == null) {
            addKeyStats(key);
        }

        ArrayList<Integer> l = this.keyStats.get(key);
        int v = (Integer) l.get(1);
        v = v + 1;
        l.set(1, v);

    }

    /**
     * adds to the number of updates in the array of the key.
     * @param key
     * @return nothing
     */
    public void setKeyStatsUpdates(final K key) {

        if (getKeyStats().get(key) == null) {
            addKeyStats(key);
        }

        ArrayList<Integer> l = this.keyStats.get(key);
        int v = (Integer) l.get(2);
        v = v + 1;
        l.set(2, v);

    }

    public KeyStatsListener() {

        keyStats = new LinkedHashMap<K, ArrayList<Integer>>();
    }

    /**
     * Get the number of hits for a key.
     * @param key
     *            the key
     * @return number of hits
     */
    public int getKeyHits(final K key) {

        int v = (Integer) keyStats.get(key).get(1);
        return v;

    }

    /**
     * Get the number of misses for a key.
     *
     * @param key
     *            the key
     * @return number of misses
     */
    public int getKeyMisses(final K key) {

        int v = (Integer) keyStats.get(key).get(0);
        return v;


    }

    /**
     * Get the number of updates for a key.
     *
     * @param key
     *            the key
     * @return number of updates
     */
    public int getKeyUpdates(final K key) {

        int v = (Integer) keyStats.get(key).get(2);
        return v;


    }

    /**
     * returns the sorted map in descending order by value.
     * @return the sorted map
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> map) {

        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(final Map.Entry<K, V> o1, final Map.Entry<K, V> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * Get the @top most hit keys.
     * @param top
     *            number of top keys
     * @return the list of keys
     */
    public List<K> getTopHitKeys(final int top) {
        List<K> topHits = new ArrayList<K>();
        List<K> topHitsCount = new ArrayList<K>();


        Map<K, Integer> treeMap = new TreeMap<K, Integer>();

        for (Map.Entry<K, ArrayList<Integer>> entry : keyStats.entrySet()) {
            K key = entry.getKey();
            ArrayList<Integer> value = entry.getValue();

            treeMap.put(key, value.get(1));


        }

        Map<K, Integer> map = sortByValue(treeMap);

        for (Map.Entry<K, Integer> entry : map.entrySet()) {
            K key = entry.getKey();
            topHits.add(key);

        }



        int i = 0;
        while (i < top) {
            topHitsCount.add(topHits.get(i));
            i++;
        }



        return topHitsCount;


    }

    /**
     * Get the @top most missed keys.
     * @param top
     *            number of top keys
     * @return the list of keys
     */
    public List<K> getTopMissedKeys(final int top) {

        List<K> topHits = new ArrayList<K>();
        List<K> topHitsCount = new ArrayList<K>();


        Map<K, Integer> treeMap = new TreeMap<K, Integer>();


        for (Map.Entry<K, ArrayList<Integer>> entry : keyStats.entrySet()) {
            K key = entry.getKey();
            ArrayList<Integer> value = entry.getValue();

            treeMap.put(key, value.get(0));


        }

        Map<K, Integer> map = sortByValue(treeMap);

        for (Map.Entry<K, Integer> entry : map.entrySet()) {
            K key = entry.getKey();
            topHits.add(key);

        }



        int i = 0;
        while (i < top) {
            topHitsCount.add(topHits.get(i));
            i++;
        }


        return topHitsCount;


    }

    /**
     * Get the @top most updated keys.
     * @param top
     *            number of top keys
     * @return the list of keys
     */
    public List<K> getTopUpdatedKeys(final int top) {

        List<K> topHits = new ArrayList<K>();
        List<K> topHitsCount = new ArrayList<K>();


        Map<K, Integer> treeMap = new TreeMap<K, Integer>();


        for (Map.Entry<K, ArrayList<Integer>> entry : keyStats.entrySet()) {
            K key = entry.getKey();
            ArrayList<Integer> value = entry.getValue();
            treeMap.put(key, value.get(2));

        }

        Map<K, Integer> map = sortByValue(treeMap);

        for (Map.Entry<K, Integer> entry : map.entrySet()) {
            K key = entry.getKey();
            topHits.add(key);

        }


        int i = 0;
        while (i < top) {
            topHitsCount.add(topHits.get(i));
            i++;
        }


        return topHitsCount;

    }

    /**
     * this method uses the setKeyStatsHits which adds
     * to the number of hits.
     * @param key
     * @return nothing.
     */
    public void onHit(final K key) {
        setKeyStatsHits(key);
    }

    /**
     * this method uses the setKeyStatsMisses which adds
     * to the number of misses.
     * @param key
     * @return nothing.
     */
    public void onMiss(final K key) {
        setKeyStatsMisses(key);
    }

    /**
     * this method uses the setKeyStatsUpdates which adds
     * to the number of updates.
     * @param key, value
     * @return nothing.
     */
    public void onPut(final K key, final V value) {
        setKeyStatsUpdates(key);
    }

}
