package byow.Core.Corridor;

import byow.Core.GridCoords;

public class TopToRight implements CorridorTile {

    private GridCoords coords;
    public TopToRight(GridCoords coords) {
        this.coords = coords;
    }

    @Override
    public GridCoords getCoords() {
        return coords;
    }
}
