package ru.nsu.litvinenko.lab2.commands;

import ru.nsu.litvinenko.lab2.core.Context;
import ru.nsu.litvinenko.lab2.core.ICommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EmptyStackException;
import java.util.Stack;

public class Pop implements ICommands {
    private final Logger LOGGER = LogManager.getLogger(getClass().getName());

    @Override
    public void command(Context context) {
        LOGGER.info("POP START");
        try {
            Stack<Double> stack = context.getStack();
            if (!stack.isEmpty()) {
                stack.pop();
            } else {
                throw new EmptyStackException();
            }
        } catch (EmptyStackException e) {
            LOGGER.error("EMPTY STACK EXCEPTION");
        }
        LOGGER.info("POP ENDED");
    }
}
