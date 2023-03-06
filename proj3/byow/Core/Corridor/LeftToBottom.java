package byow.Core.Corridor;

import byow.Core.GridCoords;
public class LeftToBottom implements CorridorTile {
    private GridCoords coords;
    public LeftToBottom(GridCoords coords) {
        this.coords = coords;
    }

    @Override
    public GridCoords getCoords() {
        return coords;
    }
}
