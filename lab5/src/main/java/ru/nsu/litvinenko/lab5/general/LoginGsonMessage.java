package ru.nsu.litvinenko.lab5.general;

public class LoginGsonMessage {
    private String name;
    private String errorOrAccept;

    public String getErrorOrAccept() {
        return errorOrAccept;
    }

    public void setErrorOrAccept(String errorOrAccept) {
        this.errorOrAccept = errorOrAccept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
