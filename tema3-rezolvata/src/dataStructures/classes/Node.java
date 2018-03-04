package dataStructures.classes;

public class Node<K, V> {
    private K key;
    private V value;
    private Node<K, V> next;
    private Node<K, V> prev;


    public Node(final K key, final V value, final Node<K, V> next, final Node<K, V> prev) {
        this.key = key;
        this.value = value;
        this.next = next;
        this.prev = prev;
    }

    public Node() {

    }

    /**
     * returns the value of the node.
     * @return V
     */
    public V getValue() {
        return this.value;
    }
    /**
     * returns the key of the node.
     * @return K
     */
    public K getKey() {
        return this.key;
    }
    /**
     * returns the next element to the node.
     * @return Node<K, V>
     */
    public Node<K, V> getNext() {
        return this.next;
    }
    /**
     * returns the prev element to the node.
     * @return Node<K, V>
     */
    public Node<K, V> getPrev() {
        return this.prev;
    }
    /**
     * sets the key of the node.
     * @return nothing
     */
    public void setKey(final K key) {
        this.key = key;
    }
    /**
     * sets the value of the node.
     * @return nothing
     */
    public void setValue(final V value) {
        this.value = value;
    }

    /**
     * sets the prev of the node.
     * @return nothing
     */
    public void setPrev(final Node<K, V> prev) {
        this.prev = prev;
    }

    /**
     * sets the next of the node.
     * @return nothing
     */
    public void setNext(final Node<K, V> next) {
        this.next = next;
    }


}
