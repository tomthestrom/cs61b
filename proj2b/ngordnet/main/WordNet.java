package ngordnet.main;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
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
     * Returns all the hyponyms of the hypernym, including the hypernym itself - in order thx to being a TreeSet
     * @param hypernym
     */
    public TreeSet<String> getHyponyms(String hypernym) {
        HashSet<Integer> parentIds = wordToIdsMap.get(hypernym);
        HashSet<Integer> childIds = new HashSet<>();
        TreeSet<String> hyponyms = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        for (int parentId : parentIds) {
            //find and get all child ids from the graph
            childIds.addAll(graph.getAllChildren(parentId));
            //add all words associated with the parent ids to hyponyms set
            hyponyms.addAll(idToWordsMap.get(parentId));
        }

        for (int childId : childIds) {
            //add all words associated with the found child ids to hyponyms set
            hyponyms.addAll(idToWordsMap.get(childId));
        }

        return hyponyms;
    }

    /**
     * Returns the hyponyms that are common between all requested hypernyms (sets intersection)
     * @param hypernyms
     */
    public TreeSet<String> getHyponymsIntersect(List<String> hypernyms) {
        TreeSet<String> hyponyms;
        TreeSet<String>[] hyponymsSets = new TreeSet[hypernyms.size()];

        //create hypernyms.size() amount of hyponyms sets
        for (int i = 0; i < hypernyms.size(); i++) {
           hyponymsSets[i] = getHyponyms(hypernyms.get(i));
        }

        if (hyponymsSets[0] == null) {
            hyponyms = new TreeSet<>();
        } else {
            //init hyponyms with the value of the 0th set
            hyponyms = hyponymsSets[0];
        }

        //iterate over the rest of hyponyms sets - removing items not found in the other ones
        for (int i = 1; i < hyponymsSets.length; i++) {
            if (hyponymsSets[i] != null) {
                hyponyms.retainAll(hyponymsSets[i]);
            }
        }

        return hyponyms;
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
