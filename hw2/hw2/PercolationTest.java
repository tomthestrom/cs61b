package hw2;

import org.junit.Test;
import static org.junit.Assert.*;

public class PercolationTest {
    @Test
    public void isOpenTest() {
        Percolation p1 = new Percolation(3);

        p1.open(0,0);
        p1.open(1,2);

        assertTrue(p1.isOpen(0, 0));
        assertTrue(p1.isOpen(1, 2));
        assertFalse(p1.isOpen(0, 1));
        assertFalse(p1.isOpen(2, 2));
    }

    @Test
    public void isFullTest() {
        Percolation p1 = new Percolation(3);

        p1.open(0,0);
        p1.open(1,0);
        p1.open(1,1);

        assertTrue(p1.isFull(0, 0));
        assertTrue(p1.isFull(1, 0));
        assertTrue(p1.isFull(1, 1));
        assertFalse(p1.isFull(0, 1));
        assertFalse(p1.isFull(2, 2));
    }

    @Test
    public void numberOfOpenSitesTest() {
        int N = 9;
        Percolation p1 = new Percolation(N);

        for (int i = 0; i < N; i++) {
            p1.open(i, 5);
        }

        int actual = p1.numberOfOpenSites();

        assertEquals(N, actual);
    }

    @Test
    public void percolatesTest() {
        int N = 9;
        Percolation p1 = new Percolation(N);

        for (int i = 0; i < N; i++) {
            p1.open(i, 5);
        }

        Percolation p2 = new Percolation(N);

        for (int i = 0; i < N; i++) {
            p1.open(5, i);
        }
        assertTrue(p1.percolates());
        assertFalse(p2.percolates());

        Percolation p3 = new Percolation(N);

        for (int i = 0; i < N - 2; i++) {
            p3.open(i, 5);
        }

        assertFalse(p3.percolates());

        p3.open(N - 3, 6);
        p3.open(N - 2, 6);
        p3.open(N - 1, 6);

        assertTrue(p3.percolates());
    }

}
