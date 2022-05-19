package ru.nsu.litvinenko.lab2.core;

import ru.nsu.litvinenko.lab2.constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CommandsFactory {
    private BufferedReader reader;
    private Map<String, ICommands> mapOfCommands;

    CommandsFactory() throws IOException, NoSuchMethodException, InvocationTargetException {
        Logger LOGGER = LogManager.getLogger(getClass().getName());
        try {
            LOGGER.info("FACTORY START");
            reader = new BufferedReader(new FileReader(Constants.PATH_TO_CONFIGURATION_FILE));
            mapOfCommands = new HashMap<>();
        } catch (FileNotFoundException e) {
            LOGGER.error("FILE NOT FOUND EXCEPTION");
        }
        String string = reader.readLine();
        while (string != null) {
            try {
                String key = string.substring(0, string.indexOf(' '));
                string = string.substring(string.indexOf(' ') + 1);
                Class<?> myClass = Class.forName(string);
                ICommands command = (ICommands) myClass.getDeclaredConstructor().newInstance();
                LOGGER.info("COMMAND" + command + "HAS BEEN MADE");
                mapOfCommands.put(key, command);
                string = reader.readLine();
            } catch (ClassNotFoundException e) {
                LOGGER.error("CLASS NOT FOUND EXCEPTION");
            } catch (InstantiationException e) {
                LOGGER.error("INSTANTIATION EXCEPTION");
            } catch (IllegalAccessException e) {
                LOGGER.error("ILLEGAL ACCESS EXCEPTION");
            }
        }
    }

    public Map<String, ICommands> makeCommand() {
        return mapOfCommands;
    }

}
