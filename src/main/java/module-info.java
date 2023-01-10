module com.ilyap.Addressing {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ilyap.addressing to javafx.fxml;
    exports com.ilyap.addressing;
    exports com.ilyap.addressing.controllers;
    opens com.ilyap.addressing.controllers to javafx.fxml;
    exports com.ilyap.addressing.interfaces;
    opens com.ilyap.addressing.interfaces to javafx.fxml;
    exports com.ilyap.addressing.exceptions;
    opens com.ilyap.addressing.exceptions to javafx.fxml;
}