package byow.Core;

import byow.Core.Corridor.Corridor;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Connects the rooms using the PathFinder
 */
public class RoomConnector {
    private TETile[][] worldGrid;

    /**
     * Distance to connected rooms - heuristic for the pathfinding algorithm
     */
    private MinPQ<GridSearchable> distTo;


    private List<Room> disconnectedRooms;


    public RoomConnector(TETile[][] worldGrid, List<Room> rooms) {
        this.worldGrid = worldGrid;
        this.disconnectedRooms = rooms;
        distTo = new MinPQ<>(disconnectedRooms.size(), new GridSearchableDistanceComparator());
    }

    /**
     * The meat of the class,
     * connecting the rooms
     * @return
     */
    public TETile[][] connect() {
        List<Corridor> corridors = new ArrayList<>();

        Room firstSource = disconnectedRooms.remove(0);

        setInitialDistTo(firstSource);

        while (!distTo.isEmpty()) {
            //the room closest to the source
            Room target = (Room) distTo.delMin();
            Room source = target.getTargetRoom();

            //sources source is the center, target is the target's center
            source.setSource(source.getCenter());
            source.setTarget(target.getCenter());

            //find the door on the source room
            GridCoords sourceDoor = findDoor(source);
            source.setSource(sourceDoor);


            target.setSource(target.getCenter());
            target.setTarget(sourceDoor);
            GridCoords targetDoor = findDoor(target);
            target.setSource(targetDoor);
            source.setTarget(targetDoor);

            GridDrawer.drawTileAtCoords(worldGrid, Tileset.FLOOR, sourceDoor);

            Corridor corridor = new Corridor();

            PathFinder corridorPathFinder = new PathFinder(sourceDoor, targetDoor);
            //we wanna have corridors as straight as possible
            corridorPathFinder.minimizeZigZag();

            Iterator<GridCoords> iterator = corridorPathFinder.iterator();

            boolean targetFound = false;
            List<GridCoordsDirection> directionsTaken = new ArrayList<>();
            while (!targetFound) {
                GridCoords curCoord = iterator.next();
                //the moves (directions taken) to be used by the corridor tile picker
                Direction lastMove = corridorPathFinder.getLastMove();

                GridCoordsDirection gridCoordsDirection = new GridCoordsDirection(curCoord, lastMove);
                directionsTaken.add(gridCoordsDirection);

                //bang bang, we found it
                if (curCoord.equals(targetDoor)) {
                   targetFound = true;
                   GridDrawer.drawTileAtCoords(worldGrid, Tileset.FLOOR, targetDoor);
                }
            }



            corridor.build(directionsTaken);
            corridors.add(corridor);

            //recalculate the distance from source for all disconnected rooms in the minheap
            recalculateDistTO(target);
        }

        for (Corridor corridor : corridors) {
            GridDrawer.drawCorridor(worldGrid, corridor);
        }


        return worldGrid;
    }


    /**
     * Finds the door of the room based on it's source and target - picks the tile closest to the target while
     * obeying the instructions - can't pick a room edge
     * @param room
     * @return
     */
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

    /**
     * Set the inital distance of the disconnected rooms from the source
     * @param firstSource
     */
    private void setInitialDistTo(Room firstSource) {
        for (Room discRoom : disconnectedRooms) {
            discRoom.setSource(discRoom.getCenter());
            discRoom.setTarget(firstSource.getCenter());
            discRoom.setTargetRoom(firstSource);

            distTo.insert(discRoom);
        }
    }

    /**
     * Called after every new room is connected - this way we make sure we're always connecting the closest rooms
     * @param connectedRoom
     */
    private void recalculateDistTO(Room connectedRoom) {
        for (GridSearchable discRoom : distTo) {
            Room disconnectedRoom = (Room) discRoom;

            if (GridMathUtils.euclideanDistance(disconnectedRoom.getSource(), connectedRoom.getCenter()) < disconnectedRoom.distanceFromSource()) {
                disconnectedRoom.setTarget(connectedRoom.getCenter());
                disconnectedRoom.setTargetRoom(connectedRoom);
            }
        }
    }
}
