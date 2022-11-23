package deque;

import org.junit.Test;

import static org.junit.Assert.*;

/* Performs ArrayDeque tests. */
public class ArrayDequeTest<T> {

    /**
     * After last call to addFirst:
     * Conceptual array: [5, 4, 2, 1]
     * In underlying implementation: firstItems = [5, 4, 2, 1]
     */
    @Test
    public void testAddFirstNoResizing() {
        ArrayDeque<Integer> AD1 = new ArrayDeque<>();

        //1 at index 0
        AD1.addFirst(1);
        // 2 at index 0, 1 at index 1
        AD1.addFirst(2);

        int expected = 1;
        int actual = AD1.get(1);

        assertEquals(expected, actual);

        //first at index 0 now
        AD1.addFirst(4);

        expected = 4;
        actual = AD1.get(0);
        assertEquals(expected, actual);

        AD1.addFirst(5);
        actual = AD1.get(1);

        //expected hasn't changed, therefore it's value is: 4
        assertEquals(expected, actual);
    }

    @Test
    /**
     * After last call to addLast:
     * Conceptual array: [1, 2, 4, 5]
     * In underlying implementation: lastItems = [1, 2, 4, 5]
     */
    public void testAddLastNoResizing() {
        ArrayDeque<Integer> AD1 = new ArrayDeque<>();

        AD1.addLast(1);
        AD1.addLast(2);

        int expected = 2;
        int actual = AD1.get(1);

        //[1, 2, null, null] - value at index 1 = 2
        assertEquals(expected, actual);

        AD1.addLast(4);

        expected = 1;
        actual = AD1.get(0);
        //[1, 2, 4, null] - value at index 0 = 1
        assertEquals(expected, actual);

        AD1.addLast(5);
        expected = 5;
        actual = AD1.get(3);
        //[1, 2, 4, 5] - value at index 3 = 1
        assertEquals(expected, actual);
    }

    @Test
    /**
     * After last add operation:
     * Conceptual array: [0, 1, 2, 3]
     * In underlying implementation:
     * firstItems: [null, null, 0, 1]
     * lastItems: [2, 3, null, null]
     */
    public void testGetNoResize() {
        ArrayDeque<Integer> AD1 = new ArrayDeque<>();

        AD1.addLast(3);
        AD1.addLast(2);
        AD1.addFirst(1);

        int expected1 = 1;
        int actual1 = AD1.get(0);

        /**
         * firstItems: [null, null, null, 1]
         * lastItems: [2, 3, null, null]
         * Expected 1 - index 0 at firstItems index 4 in the underlying implementation
         */
        assertEquals(expected1, actual1);

        AD1.addFirst(0);

        int expected0 = 0;
        int actual0 = AD1.get(0);

        /**
         * firstItems: [null, null, 0, 1]
         * lastItems: [2, 3, null, null]
         * Expected 0 - index 0 at firstItems index 3 in the underlying implementation
         */
        assertEquals(expected0, actual0);


        int expected3 = 3;
        int actual3 = AD1.get(3);

        /**
         * firstItems: [null, null, 0, 1]
         * lastItems: [2, 3, null, null]
         * Expected 3 - index 3 at lastItems index 1 in the underlying implementation
         */
        assertEquals(expected3, actual3);
    }

    @Test
    public void testSize() {
        ArrayDeque<Integer> AD = new ArrayDeque();

        int N = 10;

        //conceptual array = [9, 7, 5, 3, 1, 0, 2, 4, 6, 8]
        for (int i = 0; i < N; i += 1) {
            if (i % 2 == 0) {
                AD.addLast(i);
            } else {
                AD.addFirst(i);
            }
        }
        int expectedSize = 10;
        int actualSize = AD.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testGetAddResize() {
        ArrayDeque<Integer> AD = new ArrayDeque();

        int N = 10;

        //conceptual array = [9, 7, 5, 3, 1, 0, 2, 4, 6, 8]
        for (int i = 0; i < N; i += 1) {
            if (i % 2 == 0) {
                AD.addLast(i);
            } else {
                AD.addFirst(i);
            }
        }

        int expectedIndex0 = 9;
        int actualIndex0 = AD.get(0);
        int expectedIndex1 = 7;
        int actualIndex1 = AD.get(1);
        int expectedIndex4 = 1;
        int actualIndex4 = AD.get(4);
        int expectedIndex6 = 2;
        int actualIndex6 = AD.get(6);
        int expectedIndex9 = 8;
        int actualIndex9 = AD.get(9);

        assertEquals(expectedIndex0, actualIndex0);
        assertEquals(expectedIndex1, actualIndex1);
        assertEquals(expectedIndex4, actualIndex4);
        assertEquals(expectedIndex6, actualIndex6);
        assertEquals(expectedIndex9, actualIndex9);
    }

    /** Tests insertion of a large number of items.*/
    @Test
    public void testMegaAddFirst() {
        ArrayDeque<Integer> AD = new ArrayDeque();
        int N = 1000000;
        for (int i = 0; i < N; i += 1) {
            AD.addFirst(i);
        }

        for (int i = 0; i < N; i += 1) {
            AD.addFirst(AD.get(i));
        }
    }

    /** Tests insertion of a large number of items.*/
    @Test
    public void testMegaAddLast() {
        ArrayDeque<Integer> AD = new ArrayDeque();
        int N = 1000000;
        for (int i = 0; i < N; i += 1) {
            AD.addLast(i);
        }

        for (int i = 0; i < N; i += 1) {
            AD.addLast(AD.get(i));
        }
    }
    @Test
    public void testRemoveLast() {
        ArrayDeque<Integer> AD = new ArrayDeque<>();
        AD.addLast(99);
        int expected99 = 99;
        int actual99 = AD.get(0);
        assertEquals(expected99, actual99);

        AD.addLast(36);
        AD.removeLast();

        actual99 = AD.removeLast();

        assertEquals(expected99, actual99);

        AD.addLast(100);

        int expected100 = 100;
        int actual100 = AD.removeLast();
        assertEquals(expected100, actual100);
    }
}
