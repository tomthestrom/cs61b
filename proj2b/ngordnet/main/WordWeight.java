package ngordnet.main;

public record WordWeight (String word, Double weight) implements Comparable<WordWeight>{
    @Override
    public int compareTo(WordWeight wordWeight) {
        return weight().compareTo(wordWeight.weight());
    }
}
