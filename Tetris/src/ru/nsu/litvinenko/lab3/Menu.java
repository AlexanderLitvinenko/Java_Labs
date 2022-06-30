package ru.nsu.litvinenko.lab3;

import ru.nsu.litvinenko.lab3.Game.View.PrepareGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    static void showMenu() {
        JFrame jFrame = new JFrame() {};
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(1024, 768);
        jFrame.setLocationRelativeTo(null);
        jFrame.setTitle("ru/nsu/litvinenko/lab3");

        JPanel jPanel = new JPanel();
        jFrame.add(jPanel);

        JButton playButton = new JButton("New Game");
        playButton.setSize(224, 100);
        playButton.setLocation(400, 117);
        jPanel.add(playButton);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrepareGame gf = new PrepareGame();
                gf.startGame(jFrame);
            }
        });
        JButton leaderBoardButton = new JButton("High scores");
        jPanel.add(leaderBoardButton);
        leaderBoardButton.setSize(224, 100);
        leaderBoardButton.setLocation(400, 234);

        leaderBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LeaderBoard lb = new LeaderBoard();
                lb.show(jFrame);
            }
        });
        JButton exitButton = new JButton("Exit");
        jPanel.add(exitButton);
        exitButton.setSize(224, 100);
        exitButton.setLocation(400, 234);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
