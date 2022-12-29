package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles a request - NgordnetQuery in format <List> words, startYear, endYear
 *  returns an encoded plot for each word that has any history
 *  if no data for any word - return an empty plot
 */
public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap ngm;
    public HistoryHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    /**
     * Returns an encoded plot with x, y axis
     */
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        ArrayList<String> wordsToPlot = new ArrayList<>();

        ArrayList<TimeSeries> wordsHistory = new ArrayList<>();
        int startYear = q.startYear();
        int endYear = q.endYear();

        for (String word : words) {
            TimeSeries countHistory = ngm.countHistory(word, startYear, endYear);

            //make sure both lists are the same length - only add word if it has any history - can't display no data
            if (!countHistory.isEmpty()) {
                wordsToPlot.add(word);
                wordsHistory.add(countHistory);
            }
        }

        XYChart chart = Plotter.generateTimeSeriesChart(wordsToPlot, wordsHistory);

        return Plotter.encodeChartAsString(chart);
    }
}
