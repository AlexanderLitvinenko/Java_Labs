package com.company;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {
    private final Map<String, Integer> map = new HashMap<>();
    private int allWords;
    public void counter(String scannedStr) {
        Pattern pattern = Pattern.compile("[А-Яа-яЁёA-Za-z_0-9]+");
        Matcher matcher = pattern.matcher(scannedStr);
        while (matcher.find()) {
            String string = scannedStr.substring(matcher.start(), matcher.end());
            map.merge(string, Constants.ONE_WORD, Integer::sum);
            allWords++;
        }

    }
    public List<Map.Entry<String,Integer>> makeList() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return list;
    }
    public int getAllWords(){
        return allWords;
    }
}
