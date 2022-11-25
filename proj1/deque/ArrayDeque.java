package deque;

public class ArrayDeque<T> {

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
        int insertIndex = getNextFirstIndex();

        circularArray[insertIndex] = item;
        size += 1;
    }

    /**
     * Inserts items starting from the middle of the array to the right
     * In case the right size is filled till the last index, start inserting from index 0
     * [null, null, null, 1, null, null, null, null]
     * insert direction:
     * [-----------> end\ /start------------------>]
     */
    public void addLast(T item) {
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
        T firstItem = circularArray[firstIndex];

        circularArray[firstIndex] = null;
        size -= 1;
        //move firstIndex from right to left
        firstIndex = firstIndex + 1 < circularArray.length ? firstIndex + 1  : 0;

        return firstItem;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T lastItem = circularArray[lastIndex];

        circularArray[lastIndex] = null;
        size -= 1;

        //move last index from left to right
        lastIndex = lastIndex - 1 >= 0 ? lastIndex - 1 : circularArray.length - 1;

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

        boolean isInFirstItems = index < size;
        return null;
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
     * Returns the index of the currently considered first element from circularArray
     */
    private int getFirstIndex() {
        return 0;
    }


//
//    /*
//    * Determines the starting index in the underlying implementation
//    * in an array with 3 items of length 4: beginning index:
//    * First element can be found at:
//    * [length: 4 - sizeFirst: 3]
//    * + index provided by the user to get the intended value
//    *  */
//    private int getIndexInFirstItems(int requestedIndex) {
//        return (firstItems.length - sizeFirst) + requestedIndex;
//    }
//
//    /**
//     * Calculates the index in the lastItems array
//     * In case there are items in firstItems we need to adjust the requestedIndex to account for indexes in firstItems
//     */
//    private int getIndexInLastItems(int requestedIndex) {
//        if (sizeFirst > 0) {
//           return requestedIndex - sizeFirst;
//        }
//
//        return requestedIndex;
//    }
//
//    private int getIndex(int requestedIndex, boolean first) {
//       if (first) {
//           return getIndexInFirstItems(requestedIndex);
//       }
//
//        return getIndexInLastItems(requestedIndex);
//    }

    /**
     * Resizes the underlying array (passed in as reference to param arrayToResize) (either firstItems or lastItems)
     */
//    private void resize(int capacity, T[] arrayToResize) {
//        T[] a = (T[]) new Object[capacity];
//
//        boolean resizeFirst = arrayToResize == firstItems;
//
//        if (resizeFirst) {
//            resizeFirst(a);
//        } else {
//            resizeLast(a);
//        }
//    }

    /*
     *
     * check addFirst to learn how indexing works
     * basically we're adding new firsts from right to left
     * determine the leftmost (starting) index
     * with last elements it works as in trivial array - adding from left to right
     * default value 0 works for lastItems
     *
     *  */
//    private void resizeFirst(T[] a) {
//        int startSrcIndex = firstItems.length - sizeFirst;
//        int startDestIndex =  a.length - sizeFirst;
//        int lenToCopy  = sizeFirst;
//
//        System.arraycopy(firstItems,startSrcIndex, a, startDestIndex, lenToCopy);
//
//        firstItems = a;
//    }
//
//    private void resizeLast(T[] a) {
//        int startIndex = 0;
//        int lenToCopy = sizeLast;
//
//        System.arraycopy(lastItems,0, a, startIndex, lenToCopy);
//
//        lastItems = a;
//    }
}
