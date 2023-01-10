package com.ilyap.addressing.controllers;

import com.ilyap.addressing.AddressingUtils;
import com.ilyap.addressing.IPv4;
import com.ilyap.addressing.exceptions.IPException;
import com.ilyap.addressing.exceptions.NextSceneException;
import com.ilyap.addressing.interfaces.SceneTransitionable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController implements SceneTransitionable {

    @FXML
    private TextField IPv4_field;

    @FXML
    private Button startButton;


    @FXML
    private ImageView timerSetButton;

    @FXML
    void initialize() {
        startButton.setOnAction(actionEvent -> openNextScene());
        IPv4_field.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                openNextScene();
            }
        });

        timerSetButton.setOnMouseClicked(mouseEvent -> {
            try {
                AddressingUtils.setWindowScene(new Stage(), "fxml/timerSettings.fxml");
            } catch (IOException e) {
                throw new NextSceneException(e.getMessage());
            }
        });
    }

    @Override
    public void openNextScene() {
        try {
            IPv4 ipv4 = new IPv4(IPv4_field.getText());
            AddressingUtils.setIPv4(ipv4);
            AddressingUtils.openNextScene(startButton, "fxml/input.fxml");
        } catch (IPException | IOException e) {
            IPv4_field.setText(e.getMessage());
        }
    }
}
