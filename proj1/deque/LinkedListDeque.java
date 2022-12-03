package deque;

import java.util.Iterator;

/**
 * Deque implementation based on specs for project1... and some tuning :)
 * @param <T>
 */
public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {

    /**
     * One DequeNode - a unit used by LinkedListDeque :)
     * @param <T>
     */
    private static class DequeNode<T> {
        public T item;
        public DequeNode prev;
        public DequeNode next;

        public DequeNode(T i, DequeNode p, DequeNode n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private class LinkedListeDequeIterator implements Iterator<T> {

        /*
        * Index of the currently visited position
        */
        private int vizPos;

        /**
         * HEAD pointer
         */
        private DequeNode<T> nextNode;

        private LinkedListeDequeIterator() {
            vizPos = 0;
        }
        @Override
        public boolean hasNext() {
            return vizPos < size;
        }

        @Override
        public T next() {
            nextNode = vizPos == 0 ? sentinel.next : nextNode.next;
            vizPos += 1;

            return nextNode.item;
        }
    }


    private DequeNode<T> sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new DequeNode<>(null, null, null);
        size = 0;
    }

    /* adds an item to the beginning */
    @Override
    public void addFirst(T item) {
        DequeNode<T> newNode = new DequeNode<>(item, sentinel, sentinel.next);

        if (size == 0) {
            sentinel.next = newNode;
            sentinel.prev = newNode;

            //in case it's the first added item, both (prev&next) pointers point to sentinel
            newNode.next = sentinel;
        } else {
            //set the previously first prev pointer to newNode
            sentinel.next.prev = newNode;
            //set sentinel's next pointer to the nexNode - new first element
            sentinel.next = newNode;
        }

        size += 1;
    }

    /* appends an item to the end */
    @Override
    public void addLast(T item) {
        DequeNode<T> newNode = new DequeNode<>(item, sentinel.prev, sentinel);

        if (isEmpty()) {
            sentinel.next = newNode;
            sentinel.prev = newNode;

            //in case it's the first added item, both (prev&next) pointers point to sentinel
            newNode.prev = sentinel;
        } else {
            //set the previously last node's next pointer to newNode
            sentinel.prev.next = newNode;
            //set sentinel's prev pointer to the nexNode - new last element
            sentinel.prev = newNode;
        }

        size += 1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    /* removes and returns the first element of the deque */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        DequeNode<T> firstNode = sentinel.next;
        T firstItem = firstNode.item;


        if (size == 1) {
            sentinel.next = null;
            sentinel.prev = null;
        } else {
            //set the previously 2nd Node's pointer to sentinel
            sentinel.next.prev = sentinel;
            //set sentinel's next pointer to the 2nd (before removal), now new first element
            sentinel.next = firstNode.next;
        }

        size -= 1;

        return firstItem;
    }

    /* removes and returns the last element of the deque */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        DequeNode<T> lastNode = sentinel.prev;
        T lastItem = lastNode.item;


        if (size == 1) {
            sentinel.next = null;
            sentinel.prev = null;
        } else {
            //sent the next pointer of the (before deletion) 2nd to last element to sentinel (now it's the new last)
            sentinel.prev.next = sentinel;
            sentinel.prev = lastNode.prev;
        }

        size -= 1;

        return lastItem;
    }

    /**
     * Get element by index using iteration (indexing from the front)
     * @param index
     * @return
     */
    @Override
    public T get(int index) {
        if (index > size - 1) {
            throw new IndexOutOfBoundsException();
        }

        boolean iterFromFront = shouldSearchFromFront(index, getBreakPointIndex());

        DequeNode<T> nextNode = iterFromFront ? getFromFront(index) : getFromBack(index);

        return nextNode.item;
    }


    /**
     * Get element by index using recursion (indexing from the front)
     * @param index
     * @return
     */
    public T getRecursive(int index) {
        if (index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        boolean shouldSearchFromFront = shouldSearchFromFront(index, getBreakPointIndex());

        DequeNode<T> nextNode = shouldSearchFromFront ?
                getRecFromFront(sentinel.next, index) :
                getRecFromBack(sentinel.prev, getReverseIndex(index));

        return nextNode.item;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListeDequeIterator();
    }

    /*
       Returns whether or not the parameter o is equal to the deque.
        o is considered equal if it is a deque and if it contains the same contents
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LinkedListDeque) || size() != ((LinkedListDeque<?>) o).size()) {
            return false;
        }

        Iterator curObj = iterator();
        Iterator compObj = ((LinkedListDeque<?>) o).iterator();

        while(curObj.hasNext() && compObj.hasNext()) {
            if (curObj.next() != compObj.next()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, prints out a new line.
     */
    @Override
    public void printDeque() {
        if (size > 0) {
            StringBuilder itemString = new StringBuilder();
            for (T itemValue : this) {
                itemString.append(itemValue).append(" ");
            }

            System.out.println(itemString);
        }

        System.out.println();
    }

    /*
        Helper method for getRecursive
         */
    private DequeNode getRecFromFront(DequeNode nextNode, int index) {
        if (index == 0) {
            return nextNode;
        }

        return getRecFromFront(nextNode.next, index - 1);
    }

    /**
     * Helper method - reversing the index - useful when looking from the back
     * @param index
     * @return
     */
    private int getReverseIndex(int index) {
        return (size - 1) - index;
    }
    /*
    Helper method for getRecursive
    Expects index provided from behind so the last (9th) element from a 9 item list is at index=0
     */
    private DequeNode getRecFromBack(DequeNode nextNode, int reverseIndex) {
        if (reverseIndex == 0) {
            return nextNode;
        }

        return getRecFromBack(nextNode.prev, reverseIndex - 1);
    }


    /**
     * Iterate from the front until index and return DequeNode at said index
     * @param index
     * @return
     */
    private DequeNode getFromFront(int index) {
        DequeNode nextNode = sentinel.next;

        int i = 0;
        while (i < index) {
            nextNode = nextNode.next;
            i += 1;
        }

        return nextNode;
    }

    /*
     * Iterate from the back until index and return DequeNode at said index
    *  */
    private DequeNode getFromBack(int index) {
        DequeNode nextNode = sentinel.prev;

        int i = size - 1;
        while (i > index) {
            nextNode = nextNode.prev;
            i -= 1;
        }

        return nextNode;
    }

    /*
    Returns size / 2 if size even
    (size + 1) / 2 if uneven

    Eg. for size 7 -> getHalfIndex() -> 4
    */
    private int getBreakPointIndex() {
        boolean isSizeEven = size % 2 == 0;
        return isSizeEven ? size / 2 : (size + 1) / 2;
    }

    /*
    * Determines whether we should start looking for an item from the front
    * True: start from the front
    * False: start from the end
    * */
    private boolean shouldSearchFromFront(int requestedIndex, int brPointIndex) {
        return requestedIndex < brPointIndex;
    }


}
