package byow.Core;

public final class GridMathUtils {
    private GridMathUtils() {

    }

    public static double euclideanDistance(GridCoords coords1, GridCoords coords2) {
        return Math.sqrt(Math.pow((coords2.x() - coords1.x()), 2.0) + Math.pow((coords2.y() - coords1.y()), 2.0));
    }

    /**
     * Converts an x,y index in a 2D array to an index in a 1D array
     * @param coords
     * @return
     */
    public static int coordsTo1D(GridCoords coords, int gridWidth) {
       return coords.y() * gridWidth + coords.x();
    }

    public static int getAxisCenter(int axisMin, int axisMax) {
        return ((axisMax - axisMin) / 2) + axisMin;
    }

    /**
     * Returns the GridCoords obj that is closer to the target
     * @param target
     * @param coords1
     * @param coords2
     * @return
     */
    public static GridCoords getClosestCoord(GridCoords target, GridCoords coords1, GridCoords coords2) {
       double dist1 = GridMathUtils.euclideanDistance(target, coords1);
       double dist2 = GridMathUtils.euclideanDistance(target, coords2);


       return dist1 < dist2 ? coords1 : coords2;
    }
}
