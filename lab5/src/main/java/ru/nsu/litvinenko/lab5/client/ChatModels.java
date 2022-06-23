package ru.nsu.litvinenko.lab5.client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import ru.nsu.litvinenko.lab5.constants.Constants;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ChatModels {


    public static void pushMessage(KeyEvent keyEvent, TextArea message, String name, SimpleDateFormat formatForDateNow, Chat finalChat, final String END_ID) {
        KeyCodeCombination keyComb = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHIFT_DOWN);
        if (keyComb.match(keyEvent)) {
            int position = message.getCaretPosition();
            message.setText(message.getText().substring(Constants.START, position) + Constants.NEXT_LINE + message.getText().substring(position));
            message.positionCaret(position + Constants.OFFSET);
        } else if (KeyCode.ENTER == keyEvent.getCode()) {
            int position = message.getCaretPosition();
            Date date = new Date();
            message.setText(message.getText().substring(Constants.START, position - Constants.OFFSET) + message.getText().substring(position));

            String messageText = message.getText().trim() + Constants.NEXT_LINE + END_ID;
            message.setText(Constants.EMPTY_STR);
            try {
                finalChat.chat(Constants.NAME + name + formatForDateNow.format(date) + Constants.NEXT_LINE + messageText);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void startTimeLineRequests(Chat finalChat, final String NAME_ID) {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(Constants.TIME_TO_GETTER_RESOURCES), //1000 мс * 60 сек = 1 мин
                ae -> {
                    try {
                        finalChat.chat(NAME_ID);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ));
        timeline.setCycleCount(Constants.TIME_ENDLESSLY);
        timeline.play();
    }

    public static Chat loadChatResources(Socket socket, ObservableList<String> members, ObservableList<Label> chat, Scanner scanner, PrintWriter writer) {
        Chat newChat = null;
        try {
            newChat = new Chat(socket, members, chat, scanner, writer);
            newChat.chat();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newChat;
    }
}
