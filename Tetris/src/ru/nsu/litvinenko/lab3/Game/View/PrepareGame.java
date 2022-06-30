package ru.nsu.litvinenko.lab3.Game.View;

import ru.nsu.litvinenko.lab3.Game.Controler.GameState;
import ru.nsu.litvinenko.lab3.Game.Controler.MovementActions;
import ru.nsu.litvinenko.lab3.Game.Model.FigureCells;
import ru.nsu.litvinenko.lab3.Game.Model.GameFieldArray;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PrepareGame {
    public void startGame (JFrame menu){
        menu.setVisible(false);
        JFrame gameField = new JFrame() {};
        gameField.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameField.setSize(415, 840);
        gameField.setLocationRelativeTo(null);
        gameField.setVisible(true);

        GameFieldArray gf = new GameFieldArray();

        MovementActions mv = new MovementActions(gf);

        Canvas canvas = new Canvas(gf);
        gameField.add(canvas);
        GameState gs = new GameState();

        FigureCells fc = new FigureCells();

        gameField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) mv.down(fc);
                if (e.getKeyCode() == KeyEvent.VK_UP) mv.rotate(fc);
                if (e.getKeyCode() == KeyEvent.VK_LEFT|| e.getKeyCode() == KeyEvent.VK_RIGHT) mv.move(e.getKeyCode(), fc);
                canvas.repaint();
            }
        });

        GameCycle gc = new GameCycle(gameField, canvas, gs, gf, mv, fc, menu);
        gc.go();

    }
}
