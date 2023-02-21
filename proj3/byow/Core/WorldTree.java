package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.MaxPQ;

import java.util.Random;

public class WorldTree {

    private Node root;

    private int width;

    private int height;

    private TETile[][] worldGrid;


    private static final int TIMES_TO_SPLIT = 30;

    private static class Node implements Comparable<Node> {

        private Node parent;

        public Node[] children;

        private TETile[][] worldGrid;

        private int xMin;

        private int xMax;

        private int yMin;

        private int yMax;

        /**
         * If horizontal: y - coordinate
         * If vertical: x - coordinate
         */
        private int splitPoint;

        /**
         * 0 horizontal
         * 1 vertical
         */
        private boolean splitDir;

        static final int MIN_HEIGHT = 4;
        static final int MIN_WIDTH = 4;


        public Node(TETile[][] worldGrid, int xMin, int xMax, int yMin, int yMax) {
            this.worldGrid = worldGrid;
            this.xMin = xMin;
            this.xMax = xMax;
            this.yMin = yMin;
            this.yMax = yMax;
        }

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

            node.setSplitPoint(splitPoint);
            node.setSplitDir(splitDir);

           Node leftChild = new Node(node.worldGrid, leftXMin, leftXMax, leftYMin, leftYMax);
           Node rightChild = new Node(node.worldGrid, rightXMin, rightXMax, rightYMin, rightYMax);

           node.children = new Node[2];

           node.children[0] = leftChild;
           node.children[1] = rightChild;



           return node.children;
        }

        private static void splitAtX(Node node, int splitPoint) {
            for (int y = node.getyMin(); y < node.getyMax(); y += 1) {
                node.worldGrid[splitPoint][y] = Tileset.NOTHING;
            }
        }

        private static void splitAtY(Node node, int splitPoint) {
            for (int x = node.getxMin(); x < node.getxMax(); x += 1) {
                node.worldGrid[x][splitPoint] = Tileset.NOTHING;
            }
        }

        public int getxMin() {
            return xMin;
        }

        public int getxMax() {
            return xMax;
        }

        public int getyMin() {
            return yMin;
        }

        public int getyMax() {
            return yMax;
        }

        public void setSplitPoint(int splitPoint) {
            this.splitPoint = splitPoint;
        }

        public void setSplitDir(boolean splitDir) {
            this.splitDir = splitDir;
        }

        public int width() {
            return xMax - xMin;
        }

        public int height() {
            return yMax - yMin;
        }

        public int size() {
            return width() * height();
        }

        @Override
        public int compareTo(Node node) {
            return size() - node.size();
        }
    }

    public WorldTree(TETile[][] world) {
        width = world.length;
        height = world[0].length;
        worldGrid = world;

        this.root = new Node(world, 0, width, 0, height);

    }

    public TETile[][] generateMap(int seed) {
        //fill the whole map with wall tiles
        fillGrid(Tileset.WALL, root.getxMin(), root.getxMax(), root.getyMin(), root.getyMax());

        boolean splitVertical = seed % 2 == 0;
        int splitPoint = getSplitPoint(root, seed, splitVertical);

        Node[] children = Node.splitNode(root, splitPoint, true);
        splitGridByNode(root, splitPoint, splitVertical);

        int timesSplit = 1;

        //max heap to keep track of the largest current node
        MaxPQ<Node> nodesToPartition = new MaxPQ<>();
        for (Node child : children) {
            nodesToPartition.insert(child);
        }

        //always partitioning the currently largest node
        while (timesSplit < TIMES_TO_SPLIT || nodesToPartition.isEmpty()) {
            //decrement seed with every cut, so we're getting a different rand nr. from Random.uniform
            seed--;

            //get max node from the maxheap
            Node maxNode = nodesToPartition.delMax();

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

        return worldGrid;
    }


    /**
     * Fills the worldGrid between xMin, xMax, yMin, yMax
     * @param type
     * @param xMin
     * @param xMax
     * @param yMin
     * @param yMax
     */
    private void fillGrid(TETile type, int xMin, int xMax, int yMin, int yMax) {
        for (int x = xMin; x < xMax; x += 1) {
            for (int y = yMin; y < yMax; y += 1) {
                worldGrid [x][y] = type;
            }
        }
    }

    /**
     * Fills the worldGrid with NOTHING tiles along X axis
     * @param splitPoint
     * @param yMin
     * @param yMax
     */
    private void splitGridAtX(int splitPoint, int yMin, int yMax) {
        for (int y = yMin; y < yMax; y += 1) {
            worldGrid[splitPoint][y] = Tileset.NOTHING;
        }
    }

    /**
     * Fills the worldGrid with NOTHING tiles along Y axis
     * @param splitPoint
     * @param xMin
     * @param xMax
     */
    private void splitGridAtY(int splitPoint, int xMin, int xMax) {
        for (int x = xMin; x < xMax; x += 1) {
            worldGrid[x][splitPoint] = Tileset.NOTHING;
        }
    }

    private void splitGridByNode(Node node, int splitPoint, boolean splitVertical) {
        if (splitVertical) {
            splitGridAtX(splitPoint, node.getyMin(), node.getyMax());
        } else {
            splitGridAtY(splitPoint, node.getxMin(), node.getxMax());
        }
    }



    private int getSplitPoint(Node node, int seed, boolean vertical) {
        if (vertical) {
            return RandomUtils.uniform(new Random(seed), node.getxMin() + Node.MIN_WIDTH, node.getxMax() - Node.MIN_WIDTH);
        }

         return RandomUtils.uniform(new Random(seed),node.getyMin() + Node.MIN_HEIGHT, node.getyMax() - Node.MIN_HEIGHT);
    }
}
