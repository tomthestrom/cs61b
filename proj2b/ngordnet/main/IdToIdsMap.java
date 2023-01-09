package ngordnet.main;

import java.util.HashMap;
import java.util.HashSet;

public class IdToIdsMap extends HashMap<Integer, HashSet> {
    public IdToIdsMap() {
        super();
    }

    public void addIdIdPair(int parentId, int childId) {
        HashSet<Integer> childIdSet;

        if (this.containsKey(parentId)) {
            childIdSet = this.get(parentId);
            childIdSet.add(childId);
            return;
        }

        childIdSet = new HashSet<>();
        childIdSet.add(childId);
        this.put(parentId, childIdSet);
    }
}
