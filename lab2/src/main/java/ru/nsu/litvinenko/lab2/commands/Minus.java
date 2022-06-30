package ru.nsu.litvinenko.lab2.commands;


import ru.nsu.litvinenko.lab2.constants.Constants;
import ru.nsu.litvinenko.lab2.core.Context;
import ru.nsu.litvinenko.lab2.core.ICommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EmptyStackException;
import java.util.Stack;


public class Minus implements ICommands {
    private final Logger LOGGER = LogManager.getLogger(getClass().getName());

    @Override
    public void command(Context context) {
        LOGGER.info("START COMMAND");
        Stack<Double> stack = context.getStack();
        try {
            if (stack.size() >= Constants.MINIMUM_STACK_SIZE) {
                stack.push(stack.pop() - stack.pop());
            } else {
                throw new EmptyStackException();
            }
        } catch (EmptyStackException e) {
            LOGGER.error("EMPTY STACK EXCEPTION");
        }
        LOGGER.info("MINUS ENDED");
    }
}