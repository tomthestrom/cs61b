package byow.Core;

import java.util.Comparator;

public class GridSearchableDistanceComparator implements Comparator<GridSearchable> {
    @Override
    public int compare(GridSearchable obj1, GridSearchable obj2) {
       if (obj1.distanceFromSource() > obj2.distanceFromSource()) {
           return 1;
       } else if (obj1.distanceFromSource() < obj2.distanceFromSource()) {
           return -1;
       } else {
           return 0;
       }
    }
}
