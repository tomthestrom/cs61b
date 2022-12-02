package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T> {

    /**
     * Stores items added to the front and to the end
     */
    private T[] circularArray;

    /**
     * index of the first item
     */
    private int firstIndex;
    /**
     * index of the last item
     */
    private int lastIndex;

    /**
     * Number of elements that circularArray holds
     */
    private int size;

    /**
     * minimum size of underlying array as in specification
     */
    private int ITEMS_MIN_SIZE = 8;

    private class ArrayDequeIterator implements Iterator<T> {


        /*
         * Index of the currently visited position
         */
        private int vizPos;


        private ArrayDequeIterator() {
            vizPos = 0;
        }
        @Override
        public boolean hasNext() {
            return vizPos < size;
        }

        @Override
        public T next() {
            T nextItem = get(vizPos);
            vizPos++;

            return nextItem;
        }
    }
    public ArrayDeque() {
        circularArray = (T[]) new Object[ITEMS_MIN_SIZE];
        size = 0;
    }

    /**
     * Inserts items starting from the middle of the array to the left
     * In case the left size is filled till index 0 - inserting from the end
     * [null, null, null, 1, null, null, null, null]
     * insert direction:
     * [<----------- start\ /end<------------------]
     */
    public void addFirst(T item) {
        resizeUpIfNecessary();

        int insertIndex = getNextFirstIndex();

        circularArray[insertIndex] = item;
        size += 1;
    }

    /**
     * Inserts items left to right
     * In case the right size is filled till the last index, start inserting from index 0
     * [null, null, null, 1, null, null, null, null]
     * insert direction:
     * [-----------> end\ /start------------------>]
     */
    public void addLast(T item) {
        resizeUpIfNecessary();

        int insertIndex = getNextLastIndex();
        circularArray[insertIndex] = item;
        size += 1;
    }

    /**
     * Size provided to the user of ArrayDeque #Plateau's cave
     */
    public int size() {
        return size;
    }

    /**
     * True if no items stored
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, prints out a new line.
     */
    public void printDeque() {
        if (size() > 0) {
            StringBuilder itemString = new StringBuilder(String.valueOf(get(0)));
            for (int i = 1; i < size(); i += 1) {
                itemString.append(" ").append(get(i));
            }
            System.out.println(itemString);
        }

        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        resizeDownIfNecessary();

        T firstItem = circularArray[firstIndex];

        circularArray[firstIndex] = null;
        size -= 1;
        //move firstIndex from right to left
        firstIndex = firstIndex + 1 < circularArray.length ? firstIndex + 1 : 0;

        return firstItem;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        resizeDownIfNecessary();

        T lastItem = circularArray[lastIndex];

        circularArray[lastIndex] = null;
        size -= 1;

        //move last index from left to right
        lastIndex = lastIndex - 1 >= 0 ?
                lastIndex - 1 :
                circularArray.length - 1;

        return lastItem;
    }

    /**
     * Gets item by index
     * Cycling from right to left
     */
    public T get(int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        int requestedIndex = firstIndex + index < circularArray.length ?
                firstIndex + index :
                firstIndex + index - circularArray.length;

        return circularArray[requestedIndex];
    }

    /*
       Returns whether or not the parameter o is equal to the deque.
        o is considered equal if it is a deque and if it contains the same contents
        //compares to ArrayDeques for now
     */
    public boolean equals(Object o) {
        if (!(o instanceof ArrayDeque<?>) || size() != ((ArrayDeque<?>) o).size()) {
            return false;
        }

        for (int i = 0; i < size; i += 1) {
            if (get(i) != ((ArrayDeque<?>) o).get(i)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private int getCircularArrayMiddle() {
        return circularArray.length / 2;
    }

    /**
     * Returns the next index to insert the first element at within circularArray
     */
    private int getNextFirstIndex() {
        int insertIndex;

        //if size 0, insert into the middle
        if (size() == 0) {
            insertIndex = getCircularArrayMiddle() - 1;
            //if size 0, set the lastIndex
            lastIndex = insertIndex;
        } else { //insertion from right to left
            insertIndex = firstIndex - 1 >= 0 ? firstIndex - 1 : circularArray.length - 1;
        }

        firstIndex = insertIndex;

        return insertIndex;
    }

    /**
     * Returns the next index to insert the last element at within circularArray
     */
    private int getNextLastIndex() {
        int insertIndex;

        //if size 0, insert into the middle
        if (size() == 0) {
            insertIndex = getCircularArrayMiddle();
            //if size 0, set the lastIndex
            firstIndex = insertIndex;
        } else { //insertion from left to right
            insertIndex = lastIndex + 1 < circularArray.length ? lastIndex + 1 : 0;
        }

        lastIndex = insertIndex;

        return insertIndex;
    }

    /**
     * Resizes the underlying array
     * In the newly created circularArray the first element is at index 0
     */
    private void resize(int capacity) {
        if (capacity < ITEMS_MIN_SIZE) {
            capacity = ITEMS_MIN_SIZE;
        }

        T[] a = (T[]) new Object[capacity];

        for (int i = 0; i < size(); i += 1) {
            a[i] = get(i);
        }

        firstIndex = 0;
        lastIndex = size() - 1;
        circularArray = a;
    }

    /**
     * Decrease size if usage below LENGTH_TO_USAGE_RATIO by RESIZE_FACTOR
     */
    private void resizeDownIfNecessary() {
        int LENGTH_TO_USAGE_RATIO = circularArray.length / 4;
        int RESIZE_FACTOR = 2;

        if ((size <= LENGTH_TO_USAGE_RATIO) && (size > 4)) {
            resize(circularArray.length / RESIZE_FACTOR);
        }
    }


    /**
     * Increase size by RESIZE_FACTOR
     */
    private void resizeUpIfNecessary() {
        int RESIZE_FACTOR = 2;
        if (size() == circularArray.length) {
            resize(circularArray.length * RESIZE_FACTOR);
        }
    }

}
