package deque;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest {
    @Test
    public void testMaxNoParam() throws Exception {
        Comparator intComparator = ComparatorFactory.getComparator("int");

        MaxArrayDeque<Integer> MAD1 = new MaxArrayDeque<>(intComparator);
        MaxArrayDeque<Integer> MAD2 = new MaxArrayDeque<>(intComparator);

        for (int i = 0; i < 100; i += 1) {
            if (i % 2 == 0) {
                MAD1.addLast(i);
            } else {
                MAD2.addFirst(i);
            }
        }

        int expectedMaxMAD1 = 98;
        int actualMaxMAD1 = MAD1.max();

        int expectedMaxMAD2 = 99;
        int actualMaxMAD2 = MAD2.max();

        assertEquals(expectedMaxMAD1, actualMaxMAD1);
        assertEquals(expectedMaxMAD2, actualMaxMAD2);
    }

    @Test
    public void testMaxParams() throws Exception {
        Comparator strLen = ComparatorFactory.getComparator("strLen");
        Comparator charSum = ComparatorFactory.getComparator("charSum");

        MaxArrayDeque<String> MAD1 = new MaxArrayDeque<>(strLen);
        MaxArrayDeque<String> MAD2 = new MaxArrayDeque<>(strLen);

        String[] randomWords = {"tri", "hala", "bala", "mala", "Pala", "XXXX", "YYYY",
                "Katka", "dezo", "fero", "jozo", "a"};

        //maxCharSum = "Katka"
        for (int i = 0; i < randomWords.length; i++) {
            MAD1.addLast(randomWords[i]);
        }

        //maxCharSum = "mala"
        for (int i = 0; i < 5; i++) {
            MAD2.addLast(randomWords[i]);
        }

        String expMaxCharSumMAD1 = "Katka";
        String actMaxCharSumMAD1 = MAD1.max(charSum);
        String expMaxCharSumMAD2 = "mala";
        String actMaxCharSumMAD2 = MAD2.max(charSum);

        assertEquals(expMaxCharSumMAD1, actMaxCharSumMAD1);
        assertEquals(expMaxCharSumMAD2, actMaxCharSumMAD2);

        int indexLastTwoLeft = MAD1.size() - 2;

        for (int i = 0; i < indexLastTwoLeft; i++) {
           MAD1.removeFirst();
        }
        //arraydeque = {"jozo", "a"}
        String expMaxLenMAD1 = "jozo";
        String actMaxLenMAD1 = MAD1.max();

        for (int i = 0; i < randomWords.length; i++) {
            MAD2.addLast(randomWords[i]);
        }

        String expMaxLenMAD2 = "Katka";
        String actMaxLenMAD2 = MAD2.max();

        assertEquals(expMaxLenMAD1, actMaxLenMAD1);
        assertEquals(expMaxLenMAD2, actMaxLenMAD2);
    }
}
