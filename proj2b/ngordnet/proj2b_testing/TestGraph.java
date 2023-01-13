package ngordnet.proj2b_testing;

import edu.princeton.cs.algs4.In;
import ngordnet.main.Graph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class TestGraph {

    public static final String HYPONYMS_11 = "data/wordnet/hyponyms11.txt";
    public static final String HYPONYMS_14 = "data/wordnet/hyponyms14.txt";

    private Graph getGraphFromFileName(String fileName) {
        Graph graph = new Graph();
        In in = new In(fileName);
        ArrayList<String> line;
        int parentId;

        while (in.hasNextLine()) {
            line = Stream.of(in.readLine().split(",")).collect(Collectors.toCollection(ArrayList<String>::new));
            parentId =  Integer.parseInt(line.get(0));

            for (int i = 1; i < line.size(); i++) {
                int childId = Integer.parseInt(line.get(i));
                graph.addNode(parentId, childId);
            }
        }

        return graph;
    }

    @Test
    public void testGetNode() {
        Graph graph = getGraphFromFileName(HYPONYMS_11);
        HashSet<Integer> expected = new HashSet<>();

        expected.add(1);
        assertEquals(graph.getNode(0), expected);

        expected = new HashSet<>();
        expected.add(6);
        expected.add(7);
        assertEquals(graph.getNode(5), expected);

        graph = getGraphFromFileName(HYPONYMS_14);
        expected = new HashSet<>(Arrays.asList(3, 4));
        assertEquals(expected, graph.getNode(2));
    }

    @Test
    public void testGetAllChildren() {
        Graph graph = getGraphFromFileName(HYPONYMS_14);
        HashSet<Integer> expected = new HashSet<>(Arrays.asList(3, 4, 5));

        assertEquals(expected, graph.getAllChildren(2));

        expected = new HashSet<>(Arrays.asList(2, 3, 4, 5, 11, 12, 13));

        assertEquals(expected, graph.getAllChildren(1));
        //empty
        assertEquals(new HashSet<>(), graph.getAllChildren(1000));
    }

}
