package ngordnet.main;

import edu.princeton.cs.algs4.MaxPQ;
import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.*;

public class HyponymHandler extends NgordnetQueryHandler {
    private WordNet wordNet;
    private NGramMap nGramMap;
    public HyponymHandler(WordNet wn, NGramMap nGramMap) {
        this.wordNet = wn;
        this.nGramMap = nGramMap;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        TreeSet<String> hyponyms = wordNet.getKPopularHyponyms(nGramMap, words, k, startYear, endYear);

        return buildResponseString(hyponyms);
    }

    private String buildResponseString(Collection<String> hyponyms) {
        Iterator<String> iterator = hyponyms.iterator();
        StringBuilder response = new StringBuilder();

        response.append("[");

        while (iterator.hasNext()) {
            response.append(iterator.next());

            if (iterator.hasNext()) {
                response.append(", ");
            }
        }

        response.append("]");

        return response.toString();
    }
}
