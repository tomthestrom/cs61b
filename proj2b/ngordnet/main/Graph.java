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

    /**
     * Returns all children of the parent, not including the parent
     * @param parentId
     * @return
     */
    public HashSet<Integer> getAllChildren(int parentId) {
       HashSet<Integer> children = new HashSet<>();

       for (int childId: getNode(parentId)) {
           children.addAll(getAllChildrenHelper(childId, children));
       }
       return children;
    }

    /**
     * Recursively collects all children
     * @param parentId
     * @param children
     */
    private HashSet<Integer> getAllChildrenHelper(int parentId, HashSet<Integer> children) {
        children.add(parentId);

        if (getNode(parentId) != null) {
            for (int childId: getNode(parentId)) {
                getAllChildrenHelper(childId, children);
            }
        }

        return children;
    }
}
