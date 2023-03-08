package byow.Core.Corridor;

import byow.Core.GridCoords;
import byow.Core.GridDrawer;
import byow.TileEngine.TETile;
import byow.Core.Direction;
import byow.TileEngine.Tileset;

public class SimpleVertical implements CorridorTile {
    private GridCoords coords;
    public SimpleVertical(GridCoords coords) {
       this.coords = coords;
    }

    @Override
    public GridCoords getCoords() {
        return coords;
    }

    /**
     * Draw Simple Vertical Corridor Tile:
     WALL FLOOR WALL
     * @param grid
     */
    @Override
    public void drawTile(TETile[][] grid) {
        GridCoords floor = getCoords();

        GridCoords wallRight = floor.getNextInDirection(Direction.RIGHT);
        GridCoords wallLeft = floor.getNextInDirection(Direction.LEFT);

        GridCoords[] wallCoords = {wallRight, wallLeft};

        GridDrawer.drawTileAtCoords(grid, Tileset.FLOOR, floor);
        GridDrawer.drawTileAtMultipleCoords(grid, Tileset.WALL, wallCoords);
    }
}
