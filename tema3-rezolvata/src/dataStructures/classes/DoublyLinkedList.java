package dataStructures.classes;




public class DoublyLinkedList<K, V> {

    private Node<K, V> head;
    private Node<K, V> tail;
    private int size;

    public DoublyLinkedList() {
       size = 0;
    }

    /**
     * returns the size of the linked list.
     * @return int
     */
    public int size() {
        return size;
    }

    /**
     * return whether the list is empty or not.
     * @return boolean
     */
    public boolean isEmpty() {
        return size == 0;
    }



    /**
     * return the least recently used element.
     * @return Pair<K,V>
     */
    public Pair<K, V> getLRU() {

        if (head == null) {
            return null;
        }

        Pair<K, V> pair = new Pair<K, V>(tail.getKey(), tail.getValue());

        return pair;
    }


    /**
     * this method adds an element at the front of the list.
     * @return Node<K, V>
     */
    public Node<K, V> add(final Node<K, V> node) {

        // Reset position
        node.setNext(null);
        node.setPrev(null);

        // First element into the list
        if (null == this.head) {
            this.head = node;
            this.tail = node;
            return node;
        }

        // else
        this.head.setPrev(node);
        node.setNext(this.head);
        this.head = node;
        return node;
    }




    /**
     * this method removes element from the linked list.
     * @return nothing
     */
    public void remove(final Node<K, V> node) {

        // Nothing to be done
        if (this.head == null || null == node) {
            return;
        }

        // Only one node in the list
        if (this.head.getKey().equals(this.tail.getKey())
                && this.head.getKey().equals(node.getKey())) {
            this.head = null;
            this.tail = null;
            return;
        }

        // Remove from head
        if (node.getKey().equals(this.head.getKey())) {
            this.head.getNext().setPrev(null);
            this.head = this.head.getNext();
            return;
        }

        // Remove from end
        if (node.getKey().equals(this.tail.getKey())) {
            this.tail.getPrev().setNext(null);
            this.tail = this.tail.getPrev();
            return;
        }

        // Remove in the middle
        node.getPrev().setNext(node.getNext());

        node.getNext().setPrev(node.getPrev());

    }

    /**
     * this method moves a node that was used recently at the front of the list.
     * @return Node<K, V>
     */
    public Node<K, V> moveFirst(final Node<K, V> node) {
        this.remove(node);
        this.add(node);

        return node;
    }

    /**
     * this method removes element from the end of the linked list.
     * @return
     */
    public void removeLast() {
        this.remove(this.tail);
    }



}
