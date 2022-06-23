package ru.nsu.litvinenko.lab5.client;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import ru.nsu.litvinenko.lab5.constants.Constants;

import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.Set;

public class Connect implements Runnable {
    private Socket socket;
    private ObservableList<String> members;
    private ObservableList<Label> chat;
    private Scanner scanner;


    public Connect(Socket socket, ObservableList<String> members, ObservableList<Label> chat, Scanner scanner) {
        this.members = members;
        this.chat = chat;
        this.socket = socket;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        StringBuilder message = new StringBuilder();
        try {
            socket.setSoTimeout(Constants.TIMEOUT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (true) {
            assert scanner != null;
            if (!scanner.hasNextLine()) break;
            String line = scanner.nextLine();

            if (line.equals(Constants.MEMBERS)) {
                Set<String> messageSet = Set.of(message.toString().trim().split(Constants.NEXT_LINE));
                for (String client : messageSet)
                    if (!members.contains(client))
                        Platform.runLater(() -> members.add(client));
                Platform.runLater(() -> members.removeIf(client -> !messageSet.contains(client)));
                message.setLength(Constants.EMPTY_STRING_LENGTH);
            } else if (line.equals(Constants.END_OF_MESSAGE)) {
                String text = message.toString().trim();
                Label label = new Label(text);
                Platform.runLater(() -> chat.add(label));
                message.setLength(Constants.EMPTY_STRING_LENGTH);
            } else {
                message.append(line).append(Constants.NEXT_LINE);
            }
        }
    }
}
