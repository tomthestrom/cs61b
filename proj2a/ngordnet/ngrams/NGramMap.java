package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    private HashMap<Integer, WordsCountMap> wordsInYears;
    private TimeSeries counts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        wordsInYears = new HashMap<>();
        counts = new TimeSeries();
        setWordsInYearsFromFile(wordsFilename);
        setCountsFromFile(countsFilename);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        TimeSeries counts = new TimeSeries();

        for (Map.Entry<Integer, WordsCountMap> entry : wordsInYears.entrySet()) {
            Integer year = entry.getKey();
            WordsCountMap wordsCountMap = entry.getValue();

            if (wordsCountMap.containsKey(word)) {
                double wordCount = wordsCountMap.get(word).doubleValue();
                counts.put(year, wordCount);
            }
        }
        return counts;
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     * changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries counts = new TimeSeries();

        int currYear = startYear;

        while (currYear <= endYear) {
            if (yearRecordExists(currYear) && yearContainsWord(currYear, word)) {
                double wordCount = wordCountInYear(currYear, word);
                counts.put(currYear, wordCount);
            }
            currYear++;
        }
        return counts;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries totalCount = new TimeSeries();

        for (Map.Entry<Integer, Double> entry : counts.entrySet()) {
            Integer year = entry.getKey();
            Double wordsPerYear = entry.getValue();

            totalCount.put(year, wordsPerYear);
        }
        return totalCount;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries relativeFreq = new TimeSeries();

        for (Map.Entry<Integer, Double> entry : counts.entrySet()) {
            Integer year = entry.getKey();

            if (yearRecordExists(year) && yearContainsWord(year, word)) {
                double wordFrequency = wordCountInYear(year, word) / totalWordsInYear(year);;
                relativeFreq.put(year, wordFrequency);
            }
        }

        return relativeFreq;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries relativeFreq = new TimeSeries();
        int currYear = startYear;

        while (currYear <= endYear) {
            if (yearRecordExists(currYear) && yearContainsWord(currYear, word)) {
                double wordFrequency = wordCountInYear(currYear, word) / totalWordsInYear(currYear);
                relativeFreq.put(currYear, wordFrequency);
            }

            currYear++;
        }

        return relativeFreq;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries summedFreq = new TimeSeries();

        for (Map.Entry<Integer, WordsCountMap> entry : wordsInYears.entrySet()) {
            Integer year = entry.getKey();
            WordsCountMap wordsCountMap = entry.getValue();
            double summedWordCount = 0;

            for (String word : words) {
                if (wordsCountMap.containsKey(word)) {
                    summedWordCount += wordCountInYear(year, word);
                }
            }

            if (summedWordCount > 0) {
                summedFreq.put(year, summedWordCount / totalWordsInYear(year));
            }
        }

        return summedFreq;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {

        TimeSeries summedFreq = new TimeSeries();
        int currYear = startYear;

        while (currYear <= endYear) {
            double summedWordCount = 0;

            if (yearRecordExists(currYear)) {
                for (String word : words) {
                    if (yearContainsWord(currYear, word)) {
                        summedWordCount += wordCountInYear(currYear, word);
                    }
                }
            }

            if (summedWordCount > 0) {
                summedFreq.put(currYear, summedWordCount / totalWordsInYear(currYear));
            }

            currYear++;
        }
        return summedFreq;
    }

    /**
     * Sets up the HashMap wordsInYears
     */
    private void setWordsInYearsFromFile(String fileName) {
        WordsCountMap wordsCountMap;
        In in = new In(fileName);
        String word;
        int year;
        int wordCount;

        while (in.hasNextLine()) {
            word = in.readString();
            year = in.readInt();
            wordCount = in.readInt();

            //if no entry for year - create a new entry with key as year and value as a blank TreeMap
            if (!wordsInYears.containsKey(year)) {
                wordsCountMap = new WordsCountMap();
                wordsInYears.put(year, wordsCountMap);
            }

            //get the TreeMap for the corresponding year
            wordsCountMap = wordsInYears.get(year);
            //save the word for the current year with its count
            wordsCountMap.put(word, wordCount);


            //read the rest of the line - all the data we need is in the 1st 3 cols
            in.readLine();
        }
    }

    /**
     * Sets up the TimeSeries counts
     */
    private void setCountsFromFile(String fileName) {
        In in = new In(fileName);
        ArrayList<String> line;
        int year;
        double wordCount;

        while (in.hasNextLine()) {
            //unfortunately In utility class doesn't contain a nice method to spec separator, gotta read file like this
            line = Stream.of(in.readLine().split(",")).collect(Collectors.toCollection(ArrayList<String>::new));
            year =  Integer.parseInt(line.get(0));
            wordCount = Double.parseDouble(line.get(1));

            counts.put(year, wordCount);
        }
    }

    private boolean yearContainsWord(int year, String word) {
        return wordsInYears.get(year).containsKey(word);
    }

    private boolean yearRecordExists(int year) {
        return wordsInYears.containsKey(year);
    }

    private double wordCountInYear(int year, String word) {
        return wordsInYears.get(year).get(word).doubleValue();
    }

    private double totalWordsInYear(int year) {
        if (yearRecordExists(year)) {
            return counts.get(year);
        }

        return 0;
    }
}
