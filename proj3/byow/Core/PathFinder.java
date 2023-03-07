package byow.Core;

import java.util.Iterator;

public class PathFinder implements Iterable<GridCoords>{
    GridCoords source;
    GridCoords target;

    private int pathLength;

    private Direction lastMove;

    public PathFinder(GridCoords source, GridCoords target) {
       this.source = source;
       this.target = target;
       this.pathLength = 0;
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
            double distance = GridMathUtils.euclideanDistance(target, coordPointer);

                for (Direction direction: Direction.values()) {
                    double dirDistance = GridMathUtils.euclideanDistance(coordPointer.getNextInDirection(direction), target);

                    if (dirDistance < distance) {
                        distance = dirDistance;
                        coordPointer = coordPointer.getNextInDirection(direction);
                        lastMove = direction;
                    }
                }

                pathLength++;
                return coordPointer;
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
