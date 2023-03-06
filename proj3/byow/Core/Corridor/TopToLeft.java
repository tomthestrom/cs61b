package byow.Core.Corridor;

import byow.Core.GridCoords;

public class TopToLeft implements CorridorTile {
    private GridCoords coords;
    public TopToLeft(GridCoords coords) {
        this.coords = coords;
    }

    @Override
    public GridCoords getCoords() {
        return coords;
    }
}
