package ru.nsu.litvinenko.lab1;


import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        String inFile = args[Constants.FILE_IN];
        String outFile = args[Constants.FILE_OUT];
        reader.openFile(inFile);
        String string = reader.getString();
        Parser parser = new Parser();
        while (string != null) {
            parser.counter(string);
            string = reader.getString();
        }
        Pair[] sortedList = parser.makeList();
        Writer printer = new Writer();
        printer.makeCSV(outFile, sortedList, parser.getAllWords());
        reader.closeFile();
        printer.close();
    }
}
