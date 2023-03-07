package byow.Core.Corridor;

import byow.Core.GridCoords;
import byow.Core.Direction;

public class CorridorTilePicker {
    private CorridorTile tile;

    private GridCoords coords;

    private Direction curDirection;
    private Direction nextDirection;


    public CorridorTilePicker(GridCoords coords, Direction curDirection, Direction nextDirection) {
       this.coords = coords;
       this.curDirection = curDirection;
       this.nextDirection = nextDirection;

       this.tile = pickTile();
    }

    private CorridorTile pickTile() {
       if (directionCombiner(Direction.UP, Direction.UP) || directionCombiner(Direction.DOWN, Direction.DOWN)) {
            tile = new SimpleVertical(coords);
       } else if (directionCombiner(Direction.RIGHT, Direction.RIGHT) || directionCombiner(Direction.LEFT, Direction.LEFT)) {
           tile = new SimpleHorizontal(coords);
       } else if (directionCombiner(Direction.DOWN, Direction.LEFT)) {
           tile = new BottomToLeft(coords);
       } else if (directionCombiner(Direction.DOWN, Direction.RIGHT)) {
           tile = new BottomToRight(coords);
       } else if (directionCombiner(Direction.UP, Direction.LEFT)) {
           tile = new TopToLeft(coords);
       } else if (directionCombiner(Direction.UP, Direction.RIGHT)) {
           tile = new TopToRight(coords);
       } else if (directionCombiner(Direction.LEFT, Direction.DOWN)) {
           tile = new LeftToBottom(coords);
       } else if (directionCombiner(Direction.LEFT, Direction.UP)) {
           tile = new LeftToTop(coords);
       } else if (directionCombiner(Direction.RIGHT, Direction.DOWN)) {
           tile = new RightToBottom(coords);
       } else if (directionCombiner(Direction.RIGHT, Direction.UP)) {
           tile = new RightToTop(coords);
       }

        return tile;
    }

    /**
     * Used to combine 2 directions to check against
     * @param firstDirection
     * @param secondDirection
     * @return
     */
    private boolean directionCombiner(Direction firstDirection, Direction secondDirection) {
        return curDirection == firstDirection && nextDirection == secondDirection;
    }

    public CorridorTile getPickedTile() {
        return this.tile;
    }
}
