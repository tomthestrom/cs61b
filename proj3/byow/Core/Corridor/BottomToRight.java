package byow.Core.Corridor;

import byow.Core.GridDrawer;
import byow.TileEngine.TETile;
import byow.Core.Direction;
import byow.TileEngine.Tileset;
import byow.Core.GridCoords;

public class BottomToRight implements CorridorTile {

    private GridCoords coords;
    public BottomToRight(GridCoords coords) {
        this.coords = coords;
    }

    @Override
    public GridCoords getCoords() {
        return coords;
    }

    /**
     * Draw Bottom To Left Corridor Tile:
     WALL FLOOR
     WALL  WALL
     * @param grid
     */
    @Override
    public void drawTile(TETile[][] grid) {
        GridCoords floor = getCoords();

        GridCoords wallLeft = floor.getNextInDirection(Direction.LEFT);
        GridCoords wallCorner = wallLeft.getNextInDirection(Direction.DOWN);
        GridCoords wallUnder = floor.getNextInDirection(Direction.DOWN);

        GridCoords[] wallCoords = {wallLeft, wallUnder, wallCorner};

        GridDrawer.drawTileAtCoords(grid, Tileset.FLOOR, floor);
        GridDrawer.drawTileAtMultipleCoords(grid, Tileset.WALL, wallCoords);
    }
}
