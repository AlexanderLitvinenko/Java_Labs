package ru.nsu.litvinenko.lab3.Game.Model;

import ru.nsu.litvinenko.lab3.Game.Controler.MovementActions;
import ru.nsu.litvinenko.lab3.Game.Model.Figures.Figure;

import java.awt.*;

public class GameFieldArray {

    private FieldCell[][] field = new FieldCell[24][14];
    public GameFieldArray(){
        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 2; j++){
                field[i + 2][j] = new FieldCell(Color.WHITE, -1);
                field[i + 2][13 - j] = new FieldCell(Color.WHITE, -1);
            }
            for (int j = 2; j  < 12; j++){
                field[i + 2][j] = new FieldCell(Color.WHITE, 0);
            }
        }
        for (int i = 0 ; i < 2; i++) {
            for (int j = 0; j < 14; j++) {
                field[i][j] = new FieldCell(Color.WHITE, -1);
                field[23 - i][j] = new FieldCell(Color.WHITE, -1);
            }
        }
    }

    public void addFigure(Figure figure, Color _color){
        int[][] figArray = figure.getForm();// to controller
        for (int i = 0; i < 3; i++){
            for (int j = 2; j < 12; j++){
                field[i + 2][j].value += figArray[i][j - 2];
                field[i + 2][j].color = _color;
            }
        }
    }

    public void makeGround(FigureCells fc){
        Color color = fc.getColor(); //to controller+
        Point[] figureCoords;
        figureCoords = fc.getCell();
        for (int i = 0; i < 4; i++){
            field[figureCoords[i].row + 2][figureCoords[i].col + 2].value = -2;
            field[figureCoords[i].row + 2][figureCoords[i].col + 2].color = color;
        }
    }

    public int clearLayer(MovementActions mv){
        int additionalPoints = 0;
        int k = 0;
        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 10; j++){
                if (field[i + 2][j + 2].value == -2){
                    k++;
                }
            }
            if (k == 10){
                additionalPoints = 100;
                mv.downGround(i + 2);
            }
            k = 0;
        }
        return additionalPoints;
    }

    public FieldCell getCell(int str, int col){
        return field[str][col];
    }
    public FieldCell[][] getField() {return field;}
}
