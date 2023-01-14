package ngordnet.proj2b_testing;
import ngordnet.main.WordNet;
import org.junit.Test;

import javax.swing.tree.TreeCellRenderer;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class WordNetTest {

    public static final String HYPONYMS_11 = TestGraph.HYPONYMS_11;
    public static final String HYPONYMS_16 = TestGraph.HYPONYMS_16;
    public static final String SYNSETS_11 = "data/wordnet/synsets11.txt";
    public static final String SYNSETS_16 = "data/wordnet/synsets16.txt";

    @Test
    public void testGetHyponyms() {
        WordNet wordNet11 = new WordNet(SYNSETS_11, HYPONYMS_11);
        WordNet wordNet16 = new WordNet(SYNSETS_16, HYPONYMS_16);

        assertEquals(Set.of("antihistamine", "actifed"), wordNet11.getHyponyms("antihistamine"));
        assertEquals(Set.of("descent", "jump", "parachuting"), wordNet11.getHyponyms("descent"));

        TreeSet<String> expected16 = new TreeSet<>();
        expected16.addAll(asList("alteration", "change", "demotion", "increase", "jump",
                "leap", "modification", "saltation", "transition", "variation"));

        assertEquals(expected16, wordNet16.getHyponyms("change"));
    }
}
