package ru.nsu.litvinenko.lab5.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ru.nsu.litvinenko.lab5.client.ClientWindow;
import ru.nsu.litvinenko.lab5.constants.Constants;
import ru.nsu.litvinenko.lab5.general.LoginGsonMessage;
import ru.nsu.litvinenko.lab5.general.SocketConnect;

public class AuthorizationController implements Initializable {
    @FXML
    private TextField serverIpAddress;
    @FXML
    private TextField serverPort;
    @FXML
    private Text error;
    @FXML
    private TextField nameField;
    @FXML
    private Button connectButton;
    @FXML
    private Label label;


    private static Parent loadFXML(String fxml, SocketConnect socketConnect, String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientWindow.class.getResource(fxml + ".fxml"));
        fxmlLoader.setController(new ChatController(socketConnect, name));
        return fxmlLoader.load();
    }

    public static void setRoot(String fxml, Scene scene, SocketConnect socketConnect, String name) throws IOException {
        scene.setRoot(loadFXML(fxml, socketConnect, name));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectButton.setOnAction(actionEvent -> {
            Socket socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(serverIpAddress.getText(), Integer.parseInt(serverPort.getText())), 2000);
                SocketConnect socketConnect = new SocketConnect(socket);
                LoginGsonMessage loginGsonMessageOut = new LoginGsonMessage();

                loginGsonMessageOut.setName(nameField.getText().trim());
                socketConnect.getPrintWriter().println(socketConnect.getGson().toJson(loginGsonMessageOut));


                String answer = socketConnect.getScanner().nextLine();
                LoginGsonMessage loginGsonMessageIn = socketConnect.getGson().fromJson(answer, LoginGsonMessage.class);

                if (loginGsonMessageIn.getErrorOrAccept().equals(Constants.NAME_IS_TAKEN)) {
                    System.out.println(Constants.NAME_IS_TAKEN);
                    error.setText(Constants.NAME_IS_TAKEN);
                } else if (loginGsonMessageIn.getErrorOrAccept().equals(Constants.EMPTY_NAME)) {
                    System.out.println(Constants.EMPTY_NAME);
                    error.setText(Constants.EMPTY_NAME);
                } else if (loginGsonMessageIn.getErrorOrAccept().equals(Constants.SUCCESSFUL_REGISTRATION)) {
                    setRoot(Constants.CHAT, connectButton.getScene(), socketConnect, nameField.getText());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        connectButton.setOnMouseMoved(mouseEvent -> {
            connectButton.setMaxSize(140, 60);
            connectButton.setTextFill(Color.rgb(0, 0, 153));
        });
        connectButton.setOnMouseExited(mouseEvent -> {
            connectButton.setMaxSize(150, 75);
            connectButton.setTextFill(Color.BLACK);
        });

        label.setOnMouseMoved(mouseEvent -> {
            label.setTextFill(Color.rgb(0, 0, 153));
        });
        label.setOnMouseExited(mouseEvent -> {
            label.setTextFill(Color.rgb(0, 0, 0));
        });

    }
}