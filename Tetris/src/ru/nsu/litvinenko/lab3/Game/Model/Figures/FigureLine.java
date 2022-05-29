package ru.nsu.litvinenko.lab3.Game.Model.Figures;

import java.awt.*;

public class FigureLine extends Figure{
    @Override
    public void makeFigure() {
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 10; j++){
                super.form[i][j] = 0;
            }
        }
        for (int i = 0; i < 4; i++){
            super.form[0][3+i] = 1;
        }
        color = Color.YELLOW;
    }
}
