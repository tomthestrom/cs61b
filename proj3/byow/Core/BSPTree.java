package byow.Core;

import edu.princeton.cs.algs4.MaxPQ;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * BSPTree
 * Used to partition the space defined by the given WIDTH-HEIGHT
 * After partitioning the tree *Random* times we convert it's nodes into rooms of *Random* width*height
 */
public class BSPTree {

    private Node root;

    /**
     * Random obj initialized with the seed value given by the user in the constructor
     */
    private Random random;

    private static final int MIN_TIMES_TO_SPLIT = 15;
    private static final int MAX_TIMES_TO_SPLIT = 30;


    /**
     * An instance of Node defines some 2D space on the grid
     */
    private static class Node extends AbstractGridObject implements Comparable<Node> {

        public Node[] children;

        static final int MIN_HEIGHT = 8;
        static final int MIN_WIDTH = 8;

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
                leftXMin = node.getxMin();
                leftXMax = splitPoint;
                rightXMin = splitPoint;
                rightXMax = node.getxMax();

              leftYMin = rightYMin = node.getyMin();
              leftYMax = rightYMax = node.getyMax();

            } else {
                //left child is the bottom one, right child is the top one
                //splitting horizontally - x's stay the same
                leftXMin = rightXMin = node.getxMin();
                leftXMax = rightXMax = node.getxMax();
                leftYMin = node.getyMin();
                leftYMax = rightYMin = splitPoint;
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

    public BSPTree(int width, int height, int seed) {
        this.random = new Random(seed);
        this.root = new Node(0, width - 1, 0, height - 1);
    }

    public BSPTree generateTree() {
        boolean splitVertical = RandomUtils.bernoulli(random);
        int splitPoint = getSplitPoint(root, splitVertical);

        Node[] children = Node.splitNode(root, splitPoint, splitVertical);

        int timesSplit = 1;

        //max heap to keep track of the largest current node
        MaxPQ<Node> nodesToPartition = new MaxPQ<>();
        for (Node child : children) {
            nodesToPartition.insert(child);
        }

        //always partitioning the currently largest node
        while (timesSplit < RandomUtils.uniform(random, MIN_TIMES_TO_SPLIT, MAX_TIMES_TO_SPLIT) || nodesToPartition.isEmpty()) {
            //get max node from the maxheap
            Node nodeToPartition = nodesToPartition.delMax();

            //if the node is higher than wider - true
            splitVertical = nodeToPartition.height() < nodeToPartition.width();

            splitPoint = getSplitPoint(nodeToPartition, splitVertical);

            children = Node.splitNode(nodeToPartition, splitPoint, splitVertical);

            for (Node child : children) {
                nodesToPartition.insert(child);
            }

            timesSplit++;
        }

        return this;
    }

    /**
     * Calculates the splitpoint
     * @param node
     * @param seed
     * @param vertical
     * @return
     */
    private int getSplitPoint(Node node, boolean vertical) {
        if (vertical) {
            return RandomUtils.uniform(random, node.getxMin() + Node.MIN_WIDTH, node.getxMax() - Node.MIN_WIDTH);
        }

         return RandomUtils.uniform(random,node.getyMin() + Node.MIN_HEIGHT, node.getyMax() - Node.MIN_HEIGHT);
    }

    /**
     * Returns a list of Rooms
     * @return
     */
    public List<Room> getRoomsFromLeaves() {
       List<Room> roomList = new ArrayList<>();

        return getRoomsFromLeavesHelper(this.root, roomList);
    }

    /**
     *
     * @param node
     * @param rooms
     * @return
     */
    private List<Room> getRoomsFromLeavesHelper(Node node, List<Room> rooms) {
        for (Node child : node.getChildren()) {
           if (!child.isLeaf()) {
               getRoomsFromLeavesHelper(child, rooms);
           } else {
               rooms.add(makeRoomFromNode(child));
           }
        }

        return rooms;
    }

    /**
     * Makes a room from a given Node
     * @param node
     * @return
     */
    private Room makeRoomFromNode(Node node) {
        int xMin = RandomUtils.uniform(random,
                node.getxMin(),
                node.getxMax() - Room.MIN_WIDTH);

        int yMin = RandomUtils.uniform(random,
                node.getyMin(),
                node.getyMax() - Room.MIN_HEIGHT);


        int xMax = RandomUtils.uniform(random, xMin + Room.MIN_WIDTH, node.getxMax());
        int yMax = RandomUtils.uniform(random, yMin + Room.MIN_HEIGHT, node.getyMax());

        return new Room(xMin, xMax, yMin, yMax);
    }
}
