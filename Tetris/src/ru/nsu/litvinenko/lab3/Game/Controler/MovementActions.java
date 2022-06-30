package ru.nsu.litvinenko.lab3.Game.Controler;

import ru.nsu.litvinenko.lab3.Game.Model.FieldCell;
import ru.nsu.litvinenko.lab3.Game.Model.FigureCells;
import ru.nsu.litvinenko.lab3.Game.Model.GameFieldArray;
import ru.nsu.litvinenko.lab3.Game.Model.Point;

import java.awt.*;

import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.lang.Math.abs;

public class MovementActions {
    private FieldCell[][] field = new FieldCell[21][10];

    public MovementActions(GameFieldArray obj){
        field = obj.getField();
    }

    public void down(FigureCells fc){
        Point[] figureCoords;
        figureCoords = fc.getCell();
        Color color = fc.getColor();
        for (int i = 0; i < 4; i++){
            if (field[figureCoords[i].row + 1 + 2][figureCoords[i].col + 2].value < 0)
                return;
        }
        for (int i = 0; i < 4; i++){
            field[figureCoords[i].row + 2][figureCoords[i].col + 2].value = 0;
            field[figureCoords[i].row + 2][figureCoords[i].col + 2].color = color;
        }
        for (int i = 0; i < 4; i++){
            field[figureCoords[i].row + 1 + 2][figureCoords[i].col + 2].value = 1;
            field[figureCoords[i].row + 1 + 2][figureCoords[i].col + 2].color = color;
        }
        fc.putDown();
    }

    public void rotate(FigureCells fc){
        boolean rotateCapability = true;
        Point[] figureCoords;
        Point[] newCellCoords = new Point[4];
        Point axis = fc.getAxis();
        figureCoords = fc.getCell();
        Color color = fc.getColor();

        Point relativePosition = new Point(0, 0);
        for (int i = 0; i < 4; i++) {
            relativePosition.row = figureCoords[i].row - axis.row;
            relativePosition.col = figureCoords[i].col - axis.col;
            if (relativePosition.row == 0 && relativePosition.col == 0){
                newCellCoords[i] = new Point(axis.row, axis.col);
                continue;
            }
            if (abs(relativePosition.col) == 2 || abs(relativePosition.row) == 2) {
                if (abs(relativePosition.row) == 2) {
                    newCellCoords[i] = new Point(axis.row, axis.col - relativePosition.row);
                    if (relativePosition.row > 0)
                        rotateCapability = checkRotateCapability(newCellCoords[i], figureCoords[i], true);
                    else
                        rotateCapability = checkRotateCapability(figureCoords[i], newCellCoords[i], true);
                } else {
                    newCellCoords[i] = new Point(axis.row + relativePosition.col, axis.col);
                    if (relativePosition.row > 0)
                        rotateCapability = checkRotateCapability(newCellCoords[i], figureCoords[i], false);
                    else
                        rotateCapability = checkRotateCapability(figureCoords[i], newCellCoords[i], false);
                }
            }else{
                if (abs(relativePosition.row) + abs(relativePosition.col) == 2) {
                    if (relativePosition.row * relativePosition.col < 0) {
                        newCellCoords[i] = new Point(figureCoords[i].row - relativePosition.row * 2,  figureCoords[i].col);
                        if (field[newCellCoords[i].row + 2][newCellCoords[i].col + 2].value < 0)
                            rotateCapability = false;
                        if (relativePosition.row < 0){
                            if (field[axis.row + 2][axis.col + 1 + 2].value < 0)
                                rotateCapability = false;
                        }else{
                            if (field[axis.row + 2][axis.col - 1 + 2].value < 0)
                                rotateCapability = false;
                        }
                    }else {
                        newCellCoords[i] = new Point(figureCoords[i].row, figureCoords[i].col - relativePosition.col * 2);
                        if (field[newCellCoords[i].row + 2][newCellCoords[i].col + 2].value < 0)
                            rotateCapability = false;
                        if (relativePosition.col < 0){
                            if (field[axis.row - 1 + 2][axis.col + 2].value < 0)
                                rotateCapability = false;
                        } else{
                            if (field[axis.row + 1 + 2][axis.col + 2].value < 0)
                                rotateCapability = false;
                        }
                    }
                } else if (abs(relativePosition.row) == 1) {
                    newCellCoords[i] = new Point(axis.row, axis.col - relativePosition.row);
                    if (field[newCellCoords[i].row + 2][newCellCoords[i].col + 2].value < 0)
                        rotateCapability = false;
                    if (relativePosition.row < 0){
                        if (field[axis.row - 1 + 2][axis.col + 1 + 2].value < 0)
                            rotateCapability = false;
                    }else{
                        if (field[axis.row + 1 + 2][axis.col - 1 + 2].value < 0)
                            rotateCapability = false;
                    }
                } else {
                    newCellCoords[i] = new Point(axis.row + relativePosition.col, axis.col);
                    if (field[newCellCoords[i].row + 2][newCellCoords[i].col + 2].value < 0)
                        rotateCapability = false;
                    if (relativePosition.col < 0){
                        if (field[axis.row - 1 + 2][axis.col - 1 + 2].value < 0)
                            rotateCapability = false;
                    }else{
                        if (field[axis.col + 1 + 2][axis.col + 1 + 2].value < 0)
                            rotateCapability = false;
                    }
                }
            }
            if (!rotateCapability)
                return;
        }
        for (int i = 0; i < 4; i++){
            field[figureCoords[i].row + 2][figureCoords[i].col + 2].value = 0;
        }
        for (int i = 0; i < 4; i++){
            field[newCellCoords[i].row + 2][newCellCoords[i].col + 2].value = 1;
            field[newCellCoords[i].row + 2][newCellCoords[i].col + 2].color = color;
        }
        fc.rotateCell(newCellCoords);
    }

    public void move(int keyCode, FigureCells fc){
        Color color = fc.getColor();
        Point[] figureCoords;
        figureCoords = fc.getCell();
        if (keyCode == VK_RIGHT) {
            for (int i = 0; i < 4; i++) {
                if (field[figureCoords[i].row + 2][figureCoords[i].col + 2 + 1].value < 0){
                    return;
                }
            }
        }else{
            for (int i = 0; i < 4; i++) {
                if (field[figureCoords[i].row + 2][figureCoords[i].col + 2 - 1].value < 0){
                    return;
                }
            }
        }
        for (int i = 0; i < 4; i++){
            field[figureCoords[i].row + 2][figureCoords[i].col + 2].value = 0;
        }
        if (keyCode == VK_RIGHT) {
            for (int i = 0; i < 4; i++) {
                field[figureCoords[i].row + 2][figureCoords[i].col + 1 + 2].value = 1;
                field[figureCoords[i].row + 2][figureCoords[i].col + 1 + 2].color = color;
            }
            fc.moveToSide(false);
        }else {
            for (int i = 0; i < 4; i++) {
                field[figureCoords[i].row + 2][figureCoords[i].col - 1 + 2].value = 1;
                field[figureCoords[i].row + 2][figureCoords[i].col - 1 + 2].color = color;
            }
            fc.moveToSide(true);
        }
    }

    public void downGround(int layerNum){
        for (int i = 0; i < 10; i++){
            field[layerNum][i + 2].value = 0;
        }
        for (int i = layerNum - 1; i >= 2; i--){
            for (int j = 0; j < 10; j++){
                if (field[i][j + 2].value == -2){
                    field[i][j + 2].value = 0;
                    field[i + 1][j + 2].value = -2;
                    field[i + 1][j + 2].color = field[i][j + 2].color;
                }
            }
        }
    }

    boolean checkRotateCapability(Point upperCor, Point lowerCor, boolean orientation){
        if (orientation) {
            for (int i = upperCor.col; i <= lowerCor.col; i++) {
                for (int j = upperCor.row; j <= lowerCor.row; j++) {
                    if (field[j + 2][i + 2].value < 0) {
                        return false;
                    }
                }
            }
        }else{
            for (int i = lowerCor.col; i <= upperCor.col; i++) {
                for (int j = upperCor.row; j <= lowerCor.row; j++) {
                    if (field[j + 2][i + 2].value < 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
