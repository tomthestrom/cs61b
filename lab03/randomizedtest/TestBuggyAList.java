package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE

    /*
     * Simple comparison test
     * Test compares the results from performing the same operation on 2 classes:
     *  Class AListNoResizing vs TestBuggyAList
     */
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing <Integer> ALNR = new AListNoResizing<>();
        BuggyAList <Integer> BAL = new BuggyAList<>();

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
}
