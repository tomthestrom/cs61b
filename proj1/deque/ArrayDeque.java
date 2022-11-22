package deque;

public class ArrayDeque<T> {

    private T[] firstItems;
    private T[] lastItems;
    private int sizeFirst;
    private int sizeLast;

    private int ITEMS_MIN_SIZE = 8;

    public ArrayDeque() {
        firstItems = (T[]) new Object[ITEMS_MIN_SIZE / 2];
        lastItems = (T[]) new Object[ITEMS_MIN_SIZE / 2];
        sizeFirst = 0;
        sizeLast = 0;
    }

    public void addFirst(T item) {
        firstItems[firstItems.length - 1 - sizeFirst] = item;
        sizeFirst += 1;
    }

    public void addLast(T item) {
        lastItems[lastItems.length - 1 - sizeLast] = item;
        sizeLast += 1;
    }

    public int size() {
        return sizeFirst + sizeLast;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void printDeque() {
        //TODO: Implement
    }

    public T removeFirst() {
        T firstItem = firstItems[firstItems.length - sizeFirst];
       firstItems[firstItems.length - sizeFirst] = null;

       sizeFirst -= 1;

       return firstItem;
    }

    public T removeLast() {
        T lastItem = lastItems[lastItems.length - sizeLast];
        lastItems[lastItems.length - sizeLast] = null;

        sizeLast -= 1;

        return lastItem;
    }

    public T get(int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        boolean isInFirstItems = index < sizeFirst;

        if (isInFirstItems) {
            return  firstItems[index];
        } else {
            return lastItems[sizeLast - index];
        }
    }
}
