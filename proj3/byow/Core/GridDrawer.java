package byow.Core;

import byow.Core.Corridor.*;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;

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

    public static TETile getTileAtCoords(TETile[][] grid, GridCoords coords) {
        return grid[coords.x()][coords.y()];
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

    /**
     * Fills Nothing tiles with given tyle
     * @param grid
     * @param type
     * @param minY
     * @param maxY
     */
    public static void fillNothingWithTileBetweenY(TETile[][] grid, TETile type, int minY, int maxY) {
        for (int x = 0; x < Engine.WIDTH; x += 1) {
            for (int y = minY; y < maxY; y += 1) {
                if (grid[x][y] == Tileset.NOTHING) {
                    grid [x][y] = type;
                }
            }
        }
    }

    /**
     * Draw a room defined by it's walls given by xmin/max ymin/max - fill with floor inbetween
     * @param grid
     * @param room
     */
    public static void drawRoom(TETile[][] grid, Room room) {
        for (int x = room.getxMin(); x <= room.getxMax(); x++) {
            for (int y = room.getyMin(); y <= room.getyMax(); y++) {
                boolean fillWall = (x == room.getxMin() || x == room.getxMax()) ||
                        (y == room.getyMin() || y == room.getyMax());
                if (fillWall) {
                    grid[x][y] = Tileset.WALL;
                } else {
                    grid[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    /**
     * Draw rooms from the list of rooms
     * @param grid
     * @param roomList
     */
    public static void drawRooms(TETile[][] grid, List<Room> roomList) {
        for (Room room : roomList) {
            drawRoom(grid, room);
        }
    }

    /**
     * Removes walls between corridors that are parallel
     * Some maps get rendered with a 1 or 2 NOTHING tiles instead of a WALL tile -> HOTFIX
     * Well, remove walls first, than check for NOTHING tiles, in case remove walls screws something up :)
     * @param grid
     */
    public static void mapPostProcessor(TETile[][] grid) {
        for (int x = 1; x < grid.length - 1; x++) {
            for (int y = 1; y < grid[0].length - 1; y++) {
                GridCoords curCoords = new GridCoords(x, y);
                GridCoords left = curCoords.getNextInDirection(Direction.LEFT);
                GridCoords right = curCoords.getNextInDirection(Direction.RIGHT);
                GridCoords up = curCoords.getNextInDirection(Direction.UP);
                GridCoords down = curCoords.getNextInDirection(Direction.DOWN);

             if (getTileAtCoords(grid, curCoords) == Tileset.WALL) {

                //wall and left and right floor
                if (getTileAtCoords(grid, left) == Tileset.FLOOR &&
                        getTileAtCoords(grid, right) == Tileset.FLOOR) {
                    drawTileAtCoords(grid, Tileset.FLOOR, curCoords);
                }

                //wall and up and down floor
                if (getTileAtCoords(grid, up) == Tileset.FLOOR &&
                        getTileAtCoords(grid, down) == Tileset.FLOOR) {
                    drawTileAtCoords(grid, Tileset.FLOOR, curCoords);
                }
                //in case there are two walls next to each other - remove them
                GridCoords rightOfRight =  right.getNextInDirection(Direction.RIGHT);
                GridCoords upOfUp =  up.getNextInDirection(Direction.UP);
                if (getTileAtCoords(grid, right) == Tileset.WALL &&
                        getTileAtCoords(grid, rightOfRight) == Tileset.WALL &&
                        getTileAtCoords(grid, rightOfRight.getNextInDirection(Direction.RIGHT)) == Tileset.FLOOR) {
                    //remove both walls
                    drawTileAtCoords(grid, Tileset.FLOOR, right);
                    drawTileAtCoords(grid, Tileset.FLOOR, rightOfRight);
                }

                if (getTileAtCoords(grid, up) == Tileset.WALL &&
                        getTileAtCoords(grid, upOfUp) == Tileset.WALL &&
                        getTileAtCoords(grid, upOfUp.getNextInDirection(Direction.UP)) == Tileset.FLOOR) {
                    //remove both walls
                    drawTileAtCoords(grid, Tileset.FLOOR, up);
                    drawTileAtCoords(grid, Tileset.FLOOR, upOfUp);
                }
            } else if (getTileAtCoords(grid, curCoords) == Tileset.FLOOR) {

                    //if space above floor tile empty
                   if (getTileAtCoords(grid, up) == Tileset.NOTHING) {
                       drawTileAtCoords(grid, Tileset.WALL, up);
                   }
                    if (getTileAtCoords(grid, down) == Tileset.NOTHING) {
                        drawTileAtCoords(grid, Tileset.WALL, down);
                    }
                    if (getTileAtCoords(grid, left) == Tileset.NOTHING) {
                        drawTileAtCoords(grid, Tileset.WALL, left);
                    }
                    if (getTileAtCoords(grid, right) == Tileset.NOTHING) {
                        drawTileAtCoords(grid, Tileset.WALL, right);
                    }
            }
            }
    }
    }
}
