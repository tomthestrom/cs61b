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
     * Added item is always at the beginning of the array.
     * Such as beginning: [null, null, null, null]
     * addFirst(4)
     * [null, null, null, 4]
     * addFirst(3)
     * [null, null, 3, 4]
     */
    public void addFirst(T item) {
        firstItems[firstItems.length - 1 - sizeFirst] = item;
        sizeFirst += 1;
    }

    /**
     * Added item is always at the current end of the array
     * Such as beginning: [null, null, null, null]
     * addLast(5)
     * [5, null, null, null]
     * addLast(6)
     * [5, 6, null, null]
     */
    public void addLast(T item) {
        lastItems[sizeLast] = item;
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
        * firstItems = [ 1 , 2 , 3, 4] - latest first item at the beginning of the array
        * lastItems = [5, 6, 7, 8] - latest added last item at the end of the array
        * Conceptual array = [1, 2, 3, 4, 5, 6, 7, 8] - or firstItems + lastItems
     */
    public T get(int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        boolean isInFirstItems = index < sizeFirst;

        if (isInFirstItems) {
            return firstItems[getIndexInFirstItems(index)];
        } else {
            return lastItems[getIndexInLastItems(index)];
        }
    }

    /*
    * Determines the starting index in the underlying implementation
    * in an array with 3 items of length 4: beginning index:
    * First element can be found at:
    * [length: 4 - sizeFirst: 3]
    * + index provided by the user to get the intended value
    *  */
    private int getIndexInFirstItems(int requestedIndex) {
        return (firstItems.length - sizeFirst) + requestedIndex;
    }

    /**
     * Calculates the index in the lastItems array
     * In case there are items in firstItems we need to adjust the requestedIndex to account for indexes in firstItems
     */
    private int getIndexInLastItems(int requestedIndex) {
        if (sizeFirst > 0) {
           return requestedIndex - sizeFirst - 1;
        }

        return requestedIndex;
    }
}
