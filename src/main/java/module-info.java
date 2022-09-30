module com.ilyap.Addressing {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ilyap.Addressing to javafx.fxml;
    exports com.ilyap.Addressing;
    exports com.ilyap.Addressing.controllers;
    opens com.ilyap.Addressing.controllers to javafx.fxml;
}