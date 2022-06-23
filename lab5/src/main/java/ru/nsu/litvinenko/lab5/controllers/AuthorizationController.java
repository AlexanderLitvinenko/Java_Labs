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

public class AuthorizationController implements Initializable {
    @FXML
    private Text error;
    @FXML
    private TextField nameField;
    @FXML
    private Button connectButton;
    @FXML
    private Label label;


    private static Parent loadFXML(String fxml, Socket socket, Scanner scanner, PrintWriter writer, String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientWindow.class.getResource(fxml + ".fxml"));
        fxmlLoader.setController(new ChatController(socket, scanner, writer, name));
        return fxmlLoader.load();
    }

    public static void setRoot(String fxml, Scene scene, Socket socket, Scanner scanner, PrintWriter writer, String name) throws IOException {
        scene.setRoot(loadFXML(fxml, socket, scanner, writer, name));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectButton.setOnAction(actionEvent -> {
            Socket socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 8888), 2000);
                Scanner scanner = new Scanner(socket.getInputStream());
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                writer.println(nameField.getText().trim());
                String answer = scanner.nextLine();
                if (answer.equals(Constants.NAME_IS_TAKEN)) {
                    System.out.println(Constants.NAME_IS_TAKEN);
                    error.setText(Constants.NAME_IS_TAKEN);
                } else if (answer.equals(Constants.EMPTY_NAME)) {
                    System.out.println(Constants.EMPTY_NAME);
                    error.setText(Constants.EMPTY_NAME);
                } else {
                    setRoot(Constants.CHAT, connectButton.getScene(), socket, scanner, writer, nameField.getText());
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