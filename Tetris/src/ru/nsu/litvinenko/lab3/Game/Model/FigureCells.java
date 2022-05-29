package ru.nsu.litvinenko.lab3.Game.Model;

import java.awt.*;

public class FigureCells {
    private ru.nsu.litvinenko.lab3.Game.Model.Point[] cell = new ru.nsu.litvinenko.lab3.Game.Model.Point[4];
    private ru.nsu.litvinenko.lab3.Game.Model.Point axis;
    private Color color;
    public void setCell(int[][] field, Color _color){
        int k = 0;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 10; j++){
                if (field[i][j] == 1){
                    cell[k] = new ru.nsu.litvinenko.lab3.Game.Model.Point(i, j);
                    k++;
                }
            }
        }
        getAxisPos(field);
        color = _color;
    }

    public ru.nsu.litvinenko.lab3.Game.Model.Point[] getCell() {
        return cell;
    }

    public Color getColor(){
        return color;
    }

    public ru.nsu.litvinenko.lab3.Game.Model.Point getAxis() {
        return axis;
    }

    public void putDown(){
        for (int i = 0; i < 4; i++){
            cell[i].row++;
        }
        axis.row++;
    }

    public void moveToSide(boolean left) {
        if (left) {
            for (int i = 0; i < 4; i++) {
                cell[i].col--;
            }
            axis.col--;
        } else {
            for (int i = 0; i < 4; i++) {
                cell[i].col++;
            }
            axis.col++;
        }
    }

    public void rotateCell(ru.nsu.litvinenko.lab3.Game.Model.Point[] newCoords){
        for (int i = 0; i < 4; i++){
                cell[i].row = newCoords[i].row;
                cell[i].col = newCoords[i].col;
        }
    }

    void getAxisPos(int[][] field){
        for (int i = 0; i < 2; i++){
            if (field[cell[i].row + 1][cell[i].col] + field[cell[i].row][cell[i].col + 1] + field[cell[i].row][cell[i].col - 1] >= 2){
                axis = new Point(cell[i].row, cell[i].col);
            }
        }
    }
}
