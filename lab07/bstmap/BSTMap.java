package bstmap;

import java.util.Iterator;
import java.util.Set;

/**
 * Implements a BSTMap as specified by MAP61B interface
 * Core data structure is a binary search tree
 */
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

    /**
     * Removes all items from the BST
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns true if the BST contains the requested key
     */
    @Override
    public boolean containsKey(K key) {
        return containsKeyHelper(root, key);
    }

    /**
     * Recursively traverses the tree in search of the requested key, returns
     * true if found, false otherwise
     */
    private boolean containsKeyHelper(BSTNode node, K key) {
        if (node == null) {
            return false;
        }

        if (node.key.compareTo(key) == 0) {
            return true;
        }

        boolean searchLeft = node.key.compareTo(key) > 0;

        if (searchLeft) {
            return containsKeyHelper(node.left, key);
        } else {
            return containsKeyHelper(node.right, key);
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(root, key);
    }

    /**
     * Recursively traverses the tree in search of the requested key, returns its value
     */
    private V getHelper(BSTNode node, K key) {
        if (node == null) {
            return null;
        }

        if (node.key.compareTo(key) == 0) {
            return (V) node.value;
        }

        boolean searchLeft = node.key.compareTo(key) > 0;

        if (searchLeft) {
            return getHelper(node.left, key);
        } else {
            return getHelper(node.right, key);
        }

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
    }

    /**
     * Recursively traverses the nodes comparing the keys to find out where to insert the key, value pair
     */
    private BSTNode<K, V> putHelper(BSTNode<K, V> node, K key, V value) {
        if (node == null) {
            size++;
            return new BSTNode<>(key, value);
        }

        //in case the key already exists, we just assing a new value
        boolean insertHere = node.key.compareTo(key) == 0;

        if (insertHere) {
           node.value = value;
           return node;
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
