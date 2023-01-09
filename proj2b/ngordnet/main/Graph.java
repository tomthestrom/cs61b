package ngordnet.main;

import java.util.HashSet;

public class Graph {

    private IdToIdsMap idToIdsMap;

    public Graph() {
        idToIdsMap = new IdToIdsMap();
    }

    public void addNode(int parentId, int childId) {
        idToIdsMap.addIdIdPair(parentId, childId);
    }

    public HashSet<Integer> getNode(int parentId) {
        return idToIdsMap.get(parentId);
    }
}
