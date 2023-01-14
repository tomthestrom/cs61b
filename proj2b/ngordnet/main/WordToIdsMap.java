package ngordnet.main;

import java.util.HashMap;
import java.util.HashSet;

/**
 * LookUp of word associated with a set of ids (a word can have multiple ids, depending on its meaning in the WordNet)
 */
public class WordToIdsMap extends HashMap<String, HashSet<Integer>> {
    public WordToIdsMap() {
        super();
    }

    public void addWordIdPair(String word, Integer id) {
        HashSet<Integer> idSet;

        if (this.containsKey(word)) {
            idSet = this.get(word);
            idSet.add(id);
            return;
        }

        idSet = new HashSet<>();
        idSet.add(id);
        this.put(word, idSet);
    }

    @Override
    public HashSet get(Object key) {
        HashSet<Integer> set = super.get(key);

        if (set == null) {
            set = new HashSet<>();
        }

        return set;
    }
}
