package ru.nsu.litvinenko.lab5.server;

import com.google.gson.Gson;
import ru.nsu.litvinenko.lab5.constants.Constants;
import ru.nsu.litvinenko.lab5.general.GsonMessage;
import ru.nsu.litvinenko.lab5.general.LoginGsonMessage;
import ru.nsu.litvinenko.lab5.general.SocketConnect;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AcceptAndSend implements Runnable {
    private ConcurrentHashMap<String, SocketConnect> mapOfNamesAndSockets;
    private SocketConnect newClient;
    private AtomicReference<GsonMessage>[] messageHistory;
    private AtomicInteger atomicInteger;

    public AcceptAndSend(ConcurrentHashMap<String, SocketConnect> mapOfNamesAndSockets, SocketConnect newClient, AtomicReference<GsonMessage>[] messageHistory, AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
        this.mapOfNamesAndSockets = mapOfNamesAndSockets;
        this.messageHistory = messageHistory;
        this.newClient = newClient;
    }

    private void notifyChat(String name, GsonMessage gsonMessage) {
        messageHistory[atomicInteger.getAndAdd(Constants.OFFSET) % messageHistory.length].set(gsonMessage);
        atomicInteger.set(atomicInteger.get() % messageHistory.length);
        for (String client : mapOfNamesAndSockets.keySet()) {
            if (!name.equals(client)) {
                mapOfNamesAndSockets.get(client).getPrintWriter().println(newClient.getGson().toJson(gsonMessage));
            }
        }
    }


    @Override
    public void run() {
        boolean flag = false;
        String name = Constants.EMPTY_STR;
        try {
            LoginGsonMessage loginGsonMessageIn;
            newClient.getSocket().setSoTimeout(Constants.SOCKET_TIMEOUT);
            while (newClient.getScanner().hasNextLine()) {
                loginGsonMessageIn = newClient.getGson().fromJson(newClient.getScanner().nextLine(), LoginGsonMessage.class);
                name = loginGsonMessageIn.getName();
                if (mapOfNamesAndSockets.containsKey(name)) {
                    loginGsonMessageIn.setErrorOrAccept(Constants.NAME_IS_TAKEN);
                    newClient.getPrintWriter().println(newClient.getGson().toJson(loginGsonMessageIn));
                } else if (name.equals(Constants.EMPTY_STR)) {
                    loginGsonMessageIn.setErrorOrAccept(Constants.EMPTY_NAME);
                    newClient.getPrintWriter().println(newClient.getGson().toJson(loginGsonMessageIn));
                } else {
                    loginGsonMessageIn.setErrorOrAccept(Constants.SUCCESSFUL_REGISTRATION);
                    newClient.getPrintWriter().println(newClient.getGson().toJson(loginGsonMessageIn));
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
                    newClient.getPrintWriter().println(newClient.getGson().toJson(messageHistory[i % messageHistory.length].get()));
            }
            String greeting = "User named " + name + " was connected ";
            GsonMessage gsonMessage = new GsonMessage();
            gsonMessage.setMessage(greeting);
            newClient.getPrintWriter().println(newClient.getGson().toJson(gsonMessage));
            notifyChat(name, gsonMessage);
            newClient.getSocket().setSoTimeout(10000);

            while (newClient.getScanner().hasNextLine()) {
                GsonMessage gsonMessageIn = newClient.getGson().fromJson(newClient.getScanner().nextLine(), GsonMessage.class);
                if (gsonMessageIn.getMessage() != null) {
                    for (Map.Entry<String, SocketConnect> key : mapOfNamesAndSockets.entrySet()) {
                        key.getValue().getPrintWriter().println(newClient.getGson().toJson(gsonMessageIn));
                    }
                    messageHistory[atomicInteger.getAndAdd(Constants.OFFSET) % messageHistory.length].set(gsonMessageIn);
                    atomicInteger.set(atomicInteger.get() % messageHistory.length);
                } else {
                    GsonMessage gsonMessageOut = new GsonMessage();
                    Set<String> set = new HashSet<>();
                    for (Map.Entry<String, SocketConnect> key : mapOfNamesAndSockets.entrySet()) {
                        set.add(key.getKey());
                    }
                    gsonMessageOut.setMembersOfChat(set);
                    newClient.getPrintWriter().println(newClient.getGson().toJson(gsonMessageOut));
                }
            }

            String strDisconnect = "User named " + name + ". Goodbye!";
            GsonMessage gsonMessageOut = new GsonMessage();
            gsonMessageOut.setMessage(strDisconnect);
            mapOfNamesAndSockets.remove(name);
            notifyChat(name, gsonMessageOut);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
