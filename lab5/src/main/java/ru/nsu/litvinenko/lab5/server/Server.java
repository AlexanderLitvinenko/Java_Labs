package ru.nsu.litvinenko.lab5.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Server {

    private ConcurrentHashMap<String, SocketConnect> mapOfNamesAndSockets;
    private AtomicReference<String>[] messageHistory;

    private Server() {
        mapOfNamesAndSockets = new ConcurrentHashMap<>();
        messageHistory = new AtomicReference[10];

        System.out.println("Server started");
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            for (int i = 0; i < messageHistory.length; i++)
                messageHistory[i] = new AtomicReference<>();
            AtomicInteger number = new AtomicInteger(0);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new AcceptAndSend(mapOfNamesAndSockets, new SocketConnect(socket), messageHistory, number)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
