module ru.nsu.litvinenko.lab5 {
    requires javafx.controls;
    requires javafx.fxml;

//    opens ru.nsu.litvinenko.lab5 to javafx.fxml;
    //exports ru.nsu.litvinenko.lab5;
    exports ru.nsu.litvinenko.lab5.client;
    opens ru.nsu.litvinenko.lab5.client to javafx.fxml;
    exports ru.nsu.litvinenko.lab5.constants;
    opens ru.nsu.litvinenko.lab5.constants to javafx.fxml;
    exports ru.nsu.litvinenko.lab5.controllers;
    opens ru.nsu.litvinenko.lab5.controllers to javafx.fxml;

}