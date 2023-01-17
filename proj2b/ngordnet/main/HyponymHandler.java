package ngordnet.main;

import edu.princeton.cs.algs4.MaxPQ;
import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

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
        StringBuilder response = new StringBuilder();
        System.out.println(words);
        TreeSet<String> hyponyms = wordNet.getHyponymsIntersect(words);

        for (String hyponym : hyponyms) {
            response.append(hyponym);
            response.append(" ");
        }

        return response.toString();
    }
}
