package ngordnet.main;

import java.util.HashSet;

public class Graph {

    private IdToIdsMap<Integer, HashSet<Integer>> idToIdsMap;

    public Graph() {
        idToIdsMap = new IdToIdsMap<>();
    }

    public void addNode(int parentId, int childId) {
        HashSet<Integer> childIdSet;

        if (idToIdsMap.containsKey(parentId)) {
            childIdSet = idToIdsMap.get(parentId);
            childIdSet.add(childId);
            return;
        }

        childIdSet = new HashSet<>();
        childIdSet.add(childId);
        idToIdsMap.put(parentId, childIdSet);
    }

    public HashSet<Integer> getNode(int parentId) {
        return idToIdsMap.get(parentId);
    }
}
