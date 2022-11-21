package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE
    private HashMap<Integer, Boolean> SizeNonZeroOperation;
    public TestBuggyAList() {
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
    }

    /*
     * Simple comparison test
     * Test compares the results from performing the same operation on 2 classes:
     *  Class AListNoResizing vs TestBuggyAList
     */
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> ALNR = new AListNoResizing<>();
        BuggyAList<Integer> BAL = new BuggyAList<>();

        int testVal1 = 3;
        int testVal2 = 4;
        int testVal3 = 5;

        ALNR.addLast(testVal1);
        ALNR.addLast(testVal2);
        ALNR.addLast(testVal3);

        BAL.addLast(testVal1);
        BAL.addLast(testVal2);
        BAL.addLast(testVal3);


        assertEquals(ALNR.removeLast(), BAL.removeLast());
        assertEquals(ALNR.removeLast(), BAL.removeLast());
        assertEquals(ALNR.removeLast(), BAL.removeLast());
    }


    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> BL = new BuggyAList<>();


        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);

            // if operation can't be executed proceed to the next iteration
            if (L.size() == 0 && SizeNonZeroOperation.get(operationNumber)) {
                continue;
            }

            switch (operationNumber) {
                case 0 -> {
                    int randVal = StdRandom.uniform(0, 100);
                    L.addLast(randVal);
                    BL.addLast(randVal);
                    System.out.println("L, BL, addLast(" + randVal + ")");
                }
                case 1 -> {
                    int sizeL = L.size();
                    int sizeBL = BL.size();
                    System.out.println("L.size: " + sizeL);
                    System.out.println("BL.size: " + sizeBL);

                    assertEquals(sizeL, sizeBL);
                }
                case 2 -> {
                    int lastValL = L.getLast();
                    int lastValBL = BL.getLast();

                    System.out.println("L.getLast() -> " + lastValL);
                    System.out.println("BL.getLast() -> " + lastValBL);

                    assertEquals(lastValL, lastValBL);
                }
                case 3 -> {
                    int lastValL = L.removeLast();
                    int lastValBL = BL.removeLast();;
                    System.out.println("L.removeLast() -> " + lastValL);
                    System.out.println("BL.removeLast() -> " + lastValBL);

                    assertEquals(lastValL, lastValBL);
                }
                default -> System.out.println("Operation nr " + operationNumber + " not implemented.");
            }
        }
    }
}
