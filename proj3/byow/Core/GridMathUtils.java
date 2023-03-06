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
}
