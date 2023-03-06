package byow.Core.Corridor;

import byow.Core.GridCoords;

public class BottomToLeft implements CorridorTile {
    private GridCoords coords;
    public BottomToLeft (GridCoords coords) {
        this.coords = coords;
    }

    @Override
    public GridCoords getCoords() {
        return coords;
    }
}
