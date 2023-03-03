package byow.Core;

import byow.TileEngine.TETile;

public final class GridDrawer {
    private GridDrawer() {

    }

    /**
     * Fills the worldGrid between xMin, xMax, yMin, yMax
     * @param type
     * @param xMin
     * @param xMax
     * @param yMin
     * @param yMax
     */
    public static void fillRectangle(TETile[][] grid, TETile type, int xMin, int xMax, int yMin, int yMax) {
        for (int x = xMin; x < xMax; x += 1) {
            for (int y = yMin; y < yMax; y += 1) {
                grid [x][y] = type;
            }
        }
    }

    /**
     * Fills the worldGrid with type tiles along X axis
     * @param grid
     * @param type
     * @param x
     * @param yMin
     * @param yMax
     */
    public static void drawLineAtX(TETile[][] grid, TETile type, int x, int yMin, int yMax) {
        for (int y = yMin; y < yMax; y += 1) {
            grid[x][y] = type;
        }
    }

    /**
     * Fills the worldGrid with type tiles along Y axis
     * @param grid
     * @param type
     * @param y
     * @param xMin
     * @param xMax
     */
    public static void drawLineAtY(TETile[][] grid, TETile type, int y, int xMin, int xMax) {
        for (int x = xMin; x < xMax; x += 1) {
            grid[x][y] = type;
        }
    }
}
