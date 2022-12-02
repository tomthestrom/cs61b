package deque;

import java.util.Comparator;

/**
 *  Returns the requested comparator -> getComparator(requestedComparator)
 */
public class ComparatorFactory  {

    /**
     * Compares 2 ints
     */
    private static class IntComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer integer, Integer t1) {
            return integer - t1;
        }
    }

    private static class StrLenComparator implements Comparator<String> {
        @Override
        public int compare(String string, String t1) {
            return string.length() - t1.length();
        }
    }

    private static class StrCharSumComparator implements Comparator<String> {
        @Override
        public int compare(String string, String t1) {
            int firstStringSum = 0;
            int t1StringSum = 0;

            for (int i = 0; i < string.length(); i++) {
                firstStringSum += Character.codePointAt(string, i);
            }

            for (int i = 0; i < t1.length(); i++) {
                t1StringSum += Character.codePointAt(t1, i);
            }
            return firstStringSum - t1StringSum;
        }
    }

    /**
     * Returns a comparator Object based on the provided parameter
     * @throws Exception
     */
    public static Comparator getComparator(String type) throws Exception {
       switch (type) {
           case "int":
               return new IntComparator();
           case "strLen":
               return new StrLenComparator();
           case "charSum":
               return new StrCharSumComparator();
           default:
               throw new Exception("Invalid comparator name");
       }
    }
}
