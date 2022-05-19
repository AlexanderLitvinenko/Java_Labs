package ru.nsu.litvinenko.lab2.commands;


import ru.nsu.litvinenko.lab2.constants.Constants;
import ru.nsu.litvinenko.lab2.core.Context;
import ru.nsu.litvinenko.lab2.core.ICommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EmptyStackException;
import java.util.Stack;

public class Divide implements ICommands {
    private final Logger LOGGER = LogManager.getLogger(getClass().getName());
    @Override
    public void command(Context context) {
        LOGGER.info("START DIVIDE");
        Stack<Double> stack = context.getStack();
        try {
            if (stack.size() >= Constants.MINIMUM_STACK_SIZE) {
                double first = stack.pop();
                if (stack.peek() == 0) {
                    stack.push(first);
                    throw new ArithmeticException("You can not divide by zero");
                } else {
                    stack.push(first / stack.pop());
                }
            } else {
                throw new EmptyStackException();
            }
        } catch (ArithmeticException e) {
            LOGGER.error("DIVIDE BY ZERO EXCEPTION");
        }catch (EmptyStackException emptyStackException){
            LOGGER.error("EMPTY STACK EXCEPTION");
        }
        LOGGER.info("DIVIDE ENDED");
    }

    @Override
    public int getCountOfParams() {
        return 0;
    }
}
