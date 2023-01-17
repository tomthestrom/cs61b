package ngordnet.proj2b_testing;
import ngordnet.main.WordNet;
import ngordnet.ngrams.NGramMap;
import org.junit.Test;

import java.util.Set;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class WordNetTest {

    public static final String HYPONYMS_11 = TestGraph.HYPONYMS_11;
    public static final String HYPONYMS_16 = TestGraph.HYPONYMS_16;
    public static final String HYPONYMS = TestGraph.HYPONYMS;
    public static final String SYNSETS_11 = "data/wordnet/synsets11.txt";
    public static final String SYNSETS_16 = "data/wordnet/synsets16.txt";
    public static final String SYNSETS = "data/wordnet/synsets.txt";

    public static final String WORD_FILE = "./data/ngrams/top_49887_words.csv";
    public static final String COUNT_FILE = "./data/ngrams/total_counts.csv";

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
        assertEquals(new TreeSet<>(), wordNet16.getHyponyms(""));
        assertEquals(new TreeSet<>(), wordNet16.getHyponyms("blablablabal"));
    }

    @Test
    public void testGetHyponymsIntersect() {
        WordNet wordNet16 = new WordNet(SYNSETS_16, HYPONYMS_16);
        WordNet wordNet = new WordNet(SYNSETS, HYPONYMS);

        TreeSet<String> expected16 = new TreeSet<>();
        TreeSet<String> expected = new TreeSet<>();
        expected16.addAll(asList("jump","leap", "saltation", "transition"));
        expected.addAll(asList("amphitheater", "amphitheatre"));


        assertEquals(expected16, wordNet16.getHyponymsIntersect(asList("change", "transition")));

        assertEquals(expected, wordNet.getHyponymsIntersect(asList("bowl", "gallery")));

        //no intersect - key not existing
        assertEquals(new TreeSet<String>(), wordNet.getHyponymsIntersect(asList("action", "blabla")));
        assertEquals(new TreeSet<String>(), wordNet.getHyponymsIntersect(asList("blabla", "blabla")));
        assertEquals(new TreeSet<String>(), wordNet.getHyponymsIntersect(asList("blabla", "action")));
        assertEquals(new TreeSet<String>(), wordNet.getHyponymsIntersect(asList("blabla", "")));
    }

    @Test
    public void testGetKPopularHyponyms() {
        WordNet wordNet = new WordNet(SYNSETS, HYPONYMS);
        NGramMap nGramMap = new NGramMap(WORD_FILE, COUNT_FILE);

        TreeSet<String> expected = new TreeSet<>();
        expected.addAll(asList("biscuit", "cake", "kiss", "snap", "wafer"));
        assertEquals(expected, wordNet.getKPopularHyponyms(nGramMap,asList("food", "cake"), 5, 1950, 1990));

        expected = new TreeSet<>();
        expected.addAll(asList("amphitheater", "amphitheatre"));
        // k = 0, result same as getHyponymsIntersect
        assertEquals(expected, wordNet.getKPopularHyponyms(nGramMap, asList("bowl", "gallery"), 0, 2000, 2001));
    }
}
