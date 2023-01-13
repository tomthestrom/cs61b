package ngordnet.main;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.princeton.cs.algs4.In;

public class WordNet {
    //wrapper for a graph

    /**
     * Keeps track of the ids associated with the word
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
        setGraphFromFile(hyponymsFileName);
    }

    /**
     * Returns all the hyponyms of the hypernym, including the hypernym itself
     * @param hypernym
     * @return
     */
    public TreeSet<String> getHyponyms(String hypernym) {
        HashSet<Integer> parentIds = wordToIdsMap.get(hypernym);
        return null;
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

    /**
     * Sets id to id mappings from the provided hyponyms.txt
     * @param fileName
     */
    private void setGraphFromFile(String fileName) {
        In in = new In(fileName);
        ArrayList<String> line;
        int parentId;

        while (in.hasNextLine()) {
            line = Stream.of(in.readLine().split(",")).collect(Collectors.toCollection(ArrayList<String>::new));
            parentId =  Integer.parseInt(line.get(0));

            for (int i = 1; i < line.size(); i++) {
               int childId = Integer.parseInt(line.get(i));
               graph.addNode(parentId, childId);
            }
        }
    }
}
