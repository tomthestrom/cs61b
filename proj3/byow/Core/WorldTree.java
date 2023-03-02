package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.MaxPQ;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldTree {

    private Node root;

    private int width;

    private int height;

    private TETile[][] worldGrid;


    private static final int TIMES_TO_SPLIT = 60;

    /**
     * After splitting ENDS, we clean up a bit to have more space
     * using RandomUtils.bernoulli with a probability of CLEAR_RATIO
     * the space will be filled with NOTHING tiles
     */
    private static final double CLEAR_RATIO =  0.3;

    private static final int DEFAULT_ROOM_WALL_WIDTH = 1;

    /**
     * An instance of Node defines some 2D space on the worldGrid
     */
    private static class Node extends AbstractGridObject implements Comparable<Node> {

        public Node[] children;

        public boolean isVisible;

        static final int MIN_HEIGHT = 4;
        static final int MIN_WIDTH = 4;

        protected Node(int xMin, int xMax, int yMin, int yMax) {
            super(xMin, xMax, yMin, yMax);
        }


        /**
         * Splits the given node into 2 sub nodes - children
         * @param node
         * @param splitPoint
         * @param splitDir
         * @return
         */
        public static Node[] splitNode(Node node, int splitPoint, boolean splitDir) {
            node.children = new Node[2];

            //coordinates (0, 0) start in the bottom right corner
            int leftXMin;
            int rightXMin;
            int leftXMax;
            int rightXMax;
            int leftYMin;
            int rightYMin;
            int leftYMax;
            int rightYMax;

            //if splitting vertically, y-s stay the same
            if (splitDir) {
                //x coordinates increase from right to left (start at bottom right)
              rightXMin = node.getxMin();
              rightXMax = splitPoint;

              leftXMax = node.getxMax();
              leftXMin = splitPoint;

              leftYMin = rightYMin = node.getyMin();
              leftYMax = rightYMax = node.getyMax();

            } else {
                //left child is the bottom one, right child is the top one
                //splitting horizontally - x's stay the same
                leftXMin = rightXMin = node.getxMin();
                leftXMax = rightXMax = node.getxMax();

                leftYMin = node.getyMin();
                leftYMax = splitPoint;

                rightYMin = splitPoint;
                rightYMax = node.getyMax();
            }

           Node leftChild = new Node(leftXMin, leftXMax, leftYMin, leftYMax);
           Node rightChild = new Node(rightXMin, rightXMax, rightYMin, rightYMax);

           node.children[0] = leftChild;
           node.children[1] = rightChild;

           return node.children;
        }


        @Override
        public int compareTo(Node node) {
            return size() - node.size();
        }

        public boolean isLeaf() {
            return children == null;
        }

        public Node[] getChildren() {
            return children;
        }
    }

    public WorldTree(TETile[][] world) {
        width = world.length;
        height = world[0].length;
        worldGrid = world;

        this.root = new Node(0, width, 0, height);
    }

    public List<Room> generateRooms(int seed) {
        //fill the whole map with wall tiles
        GridDrawer.fillRectangle(worldGrid, Tileset.WALL, root.getxMin(), root.getxMax(), root.getyMin(), root.getyMax());

        boolean splitVertical = seed % 2 == 0;
        int splitPoint = getSplitPoint(root, seed, splitVertical);

        Node[] children = Node.splitNode(root, splitPoint, splitVertical);
        splitGridByNode(root, splitPoint, splitVertical);

        int timesSplit = 1;

        //max heap to keep track of the largest current node
        MaxPQ<Node> nodesToPartition = new MaxPQ<>();
        for (Node child : children) {
            nodesToPartition.insert(child);
        }

        Random splitNode = new Random(seed);

        //always partitioning the currently largest node
        while (timesSplit < TIMES_TO_SPLIT || nodesToPartition.isEmpty()) {
            //decrement seed with every cut, so we're getting a different rand nr. from Random.uniform
            seed--;

            //get max node from the maxheap
            Node maxNode = nodesToPartition.delMax();

            //randomly don't split maxNode, just remove it from the queue and continue with the next largest
            //ensures we have some larger clusters of wall tiles
            if (timesSplit > TIMES_TO_SPLIT / 2.5 && RandomUtils.bernoulli(splitNode, 0.3)) {
                continue;
            }

            //if the node is higher than wider - true
            splitVertical = maxNode.height() < maxNode.width();

            splitPoint = getSplitPoint(maxNode, seed, splitVertical);

            splitGridByNode(maxNode, splitPoint, splitVertical);

            children = Node.splitNode(maxNode, splitPoint, splitVertical);

            //insert all partitioned children into maxheap with every iteration
            for (Node child : children) {
                nodesToPartition.insert(child);
            }

            timesSplit++;
        }

        Random leafClear = new Random(seed);
        removeRandomLeaves(root, leafClear);
        tileNodes(root);

        return nodesToRooms(root);
    }

    public TETile[][] getWorldGrid() {
       return worldGrid;
    }


    /**
     * Split grid defined by the given node
     * @param node
     * @param splitPoint
     * @param splitVertical
     */
    private void splitGridByNode(Node node, int splitPoint, boolean splitVertical) {
        if (splitVertical) {
            GridDrawer.drawLineAtX(worldGrid, Tileset.NOTHING, splitPoint, node.getyMin(), node.getyMax());
        } else {
            GridDrawer.drawLineAtY(worldGrid, Tileset.NOTHING, splitPoint, node.getxMin(), node.getxMax());
        }
    }

    /**
     * Calculates the splitpoint
     * @param node
     * @param seed
     * @param vertical
     * @return
     */
    private int getSplitPoint(Node node, int seed, boolean vertical) {
        if (vertical) {
            return RandomUtils.uniform(new Random(seed), node.getxMin() + Node.MIN_WIDTH, node.getxMax() - Node.MIN_WIDTH);
        }

         return RandomUtils.uniform(new Random(seed),node.getyMin() + Node.MIN_HEIGHT, node.getyMax() - Node.MIN_HEIGHT);
    }

    /**
     * Pseudorandomly removes some rooms from the grid - with possibility p defined by CLEAR_RATIO
     * @param node
     * @param leafClear
     */
    private void removeRandomLeaves(Node node, Random leafClear) {
        for (Node child : node.getChildren()) {
            if (child.isLeaf() && RandomUtils.bernoulli(leafClear, CLEAR_RATIO)) {
                GridDrawer.fillRectangle(worldGrid, Tileset.NOTHING, child.getxMin(), child.getxMax(), child.getyMin(), child.getyMax());
                child.isVisible = false;
            } else if (child.isLeaf()) {
               child.isVisible = true;
            } else {
                removeRandomLeaves(child, leafClear);
            }
        }
    }

    /**
     * Fills nodes with FLOOR tiles, while preserving the tiles on the sides(walls) - makes nodes into room
     * @param node
     */
    private void tileNodes(Node node) {
        for (Node child : node.getChildren()) {
            if (!child.isLeaf()) {
                tileNodes(child);
            }

            if (child.isLeaf() && child.isVisible) {
                //in case the room's x/y min borders the screen beginning
                boolean isXMinScreenSide = child.getxMin() == 0;
                boolean isYMinScreenSide = child.getyMin() == 0;

                int xMin = isXMinScreenSide ?
                        child.getxMin() + DEFAULT_ROOM_WALL_WIDTH :
                        child.getxMin() + 2 * DEFAULT_ROOM_WALL_WIDTH;

                int yMin = isYMinScreenSide ?
                        child.getyMin() + DEFAULT_ROOM_WALL_WIDTH :
                        child.getyMin() + 2 * DEFAULT_ROOM_WALL_WIDTH;

                GridDrawer.fillRectangle(worldGrid,
                        Tileset.FLOOR,
                        xMin,
                        child.getxMax() - DEFAULT_ROOM_WALL_WIDTH,
                        yMin,
                        child.getyMax() - DEFAULT_ROOM_WALL_WIDTH);
            }
        }
    }

    private List<Room> nodesToRooms(Node node) {
        ArrayList<Room> rooms = new ArrayList<>();
        for (Node child : node.getChildren()) {
           if (!child.isLeaf()) {
               nodesToRooms(child);
           }

           if (child.isLeaf() && child.isVisible) {
                Room room = new Room(child.getxMin(), child.getxMax(), child.getyMin(), child.getyMax());
               rooms.add(room);
           }
        }

        return rooms;
    }
}
