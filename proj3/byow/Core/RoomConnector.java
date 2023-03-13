package byow.Core;

import byow.Core.Corridor.Corridor;
import byow.Core.Corridor.CorridorTile;
import byow.Core.Corridor.CorridorTilePicker;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class RoomConnector {
    private TETile[][] worldGrid;

    /**
     * Distance to connected rooms - heuristic for the pathfinding algorithm
     */
    private MinPQ<GridSearchable> distTo;

    private WeightedQuickUnionUF connectedGrid;

    private List<Room> disconnectedRooms;

    /**
     * The coordinate used to check isConnected via WQU connectedGrid
     */
    private int connectedSentinel;

    public RoomConnector(TETile[][] worldGrid, List<Room> rooms) {
        this.worldGrid = worldGrid;
        this.disconnectedRooms = rooms;
        distTo = new MinPQ<>(disconnectedRooms.size(), new GridSearchableDistanceComparator());
    }

    public TETile[][] connect() {
        List<Corridor> corridors = new ArrayList<>();

        Room firstSource = disconnectedRooms.remove(0);


        for (Room discRoom : disconnectedRooms) {
           discRoom.setSource(discRoom.getCenter());
           discRoom.setTarget(firstSource.getCenter());
           discRoom.setTargetRoom(firstSource);

           distTo.insert(discRoom);
        }

        int i = 0;
        while (!distTo.isEmpty()) {
            Room target = (Room) distTo.delMin();
            Room source = target.getTargetRoom();

            source.setSource(source.getCenter());
            source.setTarget(target.getCenter());

            GridCoords sourceDoor = findDoor(source);
            source.setTarget(sourceDoor);


            target.setSource(target.getCenter());
            target.setTarget(sourceDoor);
            GridCoords targetDoor = findDoor(target);
            target.setSource(targetDoor);

            GridDrawer.drawTileAtCoords(worldGrid, Tileset.FLOOR, sourceDoor);
            GridDrawer.drawTileAtCoords(worldGrid, Tileset.FLOOR, targetDoor);

            Corridor corridor = new Corridor();

            PathFinder corridorPathFinder = new PathFinder(sourceDoor, targetDoor);
            corridorPathFinder.setMinimizeZigZag(worldGrid, true);

            Iterator<GridCoords> iterator = corridorPathFinder.iterator();

            boolean targetFound = false;
            List<GridCoordsDirection> directionsTaken = new ArrayList<>();
            while (!targetFound) {
                GridCoords curCoord = iterator.next();
                Direction lastMove = corridorPathFinder.getLastMove();

                GridCoordsDirection gridCoordsDirection = new GridCoordsDirection(curCoord, lastMove);
                directionsTaken.add(gridCoordsDirection);

                if (curCoord.equals(targetDoor)) {
                   targetFound = true;
                }
            }

            corridor.build(directionsTaken);
            corridors.add(corridor);
            i++;

            for (GridSearchable discRoom : distTo) {
                Room disconnectedRoom = (Room) discRoom;

                if (GridMathUtils.euclideanDistance(disconnectedRoom.getSource(), target.getCenter()) < disconnectedRoom.distanceFromSource()) {
                    disconnectedRoom.setTarget(target.getCenter());
                    disconnectedRoom.setTargetRoom(target);
                }
            }
        }

        for (Corridor corridor : corridors) {
            GridDrawer.drawCorridor(worldGrid, corridor);
        }


        return worldGrid;
    }

    /**
     * Starting from the center of the room
     * Finds the wall tile, that is the closest to the target
     * @param room
     * @return
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
