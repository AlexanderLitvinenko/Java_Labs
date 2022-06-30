package ru.nsu.litvinenko.lab3.Game.Controler;

import ru.nsu.litvinenko.lab3.Game.Model.Figures.*;

public class FigureFactory {
    public Figure getFigure(int Num){
        Figure obj = null;
        switch (Num){
            case 0:
                obj = new FigureLeftHorse();
                obj.makeFigure();
                break;
            case 1:
                obj = new FigureLeftZ();
                obj.makeFigure();
                break;
            case 2:
                obj = new FigureLine();
                obj.makeFigure();
                break;
            case 3:
                obj = new FigureRightHorse();
                obj.makeFigure();
                break;
            case 4:
                obj = new FigureRightZ();
                obj.makeFigure();
                break;
            case 5:
                obj = new FigureSquare();
                obj.makeFigure();
                break;
            case 6:
                obj = new FigureT();
                obj.makeFigure();
                break;
        }
        return obj;
    }
}
