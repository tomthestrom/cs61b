package byow.Core.Corridor;

import byow.Core.GridCoords;

public class SimpleHorizontal implements CorridorTile {
    private GridCoords coords;
    public SimpleHorizontal(GridCoords coords) {
        this.coords = coords;
    }

    @Override
    public GridCoords getCoords() {
        return coords;
    }
}
