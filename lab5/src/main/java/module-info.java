module ru.nsu.litvinenko.lab5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    exports ru.nsu.litvinenko.lab5.client;
    opens ru.nsu.litvinenko.lab5.client to javafx.fxml;
    exports ru.nsu.litvinenko.lab5.constants;
    opens ru.nsu.litvinenko.lab5.constants to javafx.fxml;
    exports ru.nsu.litvinenko.lab5.controllers;
    opens ru.nsu.litvinenko.lab5.controllers to javafx.fxml;
    opens ru.nsu.litvinenko.lab5.general to com.google.gson;
    opens ru.nsu.litvinenko.lab5.server to com.google.gson;

}