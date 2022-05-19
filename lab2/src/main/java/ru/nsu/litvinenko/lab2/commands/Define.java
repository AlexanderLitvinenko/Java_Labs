package ru.nsu.litvinenko.lab2.commands;

import ru.nsu.litvinenko.lab2.constants.Constants;
import ru.nsu.litvinenko.lab2.core.Context;
import ru.nsu.litvinenko.lab2.core.ICommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class Define implements ICommands {
    private final Logger LOGGER = LogManager.getLogger(getClass().getName());

    private boolean isDigit(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            LOGGER.error("NUMBER FORMAT EXCEPTION");
            return false;
        }
    }

    @Override
    public void command(Context context) {
        try {
            LOGGER.info("START DEFINE");
            if (context.getParamsOfFunction().length < Constants.MAX_COUNT_OF_PARAMS) throw new Exception();
            String[] paramsOfFunction = context.getParamsOfFunction();
            String key = paramsOfFunction[Constants.MINIMUM_COUNT_OF_PARAMS];
            String variable = paramsOfFunction[1];
            Map<String, Double> mapOfDefines = context.getMapOfDefines();
            if (isDigit(variable)) {
                double value = Double.parseDouble(variable);
                if (mapOfDefines.containsKey(key)) {
                    mapOfDefines.replace(key, value);
                } else {
                    mapOfDefines.put(key, value);
                }
            } else if (mapOfDefines.containsKey(variable)) {
                mapOfDefines.put(key, mapOfDefines.get(variable));
            } else {

                LOGGER.warn("SYNTAX ERROR");
            }
        } catch (Exception e) {
            LOGGER.error("THE NUMBERS OF OF PARAMETERS IS NOT ENOUGH");
        }
        LOGGER.info("DEFINE ENDED");
    }

    @Override
    public int getCountOfParams() {
        return 2;
    }
}