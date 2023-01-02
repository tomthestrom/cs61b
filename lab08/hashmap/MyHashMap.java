package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    /**
     * Holds the instances of bucket - anything that implements a Collection, like LL, AL, ...
     */
    private Collection<Node>[] buckets;

    /**
     * Default size of the buckets array
     */
    protected static int DEFAULT_INIT_SIZE = 16;

    /**
     * Default Resize Factor of buckets[]
     */
    private static int RESIZE_FACTOR = 2;

    /**
     * Nr. of items stored in MyHashMap
     */
    private int size;

    /**
     * Default Load Factor
     * Defined as elements in the map / number of buckets
     */
    protected static double DEFAULT_LF = .75;

    /**
     *  Max Load Factor
     */
    private double maxLF;


    /** Constructors */
    public MyHashMap() {
        buckets = createTable(DEFAULT_INIT_SIZE);
        size = 0;
        maxLF = DEFAULT_LF;
        fillWithBuckets(buckets);
    }

    public MyHashMap(int initialSize) {
        buckets = createTable(initialSize);
        size = 0;
        maxLF = DEFAULT_LF;
        fillWithBuckets(buckets);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        size = 0;
        maxLF = maxLoad;
        fillWithBuckets(buckets);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Populates the buckets array with a new bucket for each index
     */
    private void fillWithBuckets(Collection<Node>[] buckets) {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    /**
     * Removes all items, resets bucket size to default value
     */
    @Override
    public void clear() {
        buckets = createTable(DEFAULT_INIT_SIZE);
        fillWithBuckets(buckets);
        size = 0;
    }

    /**
     * Searches for the key, true if found
     */
    @Override
    public boolean containsKey(K key) {
        int keyHash = hash(key, buckets.length);

        Iterator<Node> iterator  = buckets[keyHash].iterator();
        Node currNode;

        while (iterator.hasNext()) {
            currNode = iterator.next();

            if (currNode.key.equals(key)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the value associated with the key if key found, false otherwise
     */
    @Override
    public V get(K key) {
        int keyHash = hash(key, buckets.length);

        Iterator<Node> iterator  = buckets[keyHash].iterator();
        Node currNode;

        while (iterator.hasNext()) {
            currNode = iterator.next();

            if (currNode.key.equals(key)) {
                return currNode.value;
            }
        }

        return null;
    }

    /**
     * Nr of elements stored within MyHashMap
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Stores the given key-value pair, resizes the bucket array if it exceeds the max load factor
     */
    @Override
    public void put(K key, V value) {
        int keyHash = hash(key, buckets.length);

        Iterator<Node> iterator  = buckets[keyHash].iterator();
        Node currNode;

        while (iterator.hasNext()) {
            currNode = iterator.next();

            if (currNode.key.equals(key)) {
                currNode.value = value;
                return;
            }
        }

        if (shouldResizeBuckets()) {
            resizeBuckets(RESIZE_FACTOR);
            //buckets size changed - get new hash
            keyHash = hash(key, buckets.length);
        }

        buckets[keyHash].add(new Node(key, value));
        size++;
    }

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


    /**
     * Copied from: https://algs4.cs.princeton.edu/34hash/LinearProbingHashST.java.html
     * hash function for keys - returns value between 0 and m-1
     */
    private int hash(K key, int nrOfBuckets) {
        return (key.hashCode() & 0x7fffffff) % nrOfBuckets;
    }

    /**
     * Returns true if elements to buckets ratio larger than or equal to max load factor
     */
    private boolean shouldResizeBuckets() {
        return (float) size() / buckets.length >= maxLF;
    }

    /**
     * Handles resizing of the buckets array
     * @param resizeFactor
     */
    private void resizeBuckets(int resizeFactor) {
        Collection<Node>[] newBuckets = createTable(buckets.length * resizeFactor);
        fillWithBuckets(newBuckets);

        for (Collection<Node> bucket : buckets) {
            Iterator<Node>iterator = bucket.iterator();
            Node currNode;
            int keyHash;

            while (iterator.hasNext()) {
                currNode = iterator.next();
                keyHash = hash(currNode.key, newBuckets.length);
                newBuckets[keyHash].add(currNode);
            }
        }
        buckets = newBuckets;
    }
}
