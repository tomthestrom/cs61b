package ngordnet.ngrams;

import java.util.*;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     *  inclusive of both end points. */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        int i = startYear;

        while (i <= endYear) {
            if (ts.containsKey(i)) {
                this.put(i, ts.get(i));
            }
            i++;
        }
    }

    /** Returns all years for this TimeSeries (in any order). */
    public List<Integer> years() {
        List<Integer> allYears = new ArrayList<Integer>();

        for(Map.Entry<Integer,Double> entry : this.entrySet()) {
            Integer key = entry.getKey();
            allYears.add(key);
        }

        return allYears;
    }

    /** Returns all data for this TimeSeries (in any order).
     *  Must be in the same order as years(). */
    public List<Double> data() {
        List<Double> allData = new ArrayList<Double>();

        for(Map.Entry<Integer,Double> entry : this.entrySet()) {
            Double value = entry.getValue();
            allData.add(value);
        }

        return allData;
    }

    /** Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     *  each year, sum the data from this TimeSeries with the data from TS. Should return a
     *  new TimeSeries (does not modify this TimeSeries). */
    public TimeSeries plus(TimeSeries ts) {
       TimeSeries summedSeries = new TimeSeries();
       summedSeries.putAll(this);

        for(Map.Entry<Integer,Double> entry : ts.entrySet()) {
            Integer key = entry.getKey();
            Double value = entry.getValue();

            //if key exists, sum the value in summedSeries(copy of this) with the value in ts;
            if (summedSeries.containsKey(key)) {
                value = summedSeries.get(key) + value;
            }

            summedSeries.put(key, value);
        }

        return summedSeries;
    }

     /** Returns the quotient of the value for each year this TimeSeries divided by the
      *  value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
      *  throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
      *  Should return a new TimeSeries (does not modify this TimeSeries). */
     public TimeSeries dividedBy(TimeSeries ts) {
         TimeSeries dividedSeries = new TimeSeries();
         dividedSeries.putAll(this);

         for(Map.Entry<Integer,Double> entry : dividedSeries.entrySet()) {
             Integer key = entry.getKey();

             if (!ts.containsKey(key)) {
                 throw new IllegalArgumentException();
             }
             Double quotient = dividedSeries.get(key) / ts.get(key);

             dividedSeries.put(key, quotient);
         }

         return dividedSeries;
    }
}
