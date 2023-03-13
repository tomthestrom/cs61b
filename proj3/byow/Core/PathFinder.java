package byow.Core;

import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Iterator;

public class PathFinder implements Iterable<GridCoords>{
    GridCoords source;
    GridCoords target;

    private TETile[][] grid;
    private int pathLength;

    private int sameMoveSequence = 0;

    public final static int MAX_SAME_MOVE_SEQUENCE = 2;

    private Direction lastMove;

    private boolean minimizeZigZag;

    public PathFinder(GridCoords source, GridCoords target) {
       this.source = source;
       this.target = target;
       this.pathLength = 0;
       this.minimizeZigZag = false;
       this.sameMoveSequence = 0;
    }

    public void setMinimizeZigZag(TETile[][] grid, boolean minimizeZigZag) {
        this.grid = grid;
        this.minimizeZigZag = minimizeZigZag;
    }

    private class PathFinderIterator implements Iterator<GridCoords> {
        GridCoords coordPointer;

        public PathFinderIterator() {
            coordPointer = source;
        }

        @Override
        public boolean hasNext() {
            return !coordPointer.equals(target);
        }

        @Override
        public GridCoords next() {
            double startDistance = GridMathUtils.euclideanDistance(target, coordPointer);

            coordPointer = getNextCoord(startDistance, minimizeZigZag);
            pathLength++;
            return coordPointer;
        }

        private GridCoords getNextCoord(double startDistance, boolean minimizeZigZag) {

            if (minimizeZigZag && getLastMove() != null) {
                GridCoords nextInSameDir = coordPointer.getNextInDirection(lastMove);

                double dirDistance = GridMathUtils.euclideanDistance(nextInSameDir, target);

                //try going in the same direction as before, if the distance decreases, we're going there
                if (dirDistance < startDistance && isValidMove(nextInSameDir) && sameMoveSequence < MAX_SAME_MOVE_SEQUENCE) {
                    sameMoveSequence++;
                    return coordPointer.getNextInDirection(lastMove);
                }
            }
                return getClosestNextCoord(startDistance);
        }

        private GridCoords getClosestNextCoord(double startDistance) {
            sameMoveSequence = 0;

            GridCoords nextCoordPointer = coordPointer;
            for (Direction direction : Direction.values()) {
                double dirDistance = GridMathUtils.euclideanDistance(coordPointer.getNextInDirection(direction), target);

                if (dirDistance < startDistance) {
                    startDistance = dirDistance;
                    nextCoordPointer = coordPointer.getNextInDirection(direction);
                    lastMove = direction;
                }
            }

            return nextCoordPointer;
        }

        private boolean isValidMove(GridCoords nextCoord) {
            if (lastMove == Direction.DOWN) {
               return nextCoord.y() < target.y();
            }

            if (lastMove == Direction.UP) {
                return nextCoord.y() > target.y();
            }

            if (lastMove == Direction.RIGHT) {
                return nextCoord.x() < target.x();
            }

            //direction.left
            return nextCoord.x() > target.x();
        }
    }


    @Override
    public Iterator<GridCoords> iterator() {
        return new PathFinderIterator();
    }

    public int getPathLength() {
        return pathLength;
    }

    public Direction getLastMove() {
        return lastMove;
    }
}
