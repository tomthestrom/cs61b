package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.List;

public class RoomConnector {
    private TETile[][] worldGrid;
    private List<Room> rooms;

    /**
     * Distance to connected rooms - heuristic for the pathfinding algorithm
     */
    private MinPQ<GridSearchable> distTo;

    private WeightedQuickUnionUF connectedGrid;

    /**
     * The coordinate used to check isConnected via WQU connectedGrid
     */
    private int connectedSentinel;

    public RoomConnector(TETile[][] worldGrid, List<Room> rooms) {
        this.worldGrid = worldGrid;
        this.rooms = rooms;
        this.connectedGrid = new WeightedQuickUnionUF(worldGrid.length * worldGrid[0].length);
    }

    public TETile[][] connect() {
        //designate the 0th room as the source
        Room source = rooms.get(0);
        setConnectedSentinel(source.getCenter());

        distTo = new MinPQ<>(new GridSearchableDistanceComparator());

        for (int i = 1; i < rooms.size(); i++) {
            Room room = rooms.get(i);

            //rooms source is initially its center
            room.setSource(room.getCenter());
            //rooms target is initially the sources center
            room.setTarget(source.getCenter());

            distTo.insert(room);
        }

        Room closestRoom = (Room) distTo.delMin();


        boolean doorFound = false;

        GridCoords coordPointer = new GridCoords(closestRoom.getXCenter(), closestRoom.getYCenter());
        double distance = GridMathUtils.euclideanDistance(closestRoom.getTarget(), coordPointer);
        int i = 0;
        while (i < 5) {
            GridCoords[] directions = coordPointer.directions();

            for (GridCoords direction : directions) {
                double dirDistance = GridMathUtils.euclideanDistance(direction, closestRoom.getTarget());

                if (dirDistance < distance) {
                    distance = dirDistance;
                    coordPointer = direction;
                }
            }
           worldGrid[coordPointer.x()][coordPointer.y()] = Tileset.FLOWER;
         i += 1;
        }

        worldGrid[source.getXCenter()][source.getYCenter()] = Tileset.FLOWER;
//        worldGrid[closestRoom.getXCenter()][closestRoom.getYCenter()] = Tileset.FLOWER;
        return worldGrid;
    }

    /**
     * Adds all the points defined by the AbstractGridObject given as parameter to the connectedGrid WQU obj
     * @param gridObject
     */
    public void addToConnectedSet(AbstractGridObject gridObject) {
       for (int x = gridObject.getxMin(); x < gridObject.getxMax(); x++) {
           for (int y = gridObject.getyMin(); y < gridObject.getyMin(); y++) {
              GridCoords coords = new GridCoords(x, y);
              connectedGrid.union(connectedSentinel, GridMathUtils.coordsTo1D(coords, Engine.WIDTH));
           }
       }
    }

    public void setConnectedSentinel(GridCoords coords) {
        connectedSentinel = GridMathUtils.coordsTo1D(coords, Engine.WIDTH);
    }

}
