package ru.nsu.litvinenko.lab1;

import java.io.*;


public class Writer implements Closeable {
    private File file;
    private BufferedWriter csv;

    public void makeCSV(String nameFile, Pair[] array, int allWords) {
        file = new File(nameFile);
        try {
            csv = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < array.length; i++) {
            try {
                double interest = ((double) array[i].getValue() / allWords) * Constants.INTEREST;
                csv.write(array[i].getWord() + " ; " + array[i].getValue() + " ; " + interest + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //
//    public void closeFile() {
//        try {
//            csv.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public void close() throws IOException {
        try {
            csv.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
