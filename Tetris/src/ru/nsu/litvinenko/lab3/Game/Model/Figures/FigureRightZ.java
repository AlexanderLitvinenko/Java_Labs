package ru.nsu.litvinenko.lab3.Game.Model.Figures;

import java.awt.*;

public class FigureRightZ extends Figure{
    @Override
    public void makeFigure() {
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 10; j++){
                super.form[i][j] = 0;
            }
        }
        super.form[0][5] = 1;
        super.form[0][6] = 1;
        super.form[1][5] = 1;
        super.form[1][4] = 1;
        color = Color.BLUE;
    }
}
