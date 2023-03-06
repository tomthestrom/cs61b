package byow.Core.Corridor;

import byow.Core.GridCoords;

public class LeftToTop implements CorridorTile {

    private GridCoords coords;
    public LeftToTop(GridCoords coords) {
        this.coords = coords;
    }

    @Override
    public GridCoords getCoords() {
        return coords;
    }
}
