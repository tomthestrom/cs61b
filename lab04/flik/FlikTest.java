package flik;

import org.junit.Test;
import static org.junit.Assert.*;

public class FlikTest {
    @Test
    public void testIsSameNumber() {
        assertTrue(Flik.isSameNumber(4, 4));
        assertTrue(Flik.isSameNumber(128, 128));

        assertFalse(Flik.isSameNumber(5, 4));
    }
}
