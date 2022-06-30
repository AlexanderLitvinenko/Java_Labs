package ru.nsu.litvinenko.lab2.core;

import ru.nsu.litvinenko.lab2.constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Parser {
    private Stack<Double> stack;
    private Map<String, Double> mapOfDefines;
    private String[] paramsOfFunction;
    private Context context;
    private Map<String, ICommands> commandsList;
    private final Logger LOGGER = LogManager.getLogger(getClass().getName());

    public Parser() {
        LOGGER.info("CONSTRUCTOR OF PARSER START");
        stack = new Stack<>();
        mapOfDefines = new HashMap<>();
        paramsOfFunction = new String[Constants.MAX_COUNT_OF_PARAMS];
        context = new Context();
        context.setStack(stack);
        context.setMapOfDefines(mapOfDefines);
        context.setParamsOfFunction(paramsOfFunction);
        LOGGER.info("CONSTRUCTOR OF PARSER ENDED");

    }

    public void ConsoleMode() {
        LOGGER.info("CONSOLE MODE START");
        CommandsFactory commandsFactory = null;
        try {
            commandsFactory = new CommandsFactory();
        } catch (IOException e) {
            LOGGER.info("I/O EXCEPTION");
        } catch (NoSuchMethodException e) {
            LOGGER.error("NO SU METHOD EXCEPTION");
        } catch (InvocationTargetException e) {
            LOGGER.error("INVOCATION TARGET EXCEPTION");
        }
        try {
            if (commandsFactory != null) {
                commandsList = commandsFactory.makeCommand();
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            LOGGER.error("NULL POINTER EXCEPTION");
        }
        Scanner scanner = new Scanner(System.in);
        String string;
        try {
            while (true) {
                string = scanner.next();
                if (string.equals(Constants.EXIT)) {
                    throw new Exception("EXIT");
                }
                if (commandsList.containsKey(string)) {
                    for (int i = 0; i < commandsList.get(string).getCountOfParams(); i++) {
                        context.getParamsOfFunction()[i] = scanner.next();
                    }
                    commandsList.get(string).command(context);
                } else {
                    LOGGER.warn("COMMAND NOT FOUND");
                    System.out.println("Command not found");
                }
            }
        } catch (ClassCastException e) {
            LOGGER.error("CLASS CAST EXCEPTION");

        } catch (NullPointerException e) {
            LOGGER.error("NULL POINTER EXCEPTION");
        } catch (Exception stop) {
            LOGGER.error("CALC WAS STOPPED");
        }
        LOGGER.info("CONSOLE MODE FINISH");
    }

    public void fileMode(String fileName) {
        LOGGER.info("START FILE MODE");
        String[] arrayOfWords = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder string = new StringBuilder();
            String str;
            str = reader.readLine();
            while (str != null) {
                int gridExist = str.indexOf('#', 0);
                if (gridExist < 0) {
                    string.append(str).append(" ");
                }
                str = reader.readLine();
            }
            arrayOfWords = string.toString().trim().split(" ");
        } catch (FileNotFoundException e) {
            LOGGER.error("FILE NOT FOUND EXCEPTION");
        } catch (IOException e) {
            LOGGER.error("SOMETHING WRONG WITH METHOD readLine");
        }

        CommandsFactory commandsFactory = null;
        try {
            commandsFactory = new CommandsFactory();
        } catch (IOException e) {
            LOGGER.error("I/O EXCEPTION");
        } catch (NoSuchMethodException e) {
            LOGGER.error("NO SUCH METHOD EXCEPTION");
        } catch (InvocationTargetException e) {
            LOGGER.error("INVOCATION TARGET EXCEPTION");
            return;
        }

        try {
            if (commandsFactory != null) {
                commandsList = commandsFactory.makeCommand();
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            LOGGER.error("NULL POINTER EXCEPTION");
        }
        try {
            for (int i = 0; i < Objects.requireNonNull(arrayOfWords).length; i++) {
                if (commandsList.containsKey(arrayOfWords[i])) {
                    int flag = i;
                    for (int j = 0; j < commandsList.get(arrayOfWords[flag]).getCountOfParams(); j++) {
                        context.getParamsOfFunction()[j] = arrayOfWords[++i];
                    }
                    commandsList.get(arrayOfWords[flag]).command(context);
                } else {
                    LOGGER.warn("COMMAND NOT FOUND");
                    System.out.println("Command not found");
                }
            }
        } catch (NullPointerException e) {
            LOGGER.error("NULL POINTER EXCEPTION");
        } catch (ClassCastException e) {
            LOGGER.error("CLASS CAST EXCEPTION");
        }
        LOGGER.info("FILE MODE ENDED");
    }

}