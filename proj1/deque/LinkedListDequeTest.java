package deque;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Performs some basic linked list tests.
 */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double> lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }

    @Test
    public void testGet() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();

        lld1.addFirst(5);
        lld1.addFirst(4);
        lld1.addFirst(3);
        lld1.addFirst(2);
        lld1.addFirst(1);
        lld1.addFirst(0);

        lld1.addLast(6);
        lld1.addLast(7);
        lld1.addLast(8);

        int actualIndex0 = lld1.get(0);
        int expectedIndex0 = 0;

        int actualIndex1 = lld1.get(1);
        int expectedIndex1 = 1;

        int actualIndex2 = lld1.get(2);
        int expectedIndex2 = 2;

        int actualIndex3 = lld1.get(3);
        int expectedIndex3 = 3;

        int actualIndex4 = lld1.get(4);
        int expectedIndex4 = 4;

        int actualIndex6 = lld1.get(6);
        int expectedIndex6 = 6;

        int actualIndex8 = lld1.get(8);
        int expectedIndex8 = 8;

        assertEquals(expectedIndex0, actualIndex0);
        assertEquals(expectedIndex1, actualIndex1);
        assertEquals(expectedIndex2, actualIndex2);
        assertEquals(expectedIndex3, actualIndex3);
        //test if getting the middle element works
        assertEquals(expectedIndex4, actualIndex4);
        //check if getting elements from the back works
        assertEquals(expectedIndex6, actualIndex6);
        assertEquals(expectedIndex8, actualIndex8);
    }

    @Test
    public void testGetRecursive() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();

        lld1.addFirst(5);
        lld1.addFirst(4);
        lld1.addFirst(3);
        lld1.addFirst(2);
        lld1.addFirst(1);
        lld1.addFirst(0);

        lld1.addLast(6);
        lld1.addLast(7);
        lld1.addLast(8);

        int actualIndex0 = lld1.getRecursive(0);
        int expectedIndex0 = 0;

        int actualIndex1 = lld1.getRecursive(1);
        int expectedIndex1 = 1;

        int actualIndex2 = lld1.getRecursive(2);
        int expectedIndex2 = 2;

        int actualIndex3 = lld1.getRecursive(3);
        int expectedIndex3 = 3;

        int actualIndex4 = lld1.getRecursive(4);
        int expectedIndex4 = 4;

        int actualIndex6 = lld1.getRecursive(6);
        int expectedIndex6 = 6;

        int actualIndex8 = lld1.getRecursive(8);
        int expectedIndex8 = 8;

        assertEquals(expectedIndex0, actualIndex0);
        assertEquals(expectedIndex1, actualIndex1);
        assertEquals(expectedIndex2, actualIndex2);
        assertEquals(expectedIndex3, actualIndex3);
        //test if getting the middle element works
        assertEquals(expectedIndex4, actualIndex4);
        //check if getting elements from the back works
        assertEquals(expectedIndex6, actualIndex6);
        assertEquals(expectedIndex8, actualIndex8);
    }

    @Test
    public void testEquals() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        LinkedListDeque<Integer> lld2 = new LinkedListDeque<>();
        lld1.addFirst(5);
        lld1.addFirst(4);
        lld1.addFirst(3);
        lld2.addFirst(5);
        lld2.addFirst(4);
        lld2.addFirst(3);

        assertTrue(lld1.equals(lld2));

        lld2.addFirst(1);

        assertFalse(lld1.equals(lld2));

        lld1.addFirst(1);

        assertTrue(lld1.equals(lld2));
    }
}
