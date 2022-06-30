package ru.nsu.litvinenko.lab5.client;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import ru.nsu.litvinenko.lab5.constants.Constants;
import ru.nsu.litvinenko.lab5.general.GsonMessage;
import ru.nsu.litvinenko.lab5.general.SocketConnect;

import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.Set;

public class Connect implements Runnable {
    private SocketConnect socketConnect;
    private ObservableList<String> members;
    private ObservableList<Label> chat;


    public Connect(SocketConnect socketConnect, ObservableList<String> members, ObservableList<Label> chat) {
        this.members = members;
        this.chat = chat;
        this.socketConnect = socketConnect;

    }

    @Override
    public void run() {
        try {
            socketConnect.getSocket().setSoTimeout(Constants.TIMEOUT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (socketConnect.getScanner().hasNextLine()) {
            String line = socketConnect.getScanner().nextLine();
            System.out.println("SSSSSSSS="+line+"\n");
            GsonMessage gsonMessage = socketConnect.getGson().fromJson(line, GsonMessage.class);
            if (gsonMessage.getMembersOfChat() != null) {
                Set<String> messageSet = gsonMessage.getMembersOfChat();
                for (String client : messageSet)
                    if (!members.contains(client))
                        Platform.runLater(() -> members.add(client));
                Platform.runLater(() -> members.removeIf(client -> !messageSet.contains(client)));
            } else {
                if (gsonMessage.getName() == null) {
                    String text = gsonMessage.getMessage().trim();
                    Label label = new Label(text);
                    Platform.runLater(() -> chat.add(label));
                } else {
                    String text = gsonMessage.getName() + gsonMessage.getDateAndTime() + Constants.NEXT_LINE + gsonMessage.getMessage();
                    Label label = new Label(text);
                    Platform.runLater(() -> chat.add(label));
                }
            }
        }
    }
}
