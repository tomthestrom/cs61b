package byow.Core.Corridor;

import byow.Core.GridCoords;
import byow.Core.GridDrawer;
import byow.TileEngine.TETile;
import byow.Core.Direction;
import byow.TileEngine.Tileset;

public class RightToTop implements CorridorTile {
    private GridCoords coords;
    public RightToTop (GridCoords coords) {
        this.coords = coords;
    }

    @Override
    public GridCoords getCoords() {
        return coords;
    }

    /**
     * Draw Right To Top Corridor Tile:
     FLOOR WALL
     WALL  WALL
     * @param grid
     */
    @Override
    public void drawTile(TETile[][] grid) {
        GridCoords floor = getCoords();

        GridCoords wallRight = floor.getNextInDirection(Direction.RIGHT);
        GridCoords wallCorner = wallRight.getNextInDirection(Direction.DOWN);
        GridCoords wallUnder = floor.getNextInDirection(Direction.DOWN);

        GridCoords[] wallCoords = {wallRight, wallUnder, wallCorner};

        GridDrawer.drawTileAtCoords(grid, Tileset.FLOOR, floor);
        for (GridCoords wallCoord : wallCoords) {
            if (GridDrawer.getTileAtCoords(grid, wallCoord) != Tileset.FLOOR) {
                GridDrawer.drawTileAtCoords(grid, Tileset.WALL, wallCoord);
            }
        }
    }
}
