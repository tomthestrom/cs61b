package byow.Core;

import byow.Core.Corridor.Corridor;
import byow.Core.Corridor.CorridorTilePicker;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
        GridCoords doorCords = findDoor(closestRoom);
        closestRoom.setSource(doorCords);
        GridDrawer.drawTileAtCoords(worldGrid, Tileset.FLOOR, doorCords);

        //corridor building
        boolean targetFound = false;
        Corridor newCorridor = new Corridor();


        GridCoords coordPointer = doorCords;

        PathFinder corridorPathFinder = new PathFinder(doorCords, closestRoom.getTarget());
        Iterator<GridCoords> corridorPath = corridorPathFinder.iterator();

        List<GridCoordsDirection> directionsTaken = new ArrayList<>();

        while (!targetFound) {
            coordPointer = corridorPath.next();

            //save coordinate and move made to arrive here
            GridCoordsDirection coordsToDirection = new GridCoordsDirection(coordPointer, corridorPathFinder.getLastMove());
            directionsTaken.add(coordsToDirection);

            GridCoordsValidator gridCoordsValidator = new GridCoordsValidator(coordPointer, worldGrid);

            if (gridCoordsValidator.isWall()) {
                targetFound = true;
            }

//            worldGrid[coordPointer.x()][coordPointer.y()] = Tileset.FLOOR;
        }


        //build the corridor using the tile picker
        for (int i = 0; i < directionsTaken.size() - 1; i++) {
            GridCoordsDirection currDirection = directionsTaken.get(i);
            GridCoordsDirection nextDirection = directionsTaken.get(i + 1);

            CorridorTilePicker corridorTilePicker = new CorridorTilePicker(currDirection.coords(), currDirection.direction(), nextDirection.direction());
            newCorridor.add(corridorTilePicker.getPickedTile());
            System.out.println(currDirection);
            System.out.println(nextDirection);
        }

        worldGrid[closestRoom.getXCenter()][closestRoom.getYCenter()] = Tileset.FLOWER;

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

    public GridCoords findDoor(Room room) {
        boolean doorFound = false;

        GridCoords coordPointer = room.getCenter();

        PathFinder pathFinder = new PathFinder(coordPointer, room.getTarget());
        Iterator<GridCoords> pathFinderIterator = pathFinder.iterator();


        while (!doorFound) {
            coordPointer = pathFinderIterator.next();
            GridCoordsValidator gridCoordsValidator = new GridCoordsValidator(coordPointer, worldGrid);

            if (gridCoordsValidator.isWall()) {
                coordPointer = gridCoordsValidator.getClosestValidDoor(room.getTarget());
                doorFound = true;
            }

        };

        return coordPointer;
    }

}
