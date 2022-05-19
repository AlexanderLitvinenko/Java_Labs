package ru.nsu.litvinenko.lab2.core;

import java.util.Map;
import java.util.Stack;

public class Context {

    private Stack<Double> stack;
    private Map<String, Double> mapOfDefines;
    private String[] paramsOfFunction;

    public Stack<Double> getStack() {
        return stack;
    }

    public void setStack(Stack<Double> stack) {
        this.stack = stack;
    }

    public Map<String, Double> getMapOfDefines() {
        return mapOfDefines;
    }

    public void setMapOfDefines(Map<String, Double> map) {
        this.mapOfDefines = map;
    }

    public String[] getParamsOfFunction() {
        return paramsOfFunction;
    }

    public void setParamsOfFunction(String[] paramsOfFunction) {
        this.paramsOfFunction = paramsOfFunction;
    }

    @Override
    public String toString() {
        return "BaseContext{" +
                "stack=" + stack +
                ", defines=" + mapOfDefines + '\'' +
                '}';
    }
}

