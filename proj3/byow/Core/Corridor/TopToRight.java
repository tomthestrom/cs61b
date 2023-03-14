package byow.Core.Corridor;

import byow.Core.GridCoords;
import byow.Core.GridDrawer;
import byow.TileEngine.TETile;
import byow.Core.Direction;
import byow.TileEngine.Tileset;

public class TopToRight implements CorridorTile {

    private GridCoords coords;
    public TopToRight(GridCoords coords) {
        this.coords = coords;
    }

    @Override
    public GridCoords getCoords() {
        return coords;
    }

    /**
     * Draw Top To Right Corridor Tile:
     WALL WALL
     WALL  FLOOR
     * @param grid
     */
    @Override
    public void drawTile(TETile[][] grid) {
        GridCoords floor = getCoords();

        GridCoords wallLeft = floor.getNextInDirection(Direction.LEFT);
        GridCoords wallCorner = wallLeft.getNextInDirection(Direction.UP);
        GridCoords wallAbove = floor.getNextInDirection(Direction.UP);

        GridCoords[] wallCoords = {wallLeft, wallAbove, wallCorner};

        GridDrawer.drawTileAtCoords(grid, Tileset.FLOOR, floor);
        for (GridCoords wallCoord : wallCoords) {
            if (GridDrawer.getTileAtCoords(grid, wallCoord) != Tileset.FLOOR) {
                GridDrawer.drawTileAtCoords(grid, Tileset.WALL, wallCoord);
            }
        }
    }
}
