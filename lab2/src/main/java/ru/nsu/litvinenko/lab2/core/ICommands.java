package ru.nsu.litvinenko.lab2.core;


public interface ICommands {
    void command(Context context);

    default int getCountOfParams() {
        return 0;
    }

}
