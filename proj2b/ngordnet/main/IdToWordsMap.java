package ngordnet.main;

import java.util.HashMap;
import java.util.HashSet;

public class IdToWordsMap extends HashMap<Integer, HashSet<String>> {
    public IdToWordsMap() {
        super();
    }

    public void addIdWordPair(Integer id, String word) {
        HashSet<String> wordSet;

        if (this.containsKey(id)) {
            wordSet = this.get(id);
            wordSet.add(word);
            return;
        }

        wordSet = new HashSet<>();
        wordSet.add(word);
        this.put(id, wordSet);
    }
}
