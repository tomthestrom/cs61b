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
        boolean isFull = sizeFirst == firstItems.length;

        if (isFull) {
            resize(sizeFirst * 2, firstItems);
        }

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
        boolean isFull = sizeLast == lastItems.length;

        if (isFull) {
            resize(sizeLast * 2, lastItems);
        }

        lastItems[sizeLast] = item;
        sizeLast += 1;
    }

    public int size() {
        return sizeFirst + sizeLast;
    }

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
        if ((sizeFirst < firstItems.length / 2) && (sizeFirst > 8)) {
            resize(firstItems.length / 2, firstItems);
        }

        int firstItemIndex = getIndexInFirstItems(0);

        T firstItem = firstItems[firstItemIndex];
        firstItems[firstItemIndex] = null;

        sizeFirst -= 1;

        return firstItem;
    }

    public T removeLast() {
        if ((sizeLast < lastItems.length / 2) && (sizeLast > 8)) {
            resize(lastItems.length / 2, lastItems);
        }

        int lastItemIndex = getIndexInLastItems(size() - 1);
        T lastItem = lastItems[lastItemIndex];
        lastItems[lastItemIndex] = null;

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
            return firstItems[getIndex(index, isInFirstItems)];
        } else {
            return lastItems[getIndex(index, isInFirstItems)];
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
           return requestedIndex - sizeFirst;
        }

        return requestedIndex;
    }

    private int getIndex(int requestedIndex, boolean first) {
       if (first) {
           return getIndexInFirstItems(requestedIndex);
       }

        return getIndexInLastItems(requestedIndex);
    }

    /**
     * Resizes the underlying array (passed in as reference to param arrayToResize) (either firstItems or lastItems)
     */
    private void resize(int capacity, T[] arrayToResize) {
        T[] a = (T[]) new Object[capacity];

        boolean resizeFirst = arrayToResize == firstItems;

        if (resizeFirst) {
            resizeFirst(a);
        } else {
            resizeLast(a);
        }
    }

    /*
     *
     * check addFirst to learn how indexing works
     * basically we're adding new firsts from right to left
     * determine the leftmost (starting) index
     * with last elements it works as in trivial array - adding from left to right
     * default value 0 works for lastItems
     *
     *  */
    private void resizeFirst(T[] a) {
        int startIndex =  a.length - sizeFirst;
        int lenToCopy  = sizeFirst;

        System.arraycopy(firstItems,0, a, startIndex, lenToCopy);

        firstItems = a;
    }

    private void resizeLast(T[] a) {
        int startIndex = 0;
        int lenToCopy = sizeLast;

        System.arraycopy(lastItems,0, a, startIndex, lenToCopy);

        lastItems = a;
    }
}
