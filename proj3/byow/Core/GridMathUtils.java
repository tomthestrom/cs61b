package byow.Core;

public final class GridMathUtils {
    private GridMathUtils() {

    }

    public static double euclideanDistance(GridCoords coords1, GridCoords coords2) {
        return Math.sqrt(Math.pow((coords2.x() - coords1.x()), 2.0) + Math.pow((coords2.y() - coords1.y()), 2.0));
    }
}
