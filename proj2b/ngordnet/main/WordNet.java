package ngordnet.main;

import java.util.HashMap;
import java.util.HashSet;

public class WordNet {
    //wrapper for a graph

    /**
     * Keeps track of the id associated with the word
     */
    private WordToIdsMap wordToIdsMap;

    /**
     * Keeps track of the words associated with the id
     */
    private IdToWordsMap idToWordsMap;

    /**
     * Keeps track of id to id connection hypernym -> hyponym
     */
    private Graph graph;

    public WordNet(String synsetsFileName, String hyponymsFileName) {
        graph = new Graph();
        wordToIdsMap = new WordToIdsMap();
        idToWordsMap = new IdToWordsMap();

        //build the graph -> add all the edges
    }

    private void addWordToMap(String word, Integer id) {
        wordToIdsMap.addWordIdPair(word, id);
        idToWordsMap.addIdWordPair(id, word);
    }

    private HashSet<Integer> getWordIdSet(String word) {
        return wordToIdsMap.get(word);
    }

    private HashSet<String> getIdWordSet(int id) {
        return idToWordsMap.get(id);
    }
    //graph helper functions

}
