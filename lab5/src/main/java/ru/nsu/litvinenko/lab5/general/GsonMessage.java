package ru.nsu.litvinenko.lab5.general;

import java.util.Set;

public class GsonMessage {
    private String name;
    private String dateAndTime;
    private String message;
    private Set<String> membersOfChat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<String> getMembersOfChat() {
        return membersOfChat;
    }

    public void setMembersOfChat(Set<String> membersOfChat) {
        this.membersOfChat = membersOfChat;
    }
}
