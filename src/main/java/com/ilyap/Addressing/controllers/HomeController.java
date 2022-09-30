package com.ilyap.Addressing.controllers;

import com.ilyap.Addressing.AddressingUtils;
import com.ilyap.Addressing.IPv4;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private TextField IPv4_field;

    @FXML
    private Button startButton;


    @FXML
    private ImageView timerSetButton;

    @FXML
    void initialize() {
        startButton.setOnAction(actionEvent -> openNextScene("fxml/input.fxml"));
        IPv4_field.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                openNextScene("fxml/input.fxml");
            }
        });

        timerSetButton.setOnMouseClicked(mouseEvent -> {
            try {
                AddressingUtils.setWindowScene(new Stage(), "fxml/timerSettings.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void openNextScene(String path) {
        try {
            IPv4 ipv4 = new IPv4(IPv4_field.getText());
            AddressingUtils.setIPv4(ipv4);
            AddressingUtils.openNextScene(startButton, path);
        } catch (RuntimeException | IOException e) {
            IPv4_field.setText(e.getMessage());
        }
    }
}
