package deque;

public class LinkedListDeque {

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

    private DequeNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new DequeNode(0, null, null);
        size = 0;
    }
}
