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

    /**
     * Added item is always at the end of the array.
     * @param item
     */
    public void addFirst(T item) {
        firstItems[sizeFirst] = item;
        sizeFirst += 1;
    }

    /**
     * Added item is always at the current beginning
     * @param item
     */
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

    /**
     * Gets item by index
     * Storing items is implemented as:
        * firstItems = [ 4 , 3 , 2, 1] - latest first item at the end of the array
        * lastItems = [8, 7, 6, 5] - latest added last item in the beginning of the array
        * Conceptual array = [1, 2, 3, 4, 5, 6, 7, 8] - or reversed(firstItems) + reversed(lastItems)
     * @param index
     * @return
     */
    public T get(int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        boolean isInFirstItems = index < sizeFirst;

        if (isInFirstItems) {
            return firstItems[sizeFirst - 1 - index];
        } else {
            return lastItems[lastItems.length - 1 - index];
        }
    }
}
