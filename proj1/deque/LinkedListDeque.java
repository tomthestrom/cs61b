package deque;

public class LinkedListDeque<T> {

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

    private DequeNode<T> sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new DequeNode<>(null, null, null);
        size = 0;
    }

    /* adds an item to the beginning */
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
    public void addLast(T item) {
        DequeNode<T> newNode = new DequeNode<>(item, sentinel.prev, sentinel);

        if (size == 0) {
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

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /* removes and returns the first element of the deque */
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

    public T get(int index) {
        if (index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        int i = 0;

        DequeNode<T> nextNode = sentinel.next;

        while (i < index) {
            nextNode = nextNode.next;
            i += 1;
        }


        return nextNode.item;
    }

    /*
       Returns whether or not the parameter o is equal to the deque.
        o is considered equal if it is a deque and if it contains the same contents
     */
    public boolean equals(Object o) {
        if (!(o instanceof LinkedListDeque) || size() != ((LinkedListDeque<?>) o).size()) {
            return false;
        }

        for (int i = 0; i < size; i += 1) {
            if (get(i) != ((LinkedListDeque<?>) o).get(i)) {
                return false;
            }
        }

        return true;
    }
}
