package ru.nsu.litvinenko.lab2.commands;

import ru.nsu.litvinenko.lab2.constants.Constants;
import ru.nsu.litvinenko.lab2.core.Context;
import ru.nsu.litvinenko.lab2.core.ICommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EmptyStackException;
import java.util.Stack;

public class Sqrt implements ICommands {
    private final Logger LOGGER = LogManager.getLogger(getClass().getName());

    @Override
    public void command(Context context) {
        LOGGER.info("SQRT START");
        Stack<Double> stack = context.getStack();
        try {
            if (!stack.isEmpty()) {
                if (stack.peek() < Constants.ZERO) {
                    throw new IllegalArgumentException();
                }
                stack.push(Math.sqrt((stack.pop())));
            } else {
                throw new EmptyStackException();
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            LOGGER.error("ILLEGAL ARGUMENT EXCEPTION");
        }
        LOGGER.info("SQRT ENDED");
    }
}