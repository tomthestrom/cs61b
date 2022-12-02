package deque;

import java.util.Comparator;

/**
 * Can either tell you the max element in itself by using the Comparator<T> given to it in the constructor via max(),
 * or an arbitrary Comparator<T> that is different from the one given in the constructor via max(c).
 *
 * @param <T>
 */
public class MaxArrayDeque<T> extends ArrayDeque<T> {
    Comparator<T> comparator;

    MaxArrayDeque(Comparator<T> c) {
        comparator = c;
    }

    /*
    * Returns the maximum element in the deque as governed by the comparator;
    */
    public T max() {
        return maxHelper(comparator);
    }

    /*
     * Returns the maximum element in the deque as given by the parameter Comparator c
     */
    public T max(Comparator<T> c) {
        return maxHelper(c);
    }

    /**
     * Iterates over elements applying the compare function of the passed in Comparator
     * @param c
     */
    private T maxHelper(Comparator<T> c) {
        T maxEl = get(0);

        for (int i = 1; i < size(); i++) {
            if (c.compare(get(i), maxEl) > 0) {
                maxEl = get(i);
            }
        }

        return maxEl;
    }
}
