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

        connectedGrid = new WeightedQuickUnionUF(Engine.WIDTH * Engine.HEIGHT);
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
//            GridDrawer.drawTileAtCoords(worldGrid, Tileset.FLOOR, target.getCenter());

            Corridor corridor = new Corridor();

            PathFinder corridorPathFinder = new PathFinder(sourceDoor, targetDoor);
            corridorPathFinder.minimizeZigZag();

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
                   GridDrawer.drawTileAtCoords(worldGrid, Tileset.FLOOR, curCoord);
                }
//                WeightedQuickUnionUF roomCoordAsSet = roomCoordsAsSet(target);
//
//                for (Direction direction: Direction.values()) {
//                    if (connectedGrid.connected(GridMathUtils.coordsTo1D(curCoord.getNextInDirection(direction), Engine.WIDTH), GridMathUtils.coordsTo1D(target.getCenter(), Engine.WIDTH))) {
//                        targetFound = true;
//
//                        GridCoordsDirection grCD = new GridCoordsDirection(curCoord.getNextInDirection(direction), direction);
//                        directionsTaken.add(grCD);
//                        GridDrawer.drawTileAtCoords(worldGrid, Tileset.FLOOR, curCoord.getNextInDirection(direction));
////                        GridDrawer.drawTileAtCoords(worldGrid, Tileset.WALL, targetDoor);
//                    }
//                    }
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

//            addToConnectedSet(source);
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

    public boolean isCoordInConnectedSet(GridCoords coord) {
        return connectedGrid.connected(GridMathUtils.coordsTo1D(coord, Engine.WIDTH), connectedSentinel);
    }

    public WeightedQuickUnionUF roomCoordsAsSet(Room room) {
        WeightedQuickUnionUF connectedSet = new WeightedQuickUnionUF(Engine.HEIGHT * Engine.WIDTH);
        int sentinelIndex = GridMathUtils.coordsTo1D(room.getCenter(), Engine.WIDTH);
        for (int x = room.getxMin(); x <= room.getxMax(); x++) {
            for (int y = room.getyMin(); y <= room.getyMin(); y++) {
                //don't add room edges - we can't connect those with a corridor
                //bottom left edge
                if (x == room.getxMin() && y == room.getyMin()) {
                    continue;
                }
                //bottom right edge
                if (x == room.getxMax() && y == room.getyMin()) {
                    continue;
                }
                //top left edge
                if (x == room.getxMin() && y == room.getyMax()) {
                    continue;
                }
                //top right edge
                if (x == room.getxMax() && y == room.getyMax()) {
                    continue;
                }

               connectedSet.union(sentinelIndex, GridMathUtils.coordsTo1D(new GridCoords(x, y), Engine.WIDTH));
            }
        }

        return connectedSet;
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
