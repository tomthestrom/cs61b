package byow.Core.Corridor;

import byow.Core.GridCoords;
import byow.Core.GridDrawer;
import byow.TileEngine.TETile;
import byow.Core.Direction;
import byow.TileEngine.Tileset;

public class SimpleHorizontal implements CorridorTile {
    private GridCoords coords;
    public SimpleHorizontal(GridCoords coords) {
        this.coords = coords;
    }

    @Override
    public GridCoords getCoords() {
        return coords;
    }

    /**
     * Draw Simple Horizontal Corridor Tile:
     WALL
     FLOOR
     WALL
     * @param grid
     */
    @Override
    public void drawTile(TETile[][] grid) {
        GridCoords floor = getCoords();

        GridCoords wallAbove = floor.getNextInDirection(Direction.UP);
        GridCoords wallUnder = floor.getNextInDirection(Direction.DOWN);

        GridCoords[] wallCoords = {wallAbove, wallUnder};

        GridDrawer.drawTileAtCoords(grid, Tileset.FLOOR, floor);
        for (GridCoords wallCoord : wallCoords) {
            if (GridDrawer.getTileAtCoords(grid, wallCoord) != Tileset.FLOOR) {
                GridDrawer.drawTileAtCoords(grid, Tileset.WALL, wallCoord);
            }
        }
    }
}
