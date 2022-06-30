package ru.nsu.litvinenko.lab5.controllers;

import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import ru.nsu.litvinenko.lab5.client.Chat;
import ru.nsu.litvinenko.lab5.client.ChatModels;
import ru.nsu.litvinenko.lab5.constants.Constants;
import ru.nsu.litvinenko.lab5.general.SocketConnect;

public class ChatController implements Initializable {
    @FXML
    private ListView listViewMembers;
    @FXML
    private ListView chat;
    @FXML
    private TextArea message;

    private SocketConnect socketConnect;
    private String name;

    public ChatController(SocketConnect socketConnect, String name) {
        this.socketConnect = socketConnect;
        this.name = name;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> observableMembers = FXCollections.observableArrayList(Constants.EMPTY_STR);
        ObservableList<Label> observableChat = FXCollections.observableArrayList();
        listViewMembers.setItems(observableMembers);
        this.chat.setItems(observableChat);

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        MultipleSelectionModel<Label> chatSelectionModel = chat.getSelectionModel();
        MultipleSelectionModel<String> participantSelectionModel = listViewMembers.getSelectionModel();


        Chat finalChat = ChatModels.loadChatResources(socketConnect, observableMembers, observableChat);
        SimpleDateFormat formatForDateNow = new SimpleDateFormat(Constants.PATTERN);
        message.setOnKeyPressed(keyEvent -> ChatModels.pushMessage(socketConnect, keyEvent, message, name, formatForDateNow, finalChat));
        ChatModels.startTimeLineRequests(finalChat);

        chatSelectionModel.selectedItemProperty().addListener((changed, oldValue, newValue) -> {
            content.putString(newValue.getText());
            clipboard.setContent(content);
        });
        participantSelectionModel.selectedItemProperty().addListener((changed, oldValue, newValue) -> {
            content.putString(newValue);
            clipboard.setContent(content);
        });
    }
}