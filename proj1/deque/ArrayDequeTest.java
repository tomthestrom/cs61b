package deque;

import org.junit.Test;

import static org.junit.Assert.*;

/* Performs ArrayDeque tests. */
public class ArrayDequeTest<T> {
    @Test
    public void testAddFirstNoResizing() {
        ArrayDeque<Integer> AD1 = new ArrayDeque<>();

        AD1.addFirst(1);
        AD1.addFirst(2);

        int expected = 1;
        int actual = AD1.get(1);

        assertEquals(expected, actual);

        AD1.addFirst(4);

        expected = 4;
        actual = AD1.get(0);
        assertEquals(expected, actual);

        AD1.addFirst(5);
        expected = 4;
        actual = AD1.get(3);
        assertEquals(expected, actual);
    }

    @Test
    /**
     * After last call to addLast:
     * Conceptual array: [1, 2, 4, 5]
     * In underlying implementation: [5, 4, 2, 1]
     */
    public void testAddLastNoResizing() {
        ArrayDeque<Integer> AD1 = new ArrayDeque<>();

        AD1.addLast(1);
        AD1.addLast(2);

        int expected = 2;
        int actual = AD1.get(1);

        assertEquals(expected, actual);

        AD1.addLast(4);

        expected = 1;
        actual = AD1.get(0);
        assertEquals(expected, actual);

        AD1.addLast(5);
        expected = 5;
        actual = AD1.get(3);
        assertEquals(expected, actual);
    }

    @Test
    /**
     * After last add operation:
     * Conceptual array: [0, 1, 2, 3]
     * In underlying implementation:
     * firstItems: [1, 0, null, null]
     * lastItems: [null, null, 2, 3]
     */
    public void testGetNoResize() {
        ArrayDeque<Integer> AD1 = new ArrayDeque<>();

        AD1.addLast(3);
        AD1.addLast(2);
        AD1.addFirst(1);

        int expected1 = 1;
        int actual1 = AD1.get(0);

        assertEquals(expected1, actual1);

        AD1.addFirst(0);

        int expected0 = 0;
        int actual0 = AD1.get(0);

        assertEquals(expected0, actual0);


        int expected3 = 3;
        int actual3 = AD1.get(3);

        assertEquals(expected3, actual3);
    }
}
