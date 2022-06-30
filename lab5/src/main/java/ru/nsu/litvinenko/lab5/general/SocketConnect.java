package ru.nsu.litvinenko.lab5.general;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketConnect {
    private final Socket socket;
    private final Gson gson;
    private final PrintWriter printWriter;
    private final Scanner scanner;

    public SocketConnect(Socket socket) throws IOException {
        this.socket = socket;
        scanner = new Scanner(socket.getInputStream());
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        gson = new Gson();
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public Socket getSocket() {
        return socket;
    }

    public Gson getGson() {
        return gson;
    }
}
