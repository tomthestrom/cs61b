package ngordnet.proj2b_testing;

import edu.princeton.cs.algs4.In;
import ngordnet.main.Graph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class TestGraph {

    @Test
    public void testGetNode() {
        Graph graph = new Graph();
        HashSet<Integer> expected = new HashSet<>();
        In in = new In("data/wordnet/hyponyms11.txt");
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
        expected.add(1);
        assertEquals(graph.getNode(0), expected);

        expected = new HashSet<>();
        expected.add(6);
        expected.add(7);
        assertEquals(graph.getNode(5), expected);
    }

}
