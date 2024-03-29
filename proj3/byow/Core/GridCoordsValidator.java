package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class GridCoordsValidator {
    private GridCoords coords;
    private TETile[][] grid;

    public GridCoordsValidator(GridCoords coords, TETile[][] grid) {
       this.coords = coords;
       this.grid = grid;
    }

    /**
     * Determines whether a door can be made/corridor can be connected at the given coordinates
     * Restriction:
     * Room/corridor edges can't be made into a door
     * @return
     */
    public boolean isValidDoor() {
       return !(isTopLeftEdge() &&
               isBottomLeftEdge() &&
               isTopRightEdge() &&
               isBottomRightEdge());
    };


    /**
     * Check whether the coordinate is in the top left corner of a structure surrounded by wall
     * @return
     */
    public boolean isTopLeftEdge() {
        return isWall(coords.getNextInDirection(Direction.RIGHT)) &&
                isWall(coords.getNextInDirection(Direction.DOWN));
    }

    /**
     * Check whether the coordinate is in the bottom left corner of a structure surrounded by wall
     * @return
     */
    public boolean isBottomLeftEdge() {
        return isWall(coords.getNextInDirection(Direction.RIGHT)) &&
                isWall(coords.getNextInDirection(Direction.UP));
    }

    /**
     *
     * Check whether the coordinate is in the top right corner of a structure surrounded by wall
     * @return
     */
    public boolean isTopRightEdge() {
        return isWall(coords.getNextInDirection(Direction.LEFT)) &&
                isWall(coords.getNextInDirection(Direction.DOWN));
    }

    /**
     * Check whether the coordinate is in the bottom right corner of a structure surrounded by wall
     * @return
     */
    public boolean isBottomRightEdge() {
        return isWall(coords.getNextInDirection(Direction.LEFT)) &&
                isWall(coords.getNextInDirection(Direction.UP));
    }

    /**
     * Is there a wall at the given coordinates?
     * @param coords
     * @return
     */
    public boolean isWall(GridCoords coords) {
       return grid[coords.x()][coords.y()] == Tileset.WALL;
    }

    /**
     * Is there a wall at coords?
     * @return
     */
    public boolean isWall() {
        return grid[coords.x()][coords.y()] == Tileset.WALL;
    }

    public GridCoords getClosestValidDoor(GridCoords target) {
        GridCoords closestDoor = coords;

        if (isTopLeftEdge()) {
            closestDoor = GridMathUtils.getClosestCoord(target,
                    coords.getNextInDirection(Direction.DOWN),
                    coords.getNextInDirection(Direction.RIGHT));
        }

        if (isBottomLeftEdge()) {
            closestDoor = GridMathUtils.getClosestCoord(target,
                    coords.getNextInDirection(Direction.UP),
                    coords.getNextInDirection(Direction.RIGHT));
        }

        if (isTopRightEdge()) {
          closestDoor = GridMathUtils.getClosestCoord(target,
                  coords.getNextInDirection(Direction.DOWN),
                  coords.getNextInDirection(Direction.LEFT));
       }

        if (isBottomRightEdge()) {
            closestDoor = GridMathUtils.getClosestCoord(target,
                    coords.getNextInDirection(Direction.UP),
                    coords.getNextInDirection(Direction.LEFT));
        }

      return  closestDoor;
    }
}
