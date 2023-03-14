package byow.Core.Corridor;

import byow.Core.GridCoords;
import byow.Core.GridDrawer;
import byow.TileEngine.TETile;
import byow.Core.Direction;
import byow.TileEngine.Tileset;

public class RightToBottom implements CorridorTile {

    private GridCoords coords;
    public RightToBottom(GridCoords coords) {
        this.coords = coords;
    }

    @Override
    public GridCoords getCoords() {
        return coords;
    }

    /**
     * Draw Right to Bottom Corridor Tile:
     WALL WALL
     FLOOR  WALL
     * @param grid
     */
    @Override
    public void drawTile(TETile[][] grid) {
        GridCoords floor = getCoords();

        GridCoords wallRight = floor.getNextInDirection(Direction.RIGHT);
        GridCoords wallCorner = wallRight.getNextInDirection(Direction.UP);
        GridCoords wallAbove = floor.getNextInDirection(Direction.UP);

        GridCoords[] wallCoords = {wallRight, wallAbove, wallCorner};

        GridDrawer.drawTileAtCoords(grid, Tileset.FLOOR, floor);
        for (GridCoords wallCoord : wallCoords) {
            if (GridDrawer.getTileAtCoords(grid, wallCoord) != Tileset.FLOOR) {
                GridDrawer.drawTileAtCoords(grid, Tileset.WALL, wallCoord);
            }
        }
    }
}
