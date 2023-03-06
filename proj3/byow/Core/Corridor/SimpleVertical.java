package byow.Core.Corridor;

import byow.Core.GridCoords;

public class SimpleVertical implements CorridorTile {
    private GridCoords coords;
    public SimpleVertical(GridCoords coords) {
       this.coords = coords;
    }

    @Override
    public GridCoords getCoords() {
        return coords;
    }
}
