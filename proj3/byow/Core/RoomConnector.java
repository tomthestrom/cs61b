package byow.Core;

import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;

import java.util.List;

public class RoomConnector {
    private TETile[][] worldGrid;
    private List<Room> rooms;

    /**
     * Distance to connected rooms - heuristic for the pathfinding algorithm
     */
    private MinPQ<Room> distTo;

    public RoomConnector(TETile[][] worldGrid, List<Room> rooms) {
        this.worldGrid = worldGrid;
        this.rooms = rooms;
    }

    public TETile[][] connect() {
        Room source = rooms.get(0);

        for (int i = 1; i < rooms.size(); i++) {

        }

        return worldGrid;
    }
}
