package ru.nsu.litvinenko.lab3.Game.Controler;

import ru.nsu.litvinenko.lab3.Game.Model.FieldCell;

public class GameState {
    public boolean getGameOver(FieldCell[][] field) {
        for (int j = 0; j < 10; j++) {
            if (field[2][j + 2].value < 0) {
                return true;
            }
        }
        return false;
    }

    public boolean figureOnGround(FieldCell[][] field) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if (field[i + 2][j + 2].value == 1) {
                    if (field[i + 1 + 2][j + 2].value < 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
