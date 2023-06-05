package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.regex.*;
import org.w3c.dom.Node;

import java.util.List;

//@TODO: MOVE MAP GENERATION elsewhere, IMPLEMENT INTERACT WITH KEYBOARD, INTERACT WITH INPUT STRING
public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 60;

    public static final int WATER_HEIGHT = (int) (HEIGHT * 0.4);
    public static final int MOUNTAIN_START = (int) (HEIGHT * 0.85);

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, running both of these:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        int seed = 1;

        try {
            //TODO: temp solution for running Phase 1.
            String inputRegex = "N(\\d+)S";
            Pattern pattern = Pattern.compile(inputRegex);
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                String numberString = matcher.group(1);
                seed = Integer.parseInt(numberString);
            }

        } catch (Error e) {
           System.out.println(e);
        }

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];

        //initially fill the world with NOTHING tiles
        GridDrawer.fillRectangle(finalWorldFrame, Tileset.NOTHING, 0, WIDTH, 0, HEIGHT);

        //the seed based on which the pseudorandom map is generated
        BSPTree worldTree = new BSPTree(WIDTH, HEIGHT, seed);

        List<Room> roomList = worldTree.generateTree().getRoomsFromLeaves();

        GridDrawer.drawRooms(finalWorldFrame, roomList);


        RoomConnector roomConnector = new RoomConnector(finalWorldFrame, roomList);
        roomConnector.connect();
        GridDrawer.mapPostProcessor(finalWorldFrame);

        return finalWorldFrame;
    }

    public static void main(String[] args) {
        Engine eng = new Engine();
        eng.ter.initialize(WIDTH, HEIGHT);
        eng.ter.renderFrame(eng.interactWithInputString("N12345S"));
    }
}
