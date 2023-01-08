package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;

public class HyponymHandler extends NgordnetQueryHandler {
    private WordNet wn;
    public HyponymHandler(WordNet wn) {
        this.wn = wn;
    }
    @Override

    public String handle(NgordnetQuery q) {
        return "hello";
    }
}
