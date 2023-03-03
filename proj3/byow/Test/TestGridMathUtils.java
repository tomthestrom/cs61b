package byow.Test;
import byow.Core.GridCoords;
import byow.Core.GridMathUtils;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestGridMathUtils {
    @Test
    public void testEuclideanDistance() {
        GridCoords coords1 = new GridCoords(5, 10);
        GridCoords coords2 = new GridCoords(60, 14);

        Double expected = 55.1453;
        Double epsilon = 0.0001;

        assertEquals(expected, GridMathUtils.euclideanDistance(coords1, coords2), epsilon);
    }
}
