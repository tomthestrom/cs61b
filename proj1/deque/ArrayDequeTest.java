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
}
