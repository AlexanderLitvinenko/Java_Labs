package ru.nsu.litvinenko.lab5.client;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import ru.nsu.litvinenko.lab5.general.GsonMessage;
import ru.nsu.litvinenko.lab5.general.SocketConnect;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Chat {
    private SocketConnect socketConnect;
    private ObservableList<String> members;
    private ObservableList<Label> chat;

    public Chat(SocketConnect socketConnect, ObservableList<String> members, ObservableList<Label> chat) throws IOException {
        this.chat = chat;
        this.members = members;
        this.socketConnect = socketConnect;
    }

    public void chat(GsonMessage gsonMessage) throws IOException {
        socketConnect.getPrintWriter().println(socketConnect.getGson().toJson(gsonMessage));
    }

    public void chat() throws IOException {
        new Thread(new Connect(socketConnect, members, chat)).start();
    }
}
