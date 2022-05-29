package ru.nsu.litvinenko.lab3.Game.View;

import ru.nsu.litvinenko.lab3.Game.Model.FieldCell;
import ru.nsu.litvinenko.lab3.Game.Model.GameFieldArray;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    private FieldCell[][] field = new FieldCell[24][14];

    public Canvas(GameFieldArray obj){
        field = obj.getField();
    }

    public void paint(Graphics g){
        super.paint(g);
        for (int i = 0 ; i <11; i++){
            g.drawLine(40*i, 0, 40*i, 800);
        }
        for (int i = 0; i <21; i++){
            g.drawLine(0, 40*i, 400, 40*i);
        }
        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 10; j++){
                if (field[i+2][j+2].value == 1) {
                    g.setColor(field[i + 2][j + 2].color);
                    g.fill3DRect(40 * j, 40 * i, 40, 40, true);
                }
                if (field[i + 2][j + 2].value == -2) {
                    g.setColor(field[i + 2][j + 2].color);
                    g.fill3DRect(40 * j, 40 * i, 40, 40, true);
                }
            }
        }
    }
}
