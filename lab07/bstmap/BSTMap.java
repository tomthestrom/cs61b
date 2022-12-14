package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K, V> implements Map61B<K, V> {
    /**
     * The root of the tree
     */
    private BSTNode<K, V> root;

    /**
     * The nr of nodes in the tree
     */
    private int size;

    /**
     * Represents a node within the BST
     */
    private static class BSTNode<K, V> {
        /**
         * The key of the node
         */
        K key;
        /**
         * The value of the node
         */
        V value;

        /**
         * Reference to the left child
         */
        BSTNode<K, V> left;
        /**
         * Reference to the right child
         */
        BSTNode<K, V> right;

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    @Override
    public void clear() {

    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {

    }

    /**
     * Prints out the BSTMap in order of increasing Key
     */
    public void printInOrder() {

    }

    /*
     * Unsupported Methods:
     * keySet
     * remove(key)
     * remove(key, value)
     * iterator()
     */

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
