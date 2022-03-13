package com.company;


import java.util.List;
import java.util.Map;


public class Main {

    public static void main(String[] args) {
        MyScanner myScanner = new MyScanner();
        String inFile = args[Constants.FILE_IN];
        String outFile = args[Constants.FILE_OUT];
        myScanner.openFile(inFile);
        String string = myScanner.getString();
        Parser parser = new Parser();
        while (string != null) {
            parser.counter(string);
            string = myScanner.getString();
        }
        List<Map.Entry<String, Integer>> sortedList = parser.makeList();
        MyPrinter printer = new MyPrinter();
        printer.makeCSV(outFile, sortedList, parser.getAllWords());
        myScanner.closeFile();
        printer.closeFile();
    }
}
