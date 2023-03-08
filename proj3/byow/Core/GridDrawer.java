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
            GridDrawer.drawCorridorTile(grid, corridor);
        }
    }

    /**
     * Draw Bottom To Left Corridor Tile:
        FLOOR WALL
        WALL  WALL
     * @param grid
     * @param corridorTile
     */
    private static void drawCorridorTile(TETile[][] grid, BottomToLeft corridorTile) {
        GridCoords floor = corridorTile.getCoords();

        GridCoords wallRight = floor.getNextInDirection(Direction.RIGHT);
        GridCoords wallCorner = wallRight.getNextInDirection(Direction.DOWN);
        GridCoords wallUnder = floor.getNextInDirection(Direction.DOWN);

        GridCoords[] wallCoords = {wallRight, wallUnder, wallCorner};

        drawTileAtCoords(grid, Tileset.FLOOR, floor);
        drawTileAtMultipleCoords(grid, Tileset.WALL, wallCoords);
    }

    /**
     * Draw Bottom To Left Corridor Tile:
     WALL FLOOR
     WALL  WALL
     * @param grid
     * @param corridorTile
     */
    private static void drawCorridorTile(TETile[][] grid, BottomToRight corridorTile) {
        GridCoords floor = corridorTile.getCoords();

        GridCoords wallLeft = floor.getNextInDirection(Direction.LEFT);
        GridCoords wallCorner = wallLeft.getNextInDirection(Direction.DOWN);
        GridCoords wallUnder = floor.getNextInDirection(Direction.DOWN);

        GridCoords[] wallCoords = {wallLeft, wallUnder, wallCorner};

        drawTileAtCoords(grid, Tileset.FLOOR, floor);
        drawTileAtMultipleCoords(grid, Tileset.WALL, wallCoords);
    }

    /**
     * Draw Bottom To Left Corridor Tile:
     WALL WALL
     WALL  FLOOR
     * @param grid
     * @param corridorTile
     */
    private static void drawCorridorTile(TETile[][] grid, LeftToBottom corridorTile) {
        GridCoords floor = corridorTile.getCoords();

        GridCoords wallLeft = floor.getNextInDirection(Direction.LEFT);
        GridCoords wallCorner = wallLeft.getNextInDirection(Direction.UP);
        GridCoords wallAbove = floor.getNextInDirection(Direction.UP);

        GridCoords[] wallCoords = {wallLeft, wallAbove, wallCorner};

        drawTileAtCoords(grid, Tileset.FLOOR, floor);
        drawTileAtMultipleCoords(grid, Tileset.WALL, wallCoords);
    }

    /**
     * Draw Left To Top Corridor Tile:
     WALL FLOOR
     WALL  WALL
     * @param grid
     * @param corridorTile
     */
    private static void drawCorridorTile(TETile[][] grid, LeftToTop corridorTile) {
        GridCoords floor = corridorTile.getCoords();

        GridCoords wallLeft = floor.getNextInDirection(Direction.LEFT);
        GridCoords wallCorner = wallLeft.getNextInDirection(Direction.DOWN);
        GridCoords wallUnder = floor.getNextInDirection(Direction.DOWN);

        GridCoords[] wallCoords = {wallLeft, wallUnder, wallCorner};

        drawTileAtCoords(grid, Tileset.FLOOR, floor);
        drawTileAtMultipleCoords(grid, Tileset.WALL, wallCoords);
    }

    /**
     * Draw Right to Bottom Corridor Tile:
     WALL WALL
     FLOOR  WALL
     * @param grid
     * @param corridorTile
     */
    private static void drawCorridorTile(TETile[][] grid, RightToBottom corridorTile) {
        GridCoords floor = corridorTile.getCoords();

        GridCoords wallRight = floor.getNextInDirection(Direction.RIGHT);
        GridCoords wallCorner = wallRight.getNextInDirection(Direction.UP);
        GridCoords wallAbove = floor.getNextInDirection(Direction.UP);

        GridCoords[] wallCoords = {wallRight, wallAbove, wallCorner};

        drawTileAtCoords(grid, Tileset.FLOOR, floor);
        drawTileAtMultipleCoords(grid, Tileset.WALL, wallCoords);
    }

    /**
     * Draw Right To Top Corridor Tile:
     FLOOR WALL
     WALL  WALL
     * @param grid
     * @param corridorTile
     */
    private static void drawCorridorTile(TETile[][] grid, RightToTop corridorTile) {
        GridCoords floor = corridorTile.getCoords();

        GridCoords wallRight = floor.getNextInDirection(Direction.RIGHT);
        GridCoords wallCorner = wallRight.getNextInDirection(Direction.DOWN);
        GridCoords wallUnder = floor.getNextInDirection(Direction.DOWN);

        GridCoords[] wallCoords = {wallRight, wallUnder, wallCorner};

        drawTileAtCoords(grid, Tileset.FLOOR, floor);
        drawTileAtMultipleCoords(grid, Tileset.WALL, wallCoords);
    }

    /**
     * Draw Simple Vertical Corridor Tile:
     WALL FLOOR WALL
     * @param grid
     * @param corridorTile
     */
    private static void drawCorridorTile(TETile[][] grid, SimpleVertical corridorTile) {
        GridCoords floor = corridorTile.getCoords();

        GridCoords wallRight = floor.getNextInDirection(Direction.RIGHT);
        GridCoords wallLeft = floor.getNextInDirection(Direction.LEFT);

        GridCoords[] wallCoords = {wallRight, wallLeft};

        drawTileAtCoords(grid, Tileset.FLOOR, floor);
        drawTileAtMultipleCoords(grid, Tileset.WALL, wallCoords);
    }

    /**
     * Draw Simple Horizontal Corridor Tile:
     WALL
     FLOOR
     WALL
     * @param grid
     * @param corridorTile
     */
    private static void drawCorridorTile(TETile[][] grid, SimpleHorizontal corridorTile) {
        GridCoords floor = corridorTile.getCoords();

        GridCoords wallAbove = floor.getNextInDirection(Direction.UP);
        GridCoords wallUnder = floor.getNextInDirection(Direction.DOWN);

        GridCoords[] wallCoords = {wallAbove, wallUnder};

        drawTileAtCoords(grid, Tileset.FLOOR, floor);
        drawTileAtMultipleCoords(grid, Tileset.WALL, wallCoords);
    }

    /**
     * Draw Top To Left Corridor Tile:
     WALL WALL
     FLOOR  WALL
     * @param grid
     * @param corridorTile
     */
    private static void drawCorridorTile(TETile[][] grid, TopToLeft corridorTile) {
        GridCoords floor = corridorTile.getCoords();

        GridCoords wallRight = floor.getNextInDirection(Direction.RIGHT);
        GridCoords wallCorner = wallRight.getNextInDirection(Direction.UP);
        GridCoords wallAbove = floor.getNextInDirection(Direction.UP);

        GridCoords[] wallCoords = {wallRight, wallAbove, wallCorner};

        drawTileAtCoords(grid, Tileset.FLOOR, floor);
        drawTileAtMultipleCoords(grid, Tileset.WALL, wallCoords);
    }

    /**
     * Draw Top To Right Corridor Tile:
     WALL WALL
     WALL  FLOOR
     * @param grid
     * @param corridorTile
     */
    private static void drawCorridorTile(TETile[][] grid, TopToRight corridorTile) {
        GridCoords floor = corridorTile.getCoords();

        GridCoords wallLeft = floor.getNextInDirection(Direction.LEFT);
        GridCoords wallCorner = wallLeft.getNextInDirection(Direction.UP);
        GridCoords wallAbove = floor.getNextInDirection(Direction.UP);

        GridCoords[] wallCoords = {wallLeft, wallAbove, wallCorner};

        drawTileAtCoords(grid, Tileset.FLOOR, floor);
        drawTileAtMultipleCoords(grid, Tileset.WALL, wallCoords);
    }
}
