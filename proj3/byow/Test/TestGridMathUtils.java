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

    @Test
    public void testCoordsTo1D() {
        GridCoords coords1 = new GridCoords(5, 10);
        GridCoords coords2 = new GridCoords(30, 14);
        GridCoords coords3 = new GridCoords(0, 0);

        int gridWidth = 50;

        int expected1 = 505;
        int expected2 = 730;
        int expected3 = 0;


        assertEquals(expected1, GridMathUtils.coordsTo1D(coords1, gridWidth));
        assertEquals(expected2, GridMathUtils.coordsTo1D(coords2, gridWidth));
        assertEquals(expected3, GridMathUtils.coordsTo1D(coords3, gridWidth));

    }
}
