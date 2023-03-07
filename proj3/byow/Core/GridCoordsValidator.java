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
        return grid[coords.right().x()][coords.right().y()] == Tileset.WALL &&
                grid[coords.bottom().x()][coords.bottom().y()] == Tileset.WALL;
    }

    /**
     * Check whether the coordinate is in the bottom left corner of a structure surrounded by wall
     * @return
     */
    public boolean isBottomLeftEdge() {
        return grid[coords.right().x()][coords.right().y()] == Tileset.WALL &&
                grid[coords.top().x()][coords.top().y()] == Tileset.WALL;
    }

    /**
     *
     * Check whether the coordinate is in the top right corner of a structure surrounded by wall
     * @return
     */
    public boolean isTopRightEdge() {
        return grid[coords.left().x()][coords.left().y()] == Tileset.WALL &&
                grid[coords.bottom().x()][coords.bottom().y()] == Tileset.WALL;
    }

    /**
     * Check whether the coordinate is in the bottom right corner of a structure surrounded by wall
     * @return
     */
    public boolean isBottomRightEdge() {
        return grid[coords.left().x()][coords.left().y()] == Tileset.WALL &&
                grid[coords.top().x()][coords.top().y()] == Tileset.WALL;
    }

}
