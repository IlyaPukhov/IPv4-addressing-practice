package com.ilyap.IPv4;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class IPv4PracticeRunner extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        AddressingUtils.setWindowScene(primaryStage, "fxml/home.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}