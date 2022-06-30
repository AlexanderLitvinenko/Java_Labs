package ru.nsu.litvinenko.lab1;

import java.io.*;


public class Reader {
    private BufferedReader reader;

    public void openFile(String nameFile) {
        try {
            reader = new BufferedReader(new FileReader(nameFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getString() {
        String string = null;
        try {
            string = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    public void closeFile() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}