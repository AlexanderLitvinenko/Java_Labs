package ru.nsu.litvinenko.lab3;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderBoard {
    public void show(JFrame menuFrame){
        menuFrame.setVisible(false);
        JFrame leaderBoardFrame = new JFrame() {};
        leaderBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        leaderBoardFrame.setSize(400, 800);
        leaderBoardFrame.setLocationRelativeTo(null);
        leaderBoardFrame.setVisible(true);
        Map<String , Integer> scoreMap = new HashMap<>();
        BufferedReader br = null;
        String str;
        try {
            br = new BufferedReader(new FileReader("leaderBoard.txt"));
            while ((str = br.readLine()) != null){
                scoreMap.put(str.substring(0, str.indexOf(" ")), Integer.valueOf(str.substring(str.indexOf(" ") + 1)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel lb = new JLabel();
        leaderBoardFrame.add(lb);
        lb.setText(" ");
        Border border = BorderFactory.createLineBorder(Color.green, 3);
        List<Map.Entry<String, Integer>> mapList = new ArrayList<>(scoreMap.entrySet());
        JLabel[] labels = new JLabel[4];
        for (int i = 0; i < 4; i++){
            labels[i] = new JLabel();

            leaderBoardFrame.add(labels[i]);
            labels[i].setSize(400, 200);
            labels[i].setLocation(0, i*200);
            labels[i].setBorder(border);
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
            labels[i].setText(mapList.get(i).getKey()+ " " + mapList.get(i).getValue());
        }
    }
}
