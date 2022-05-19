package ru.nsu.litvinenko.lab2.core;

import ru.nsu.litvinenko.lab2.constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException {
        LOGGER.info("START MAIN");
        Parser parser = new Parser();
        if (args.length == Constants.COUNTS_OF_ARGUMENTS_FOR_CONSOLE_MODE) {
            LOGGER.info("CONSOLE MODE START");
            parser.ConsoleMode();
            LOGGER.info("CONSOLE MODE ENDED");
        } else {
            LOGGER.info("FILE MODE START");
            parser.fileMode(args[0]);
            LOGGER.info("FILE MODE ENDED");
        }
        LOGGER.info("MAIN ENDED");
    }
}
