package ru.nsu.litvinenko.lab3.Game.View;

import ru.nsu.litvinenko.lab3.Game.Controler.FigureFactory;
import ru.nsu.litvinenko.lab3.Game.Controler.GameState;
import ru.nsu.litvinenko.lab3.Game.Controler.MovementActions;
import ru.nsu.litvinenko.lab3.Game.Model.FigureCells;
import ru.nsu.litvinenko.lab3.Game.Model.Figures.Figure;
import ru.nsu.litvinenko.lab3.Game.Model.GameFieldArray;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;


public class GameCycle{
     private int score;
     JFrame menuFrame;
     JFrame frame;
     ru.nsu.litvinenko.lab3.Game.View.Canvas canvas;
     GameState gs;
     GameFieldArray gf;
     MovementActions mv;
     FigureCells fc;

    public GameCycle(JFrame _frame, Canvas _canvas, GameState _gs, GameFieldArray _gf, MovementActions _mv, FigureCells _fc, JFrame menu){
        score = 0;
        frame = _frame;
        canvas = _canvas;
        gs = _gs;
        gf = _gf;
        mv = _mv;
        fc = _fc;
        menuFrame = menu;
    }

    public void go(){
        frame.setTitle("Score: " + 0);
        figureDrop();
        Timer timer1 = new Timer(500, e -> {
            canvas.repaint();
            if (!gs.figureOnGround(gf.getField())){
                mv.down(fc);
            }else {
                gf.makeGround(fc);
                if (gs.getGameOver(gf.getField())){
                    finishGame();
                    return;
                }
                score += gf.clearLayer(mv);
                frame.setTitle("Score: " + score);
                figureDrop();
            }
        });
        timer1.start();
    }

    void figureDrop(){
        FigureFactory ff = new FigureFactory();
        int randomNum;
        randomNum = (int)(Math.random()*6);
        Figure fig = ff.getFigure(randomNum);
        Color color = fig.getColor();
        fc.setCell(fig.getForm(), color);
        gf.addFigure(fig, color);
    }

    void finishGame(){
        frame.setVisible(false);
        JFrame enterName = new JFrame() {};
        enterName.setVisible(true);
        enterName.setSize(500, 300);
        enterName.setLocationRelativeTo(null);
        enterName.setTitle("Enter your name");
        JPanel jPanel = new JPanel();
        enterName.add(jPanel);
        JButton enter = new JButton("ENTER");
        jPanel.add(enter);
        JTextField jTextField = new JTextField("", 20);
        jPanel.add(jTextField);
        Map<String , Integer> scoreMap = new HashMap<>();
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = jTextField.getText();
                try {
                    BufferedReader br = new BufferedReader(new FileReader("leaderBord.txt"));
                    String str;
                    while ((str = br.readLine()) != null){
                        scoreMap.put(str.substring(0, str.indexOf(" ")), Integer.valueOf(str.substring(str.indexOf(" ") + 1)));
                    }
                    scoreMap.put(name, score);
                    List<Map.Entry<String, Integer>> mapList = new ArrayList<>(scoreMap.entrySet());
                    mapList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
                    FileWriter fw= new FileWriter("leaderBord.txt");
                    PrintWriter pw = new PrintWriter(fw);
                    for (int i = 0; i < mapList.size(); i++) {
                        pw.println(mapList.get(i).getKey()+" "+mapList.get(i).getValue());
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                menuFrame.setVisible(true);
                enterName.setVisible(false);
                frame.setVisible(false);

            }
        });
        jPanel.revalidate();
    }
}
