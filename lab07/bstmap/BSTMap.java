package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable, V> implements Map61B<K, V> {
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
    private static class BSTNode<K extends Comparable, V> {
        /**
         * The key of the node
         */
        public K key;
        /**
         * The value of the node
         */
        public V value;

        /**
         * Reference to the left child
         */
        public BSTNode<K, V> left;
        /**
         * Reference to the right child
         */
        public BSTNode<K, V> right;

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

    /**
     * Returns the nr. of stored items
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Stores a key value pair
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(root, key, value);
        size++;
    }

    /**
     * Recursively traverses the nodes comparing the keys to find out where to insert the key, value pair
     */
    private BSTNode<K, V> putHelper(BSTNode<K, V> node, K key, V value) {
        if (node == null) {
            return new BSTNode<>(key, value);
        }
        boolean insertLeft = node.key.compareTo(key) > 0;

        if (insertLeft) {
            node.left = putHelper(node.left, key, value);
        } else {
            node.right = putHelper(node.right, key, value);
        }

        return node;
    }

    /**
     * Prints out the BSTMap in order of increasing Key
     */
    public void printInOrder() {
        printInOrderHelper(root);
        System.out.println();
    }

    /**
     * Recursively traverses the tree and prints its items
     * visiting the smallest (left) values first before printing the current value and
     * visitng the largerst (right) values
     */
    private void printInOrderHelper(BSTNode<K, V> node) {
        if (node == null) {
            return;
        }
        printInOrderHelper(node.left);
        System.out.print(node.key);
        printInOrderHelper(node.right);
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
