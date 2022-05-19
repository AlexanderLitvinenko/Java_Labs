import ru.nsu.litvinenko.lab2.commands.*;
import ru.nsu.litvinenko.lab2.core.Context;
import ru.nsu.litvinenko.lab2.core.ICommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class TestCommands {
    static private final Logger LOGGER = LogManager.getLogger(TestCommands.class.getName());
    private final String LOGGER_TEST = "TEST";

    private final String DEFINE_A = "a";
    private final String DEFINE_8_STR = "8";
    private final String DEFINE_B = "b";
    private final String DEFINE_BS = "bs";
    private final String DEFINE_AS = "as";
    private final double DEFINE_8_DOUBLE = 8.0;


    @Test
    public void testCommandDefine() {
        ICommands defineCommand = new Define();
        Map<String, Double> define = new HashMap<>();
        Context context = new Context();
        context.setMapOfDefines(define);

        String[] values = new String[2];
        context.setParamsOfFunction(values);

        context.getParamsOfFunction()[0] = PUSH_A;
        context.getParamsOfFunction()[1] = DEFINE_8_STR;
        defineCommand.command(context);

        context.getParamsOfFunction()[0] = DEFINE_B;
        context.getParamsOfFunction()[1] = PUSH_A;
        defineCommand.command(context);

        context.getParamsOfFunction()[0] = DEFINE_BS;
        context.getParamsOfFunction()[1] = DEFINE_AS;
        defineCommand.command(context);

        Map<String, Double> expected = new HashMap<>();
        expected.put(PUSH_A, DEFINE_8_DOUBLE);
        expected.put(DEFINE_B, DEFINE_8_DOUBLE);
        LOGGER.info(defineCommand.getClass().getSimpleName() + LOGGER_TEST);
        Assert.assertEquals(expected, define);
    }

    private final double DIVISION_MINUS_ZERO = -0.0;
    private final double DIVISION_TWO = 2.0;
    private final double DIVISION_SIX = 6.0;
    private final double DIVISION_THREE = 3.0;

    @Test
    public void testCommandDivision() throws Exception {
        ICommands divisionCommand = new Divide();
        Stack stack = new Stack();
        stack.push(DIVISION_MINUS_ZERO);
        stack.push(DIVISION_TWO);
        stack.push(DIVISION_SIX);

        Context context = new Context();
        context.setStack(stack);

        divisionCommand.command(context);
        divisionCommand.command(context);

        Stack expected = new Stack();
        expected.push(DIVISION_MINUS_ZERO);
        expected.push(DIVISION_THREE);

        LOGGER.info(divisionCommand.getClass().getSimpleName() + LOGGER_TEST);
        Assert.assertEquals(expected, stack);
    }

    private final double MINUS_THREE = 3.0;
    private final double MINUS_TWO = 2.0;
    private final double MINUS_MINUS_ONE = -1.0;

    @Test
    public void testCommandMinus() {
        ICommands minusCommand = new Minus();
        Stack stack = new Stack();
        stack.push(MINUS_THREE);
        stack.push(MINUS_TWO);

        Context context = new Context();
        context.setStack(stack);

        minusCommand.command(context);
        minusCommand.command(context);

        Stack expected = new Stack();
        expected.push(MINUS_MINUS_ONE);

        LOGGER.info(minusCommand.getClass().getSimpleName() + LOGGER_TEST);
        Assert.assertEquals(expected, stack);
    }

    private final double MULTIPLICATION_THREE = 3.0;
    private final double MULTIPLICATION_MINUS_TWO = -2.0;
    private final double MULTIPLICATION_MINUS_SIX = -6.0;

    @Test
    public void testCommandMultiplication() {
        ICommands multiplicationCommand = new Multiply();
        Stack stack = new Stack();
        stack.push(MULTIPLICATION_THREE);
        stack.push(MULTIPLICATION_MINUS_TWO);

        Context context = new Context();
        context.setStack(stack);

        multiplicationCommand.command(context);
        
        multiplicationCommand.command(context);
        

        Stack expected = new Stack();
        expected.push(MULTIPLICATION_MINUS_SIX);

        LOGGER.info(multiplicationCommand.getClass().getSimpleName() + LOGGER_TEST);
        Assert.assertEquals(expected, stack);
    }

    private final double PLUS_ONE = 1.0;
    private final double PLUS_MINUS_TWO = -2.0;
    private final double PLUS_MINUS_ONE = -1.0;

    @Test
    public void testCommandPlus() {
        ICommands plusCommand = new Plus();
        Stack stack = new Stack();
        stack.push(PLUS_ONE);
        stack.push(PLUS_MINUS_TWO);

        Context context = new Context();
        context.setStack(stack);

        plusCommand.command(context);
        ;
        plusCommand.command(context);
        ;

        Stack expected = new Stack();
        expected.push(PLUS_MINUS_ONE);

        LOGGER.info(plusCommand.getClass().getSimpleName() + LOGGER_TEST);
        Assert.assertEquals(expected, stack);
    }

    private final double POP_ONE = 1.0;

    @Test
    public void testCommandPop() {
        ICommands popCommand = new Pop();
        Stack stack = new Stack();
        stack.push(POP_ONE);

        Context context = new Context();
        context.setStack(stack);

        popCommand.command(context);
        ;
        popCommand.command(context);
        ;

        Stack expected = new Stack();

        LOGGER.info(popCommand.getClass().getSimpleName() + LOGGER_TEST);
        Assert.assertEquals(expected, stack);
    }

    private final double PRINT_ONE = 1.0;

    @Test
    public void testCommandPrint() {
        ICommands printCommand = new Print();
        Stack stack = new Stack();
        stack.push(PRINT_ONE);

        Context context = new Context();
        context.setStack(stack);

        printCommand.command(context);
        ;

        Stack expected = new Stack();
        expected.push(PRINT_ONE);

        LOGGER.info(printCommand.getClass().getSimpleName() + LOGGER_TEST);
        Assert.assertEquals(expected, stack);
    }

    private final String PUSH_A = "a";
    private final double PUSH_TWO = 2.0;
    private final String PUSH_B = "b";
    private final String PUSH_ONE_STR = "1";
    private final Double PUSH_ONE_DOUBLE = 1.0;

    @Test
    public void testCommandPush() {
        ICommands pushCommand = new Push();
        TreeMap<String, Double> define = new TreeMap<>();
        define.put(PUSH_A, PUSH_TWO);
        Stack stack = new Stack();

        Context context = new Context();
        context.setStack(stack);
        context.setMapOfDefines(define);

        String[] values = new String[2];
        context.setParamsOfFunction(values);

        context.getParamsOfFunction()[0] = PUSH_ONE_STR;
        pushCommand.command(context);
        context.getParamsOfFunction()[0] = PUSH_B;
        pushCommand.command(context);
        context.getParamsOfFunction()[0] = PUSH_A;
        pushCommand.command(context);
        Stack expected = new Stack();
        expected.push(PUSH_ONE_DOUBLE);
        expected.push(PUSH_TWO);

        LOGGER.info(pushCommand.getClass().getSimpleName() + LOGGER_TEST);
        Assert.assertEquals(expected, stack);
    }

    private final double SQRT_FOUR = 4.0;
    private final double SQRT_MINUS_ONE = -1.0;
    private final double SQRT_MINUS_ZERO = -0.0;
    private final double SQRT_TWO = 2.0;

    @Test
    public void testCommandSqrt() {
        ICommands sqrtCommand = new Sqrt();
        Stack stack = new Stack();
        Context context = new Context();
        context.setStack(stack);
        stack.push(SQRT_FOUR);
        sqrtCommand.command(context);
        ;
        stack.push(SQRT_MINUS_ONE);
        sqrtCommand.command(context);
        ;
        stack.push(SQRT_MINUS_ZERO);
        sqrtCommand.command(context);
        ;

        Stack expected = new Stack();
        expected.push(SQRT_TWO);
        expected.push(SQRT_MINUS_ONE);
        expected.push(SQRT_MINUS_ZERO);

        LOGGER.info(sqrtCommand.getClass().getSimpleName() + LOGGER_TEST);
        Assert.assertEquals(expected, stack);
    }
}