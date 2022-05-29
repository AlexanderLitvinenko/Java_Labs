package ru.nsu.litvinenko.lab3.Game.Model.Figures;

import java.awt.*;

public abstract class Figure {
    int[][] form = new int[3][10];
    Color color;
    public abstract void makeFigure();
    public int[][] getForm(){
        return form;
    }
    public Color getColor(){
        return color;
    }
}
