package byow.Core.Corridor;

import byow.Core.GridCoords;

public class RightToBottom implements CorridorTile {

    private GridCoords coords;
    public RightToBottom(GridCoords coords) {
        this.coords = coords;
    }

    @Override
    public GridCoords getCoords() {
        return coords;
    }
}
