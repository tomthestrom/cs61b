package byow.Core;

import java.util.Iterator;

public class PathFinder implements Iterable<GridCoords>{
    GridCoords source;
    GridCoords target;

    private int pathLength;

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

                GridCoords[] directions = coordPointer.directions();

                for (GridCoords direction : directions) {
                    double dirDistance = GridMathUtils.euclideanDistance(direction, target);

                    if (dirDistance < distance) {
                        distance = dirDistance;
                        coordPointer = direction;
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
}
