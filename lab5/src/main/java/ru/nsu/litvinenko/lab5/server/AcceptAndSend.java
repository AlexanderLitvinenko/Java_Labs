package ru.nsu.litvinenko.lab5.server;

import ru.nsu.litvinenko.lab5.constants.Constants;

import java.io.IOException;
import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AcceptAndSend implements Runnable {
    private ConcurrentHashMap<String, SocketConnect> mapOfNamesAndSockets;
    private SocketConnect newClient;
    private AtomicReference<String>[] messageHistory;
    private AtomicInteger atomicInteger;

    public AcceptAndSend(ConcurrentHashMap<String, SocketConnect> mapOfNamesAndSockets, SocketConnect newClient, AtomicReference<String>[] messageHistory, AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
        this.mapOfNamesAndSockets = mapOfNamesAndSockets;
        this.messageHistory = messageHistory;
        this.newClient = newClient;
    }

    private void notifyChat(String name, String strDisconnect) {
        messageHistory[atomicInteger.getAndAdd(Constants.OFFSET) % messageHistory.length].set(strDisconnect);
        atomicInteger.set(atomicInteger.get() % messageHistory.length);
        for (String client : mapOfNamesAndSockets.keySet()) {
            if (!name.equals(client)) {
                mapOfNamesAndSockets.get(client).getPrintWriter().println(strDisconnect);
            }
        }
    }


    @Override
    public void run() {
        boolean flag = false;
        String name = Constants.EMPTY_STR;
        try {
            newClient.getSocket().setSoTimeout(2000);
            while (newClient.getScanner().hasNextLine()) {
                name = newClient.getScanner().nextLine().trim();
                if (mapOfNamesAndSockets.containsKey(name)) {
                    newClient.getPrintWriter().println(Constants.NAME_IS_TAKEN);
                } else if (name.equals(Constants.EMPTY_STR)) {
                    newClient.getPrintWriter().println(Constants.EMPTY_NAME);
                } else {
                    newClient.getPrintWriter().println(Constants.SUCCESSFUL_REGISTRATION);
                    mapOfNamesAndSockets.put(name, newClient);
                    flag = true;
                    break;
                }
            }

            System.out.println("Connected:" + name);
            if (!flag) {
                return;
            }
            for (int i = atomicInteger.get(); i < messageHistory.length + atomicInteger.get(); i++) {
                if (messageHistory[i % messageHistory.length].get() != null)
                    newClient.getPrintWriter().println(messageHistory[i % messageHistory.length].get());
            }
            String greeting = "User named " + name + " was connected " + Constants.NEXT_LINE + Constants.END_OF_MESSAGE;
            newClient.getPrintWriter().println(greeting);
            notifyChat(name, greeting);
            newClient.getSocket().setSoTimeout(10000);
            StringBuilder message = new StringBuilder();

            while (newClient.getScanner().hasNextLine()) {
                String str = newClient.getScanner().nextLine();
                message.append(str).append(Constants.NEXT_LINE);
                if (str.equals(Constants.END_OF_MESSAGE)) {
                    for (Map.Entry<String, SocketConnect> key : mapOfNamesAndSockets.entrySet()) {
                        key.getValue().getPrintWriter().println(message);
                    }
                    messageHistory[atomicInteger.getAndAdd(Constants.OFFSET) % messageHistory.length].set(message.toString());
                    atomicInteger.set(atomicInteger.get() % messageHistory.length);
                    message.setLength(0);
                } else if (str.equals(Constants.MEMBERS)) {
                    StringBuilder string = new StringBuilder();
                    for (Map.Entry<String, SocketConnect> key : mapOfNamesAndSockets.entrySet()) {
                        string.append(key.getKey()).append(Constants.NEXT_LINE);
                    }
                    string.append(Constants.MEMBERS);
                    newClient.getPrintWriter().println(string);
                    message.setLength(0);
                }
            }

            String strDisconnect = "User named " + name + ". Goodbye!" + Constants.NEXT_LINE + Constants.END_OF_MESSAGE;
            mapOfNamesAndSockets.remove(name);
            notifyChat(name, strDisconnect);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
