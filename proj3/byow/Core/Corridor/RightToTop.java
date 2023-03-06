package byow.Core.Corridor;

import byow.Core.GridCoords;

public class RightToTop implements CorridorTile {
    private GridCoords coords;
    public RightToTop (GridCoords coords) {
        this.coords = coords;
    }

    @Override
    public GridCoords getCoords() {
        return coords;
    }
}
