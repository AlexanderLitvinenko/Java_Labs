package ru.nsu.litvinenko.lab1;

public class Pair {
    private Integer value;
    private String word;

    public Integer getValue() {
        return this.value;
    }

    public String getWord() {
        return this.word;
    }

    public Pair(String word, Integer countOfWords) {
        this.value = countOfWords;
        this.word = word;
    }

}
