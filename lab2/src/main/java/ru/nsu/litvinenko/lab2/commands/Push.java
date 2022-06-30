package ru.nsu.litvinenko.lab2.commands;


import ru.nsu.litvinenko.lab2.constants.Constants;
import ru.nsu.litvinenko.lab2.core.Context;
import ru.nsu.litvinenko.lab2.core.ICommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Stack;

public class Push implements ICommands {
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
        LOGGER.info("PUSH START");
        try {
            if (context.getParamsOfFunction().length < Constants.MINIMUM_COUNT_OF_PARAMS_FOR_PUSH) throw new Exception();
            String[] paramsOfFunction = context.getParamsOfFunction();
            Map<String, Double> map = context.getMapOfDefines();
            Stack<Double> stack = context.getStack();
            String string = paramsOfFunction[Constants.MINIMUM_COUNT_OF_PARAMS];
            if (map.containsKey(string)) {
                stack.push(map.get(string));
            } else if (isDigit(string)) {
                stack.push(Double.parseDouble(string));
            } else {
                LOGGER.warn("SYNTAX ERROR");
            }
        } catch (Exception e) {
            LOGGER.error("THE NUMBER OF PARAMETERS IS NOT ENOUGH");
        }
        LOGGER.info("PUSH ENDED");
    }

    @Override
    public int getCountOfParams() {
        return 1;
    }
}