package ru.nsu.litvinenko.lab1;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {
    private final Map<String, Integer> map = new HashMap<>();
    private int allWords;
    private Pair[] array;

    private void sorting() {
        boolean isSorted = false;
        int buf;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i].getValue() > array[i + 1].getValue()) {
                    isSorted = false;
                    Pair tmp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = tmp;
                }
            }
        }

//        for (int i = 0; i < array.length; i++) {
//            for (int j = array.length; j > 0; j++) {
//                if (array[i].getValue() > array[j].getValue()) {
//                    Pair tmp = array[j];
//                    array[j] = array[i];
//                    array[i] = tmp;
//                }
//            }
//        }
    }

    public void counter(String scannedStr) {
        Pattern pattern = Pattern.compile("[А-Яа-яЁёA-Za-z_0-9]+");
        Matcher matcher = pattern.matcher(scannedStr);
        while (matcher.find()) {
            String string = scannedStr.substring(matcher.start(), matcher.end());
            map.merge(string, Constants.ONE_WORD, Integer::sum);
            allWords++;
        }
        array = new Pair[map.size()];
        int count = 0;
        for (String key : map.keySet()) {
            array[count++] = new Pair(key, map.get(key));
        }

    }

    public Pair[] makeList() {
        sorting();
        return array;
    }

    public int getAllWords() {
        return allWords;
    }
}
