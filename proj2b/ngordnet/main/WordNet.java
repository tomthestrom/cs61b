package ngordnet.main;

import java.util.HashMap;
import java.util.HashSet;

public class WordNet {
    //wrapper for a graph

    /**
     * Keeps track of the id associated with the word
     */
    private WordToIdsMap<String, HashSet<Integer>> wordToIdsMap;
    private Graph graph;

    public WordNet(String synsetsFileName, String hyponymsFileName) {
        graph = new Graph();
        wordToIdsMap = new WordToIdsMap<>();
        //build the graph -> add all the edges
    }

    private void addWordToMap(String word, Integer id) {
        HashSet<Integer> idSet;
        if (wordToIdsMap.containsKey(word)) {
            idSet = wordToIdsMap.get(word);
            idSet.add(id);
            return;
        }

        idSet = new HashSet<>();
        idSet.add(id);
        wordToIdsMap.put(word, idSet);
    }

    private HashSet<Integer> getWordIds(String word) {
        return wordToIdsMap.get(word);
    }

    //graph helper functions

}
