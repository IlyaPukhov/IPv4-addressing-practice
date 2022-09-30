package com.ilyap.Addressing;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class IPv4PracticeRunner extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        AddressingUtils.setWindowScene(stage, "fxml/home.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}