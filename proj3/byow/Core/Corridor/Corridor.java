package byow.Core.Corridor;

import byow.Core.GridCoordsDirection;

import java.util.ArrayList;
import java.util.List;

public class Corridor extends ArrayList<CorridorTile> {
    public void build(List<GridCoordsDirection> directionsTaken) {
        for (int i = 0; i < directionsTaken.size() - 1; i++) {
            GridCoordsDirection currDirection = directionsTaken.get(i);
            GridCoordsDirection nextDirection = directionsTaken.get(i + 1);

            CorridorTilePicker corridorTilePicker = new CorridorTilePicker(currDirection.coords(), currDirection.direction(), nextDirection.direction());
            add(corridorTilePicker.getPickedTile());
        }
    }
}
