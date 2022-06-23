package ru.nsu.litvinenko.lab5.client;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Chat {
    private Socket socket;
    private PrintWriter writer;
    private Scanner scanner;
    private ObservableList<String> members;
    private ObservableList<Label> chat;

    public Chat(Socket socket, ObservableList<String> members, ObservableList<Label> chat, Scanner scanner, PrintWriter writer) throws IOException {
        this.chat = chat;
        this.members = members;
        this.socket = socket;
        this.writer = writer;
        this.scanner = scanner;
    }

    public void chat(String text) throws IOException {
        writer.println(text);
    }
    public void chat() throws IOException {
        new Thread(new Connect(socket, members,chat, scanner)).start();
    }
}
