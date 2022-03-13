package com.company;

import java.io.*;
import java.util.List;
import java.util.Map;

public class MyPrinter {
    private File file;
    private BufferedWriter csv;

    public void makeCSV(String nameFile, List<Map.Entry<String, Integer>> list, int allWords) {
        file = new File(nameFile);

        try {
            csv = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, Integer> it : list) {
            try {
                double interest = ((double) it.getValue() / allWords) * Constants.INTEREST;
                csv.write(it.getKey() + " ; " + it.getValue() + " ; " + interest + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeFile() {
        try {
            csv.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
