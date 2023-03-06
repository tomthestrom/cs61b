package byow.Core.Corridor;

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
}
