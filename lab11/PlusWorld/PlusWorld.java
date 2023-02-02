package PlusWorld;
import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final int MAX_PLUS_SIZE = 6;

    /**
     * Creates a plus size of squares (of flowers) of the given size at the given coordinates
     */
    private static void addPlus(TETile[][] tiles, int size, int startX, int startY) {
        size = Math.min(size, MAX_PLUS_SIZE);

        int centralX = startX;
        int leftX = centralX - size;
        int rightX = centralX + size;

        int topY = startY;
        int middleY = topY - size;
        int bottomY = middleY - size;

        //top square
        addSquare(tiles, size, centralX, topY);
        //middle square
        addSquare(tiles, size, centralX, middleY);
        //middle left square
        addSquare(tiles, size, leftX, middleY);
        //middle right square
        addSquare(tiles, size, rightX, middleY);
        //bottom square
        addSquare(tiles, size, centralX, bottomY);
    }

    /**
     * Creates a square of Flower tiles at the given coordinates
     */
    private static void addSquare(TETile[][] tiles, int size, int startX, int startY) {

        for (int y = startY; y <= startY + size; y++) {
            for (int x = startX; x <= startX + size; x++) {
               tiles[x][y] = Tileset.FLOWER;
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        int plusSquareSize = 3;

        int startX = world[0].length / 2 - (plusSquareSize / 2);
        int startY = world.length / 2 + plusSquareSize;

        addPlus(world, plusSquareSize, startX, startY);
        ter.renderFrame(world);
    }
}
