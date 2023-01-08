package ngordnet.main;

import java.util.HashMap;

/**
 * LookUp of word associated with a set of ids (a word can have multiple ids, depending on its meaning in the WordNet)
 * @param <String>
 * @param <HashSet>
 */
public class WordToIdsMap<String, HashSet> extends HashMap<String, HashSet> {
    public WordToIdsMap() {
        super();
    }
}
