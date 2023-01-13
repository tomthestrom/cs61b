package ngordnet.main;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.princeton.cs.algs4.In;

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

        setMapsFromFile(synsetsFileName);
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

    /**
     * Sets both word to ids and ids to word mappings from the provided synsets.txt file
     * @param fileName
     */
    private void setMapsFromFile(String fileName) {
        In in = new In(fileName);
        ArrayList<String> line;
        int id;

        while (in.hasNextLine()) {
            line = Stream.of(in.readLine().split(",")).collect(Collectors.toCollection(ArrayList<String>::new));
            id =  Integer.parseInt(line.get(0));
            String[] words = (line.get(1)).trim().split("\\s+");

            for (String word: words) {
                addWordToMap(word, id);
            }
        }
    }
}
