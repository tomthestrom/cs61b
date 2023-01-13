package ngordnet.proj2b_testing;

import edu.princeton.cs.algs4.In;
import ngordnet.main.WordToIdsMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class TestWordToIdsMap {
    @Test
    public void testAddWordIdPair() {
        WordToIdsMap wordIdMap = new WordToIdsMap();
        HashSet<Integer> expected = new HashSet<>();
        In in = new In("data/wordnet/synsets11.txt");
        ArrayList<String> line;
        int id;

        while (in.hasNextLine()) {
            line = Stream.of(in.readLine().split(",")).collect(Collectors.toCollection(ArrayList<String>::new));
            id =  Integer.parseInt(line.get(0));
            String[] words = (line.get(1)).trim().split("\\s+");

            for (String word: words) {
                wordIdMap.addWordIdPair(word, id);
            }
        }

        expected.add(4);

        //parachuting -> 4
        assertEquals(wordIdMap.get("parachuting"), expected);

        expected.add(6);

        //jump -> 4, 6
        assertEquals(wordIdMap.get("jump"), expected);
    }
}
