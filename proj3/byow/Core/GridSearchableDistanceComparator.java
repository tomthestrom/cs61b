package byow.Core;

import java.util.Comparator;

public class GridSearchableDistanceComparator implements Comparator<GridSearchable> {
    @Override
    public int compare(GridSearchable obj1, GridSearchable obj2) {
       double dist = obj1.distanceFromSource() - obj2.distanceFromSource();

       if (dist > 0) {
           return 1;
       } else if (dist < 0) {
           return -1;
       } else {
           return 0;
       }
    }
}
