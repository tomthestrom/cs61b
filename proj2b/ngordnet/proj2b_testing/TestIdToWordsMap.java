package ngordnet.proj2b_testing;

import edu.princeton.cs.algs4.In;
import ngordnet.main.IdToWordsMap;
import ngordnet.main.WordToIdsMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class TestIdToWordsMap {
    @Test
    public void testAddIdToWordPair() {
        IdToWordsMap idWordMap = new IdToWordsMap();
        HashSet<String> expected = new HashSet<>();
        In in = new In("data/wordnet/synsets11.txt");
        ArrayList<String> line;
        int id;

        while (in.hasNextLine()) {
            line = Stream.of(in.readLine().split(",")).collect(Collectors.toCollection(ArrayList<String>::new));
            id =  Integer.parseInt(line.get(0));
            String[] words = (line.get(1)).trim().split("\\s+");

            for (String word: words) {
                idWordMap.addIdWordPair(id, word);
            }
        }

        expected.add("parachuting");
        expected.add("jump");

        //4 -> parachuting, jump
        assertEquals(idWordMap.get(4), expected);

        expected = new HashSet<>();

        expected.add("jump");
        expected.add("leap");

        //6 -> jump, leap
        assertEquals(idWordMap.get(6), expected);
    }
}
