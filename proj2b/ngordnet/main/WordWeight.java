package ngordnet.main;

public class WordWeight implements Comparable<WordWeight>{
    private String word;
    private Double weight;

    public WordWeight(String word, Double weight) {
        this.word = word;
        this.weight = weight;
    }

    public String getWord() {
        return word;
    }

    public Double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(WordWeight wordWeight) {
        return getWeight().compareTo(wordWeight.getWeight());
    }
}
