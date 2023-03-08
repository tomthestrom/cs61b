package byow.Core;

import byow.Core.Corridor.*;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

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

    public static void drawTileAtCoords(TETile[][] grid, TETile type, GridCoords coords) {
        grid[coords.x()][coords.y()] = type;
    }

    public static void drawTileAtMultipleCoords(TETile[][] grid, TETile type, GridCoords[] multCoords) {
        for (GridCoords coords : multCoords) {
            grid[coords.x()][coords.y()] = type;
        }
    }

    public static void drawCorridor(TETile[][] grid, Corridor corridor) {
        for (CorridorTile corridorTile : corridor) {
            corridorTile.drawTile(grid);
        }
    }
}
