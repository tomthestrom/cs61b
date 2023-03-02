package byow.Core;

import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.MaxPQ;

import java.util.List;

public class RoomConnector {
    private TETile[][] worldGrid;
    private List<Room> rooms;

    /**
     * Distance to connected rooms - heuristic for the pathfinding algorithm
     */
    private MaxPQ<Room> distTo;

    public RoomConnector(TETile[][] worldGrid, List<Room> rooms) {
        this.worldGrid = worldGrid;
        this.rooms = rooms;
    }


}
