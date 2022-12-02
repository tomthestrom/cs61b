package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.HashMap;
import static org.junit.Assert.*;

/* Performs ArrayDeque tests. */
public class ArrayDequeTest<T> {

    /**
     * Helper HashMap for the randomized test
     */
    private HashMap<Integer, Boolean> SizeNonZeroOperation;
    public ArrayDequeTest() {
        //some operations can't be executed if AList.size() == 0
        SizeNonZeroOperation = new HashMap<>();

        // addLast
        SizeNonZeroOperation.put(0, false);
        // size
        SizeNonZeroOperation.put(1, false);
        // getLast
        SizeNonZeroOperation.put(2, true);
        // removeLast
        SizeNonZeroOperation.put(3, true);
        //get item in the middle
        SizeNonZeroOperation.put(4, true);
        //removeFirst
        SizeNonZeroOperation.put(5, true);
    }
    @Test
    public void testAddFirstNoResizing() {
        ArrayDeque<Integer> AD1 = new ArrayDeque<>();

        AD1.addFirst(1);
        AD1.addFirst(2);
        AD1.addFirst(3);
        AD1.addFirst(4);
        AD1.addFirst(5);

        int expected = 5;
        int actual = AD1.get(0);

        assertEquals(expected, actual);
    }

    @Test
    public void testAddLastNoResizing() {
        ArrayDeque<Integer> AD1 = new ArrayDeque<>();

        AD1.addLast(1);
        AD1.addLast(2);

        int expected = 2;
        int actual = AD1.get(1);

        assertEquals(expected, actual);
    }

    @Test
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


        int expected3 = 2;
        int actual3 = AD1.get(3);

        assertEquals(expected3, actual3);
    }

    @Test
    public void testSize() {
        ArrayDeque<Integer> AD = new ArrayDeque();

        int N = 10;

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

    @Test
    public void testRemoveFirstResize() {
        int addAmount = 40;
        ArrayDeque<Integer> AD = new ArrayDeque<>();

        for (int i = 0; i < addAmount; i += 1) {
            AD.addFirst(i);
        }
        AD.printDeque();

        for (int i = 0; i < 20; i += 1) {
            AD.removeFirst();
        }
        int expected19 = 19;
        int actual19 = AD.get(0);

        AD.printDeque();
        assertEquals(expected19, actual19);

        for (int i = 0; i < 16; i += 1) {
            AD.removeFirst();
        }

        int expected3 = 3;
        int actual3 = AD.get(0);
        AD.printDeque();
        assertEquals(expected3, actual3);
    }

    @Test
    /*
     * Randomly add, remove and compare values of LinkedListDeque and ArrayDeque for N nr. of operations
     */
    public void randomizedTest() {
        LinkedListDeque<Integer> L = new LinkedListDeque<>();
        ArrayDeque<Integer> AD = new ArrayDeque<>();


        int N = 50000;
        for (int i = 0; i < N; i += 1) {
            //operation nrs generated in provided range:
            int operationNumber = StdRandom.uniform(0, 5);

            // if operation can't be executed proceed to the next iteration
            if (L.size() == 0 && SizeNonZeroOperation.get(operationNumber)) {
                continue;
            }

            switch (operationNumber) {
                case 0 -> {
                    int randVal = StdRandom.uniform(0, 100);
                    L.addLast(randVal);
                    AD.addLast(randVal);
                }
                case 1 -> {
                    int sizeL = L.size();
                    int sizeAD = AD.size();
                    assertEquals(sizeL, sizeAD);
                }
                case 2 -> {
                    int lastValL = L.get(L.size() - 1);
                    int lastValAD = AD.get(AD.size() - 1);
                    assertEquals(lastValL, lastValAD);
                }
                case 3 -> {
                    int lastValL = L.removeLast();
                    int lastValAD = AD.removeLast();
                    assertEquals(lastValL, lastValAD);
                }
                case 4 -> {
                    int firstValL = L.removeFirst();
                    int firstValAD = AD.removeFirst();
                    assertEquals(firstValL, firstValAD);
                }
                case 5 -> {
                    int middleValL = L.get(L.size() / 2);
                    int middleValAD = AD.get(AD.size() / 2);
                    assertEquals(middleValL, middleValAD);
                }
                default -> System.out.println("Operation nr " + operationNumber + " not implemented.");
            }
        }
    }

    @Test
    public void testEquals() {
        ArrayDeque<Integer> AD1 = new ArrayDeque<>();
        ArrayDeque<Integer> AD2 = new ArrayDeque<>();

        for (int i = 0; i < 100; i += 1) {
            if (i % 2 == 0) {
                AD1.addLast(i);
                AD2.addLast(i);
            } else {
                AD1.addFirst(i);
                AD2.addFirst(i);
            }
        }

        assertTrue(AD1.equals(AD2));

        AD1.removeFirst();

        assertFalse(AD1.equals(AD2));
    }
    @Test
    public void testIterator() {
        int N = 1000;
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 0; i < N; i++) {
            ad1.addLast(i);
        }

        int expected = 0;
        for (int i: ad1) {
            assertEquals(expected, i);
            expected++;
        }

        //check that expected reached N - all iterations ran
        assertEquals(N, expected);
    }
}
