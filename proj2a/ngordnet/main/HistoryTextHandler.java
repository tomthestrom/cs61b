package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;

/**
 * Handles a request in format <List> words, startYear, endYear and returns a string representation with the word
 * in front and timeseries y=usage count per each year between startYear and endYear
 */
public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap ngm;
    public HistoryTextHandler(NGramMap ngm) {
       this.ngm = ngm;
    }


    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();


        StringBuilder response = new StringBuilder();

        for (String word : words) {
            response.append(word);
            response.append(": ");
            response.append(ngm.countHistory(word, startYear, endYear));
            response.append(System.getProperty("line.separator"));
        }

        return response.toString();
    }
}
