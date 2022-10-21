package com.ilyap.addressing.controllers;

import com.ilyap.addressing.AddressingUtils;
import com.ilyap.addressing.IPv4;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class InputController {

    @FXML
    private TextField broadcastAddressField;

    @FXML
    private TextField currentAddressField;

    @FXML
    private TextField hostsField;

    @FXML
    private TextField maskField;

    @FXML
    private TextField networkAddressField;

    @FXML
    private Text remainingTime;

    @FXML
    private Button resultsButton;

    @FXML
    void initialize() {
        IPv4 ipv4 = AddressingUtils.getIPv4();
        currentAddressField.setText(ipv4.getIPv4());

        maskField.setOnKeyPressed(keyEvent -> requestFocus(keyEvent, hostsField));
        hostsField.setOnKeyPressed(keyEvent -> requestFocus(keyEvent, networkAddressField));
        networkAddressField.setOnKeyPressed(keyEvent -> requestFocus(keyEvent, broadcastAddressField));
        broadcastAddressField.setOnKeyPressed(keyEvent -> requestFocus(keyEvent, maskField));

        resultsButton.setOnAction(actionEvent -> openNextScene());
        broadcastAddressField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                openNextScene();
            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int countdownStarter = AddressingUtils.getTimerInterval();

            public void run() {
                remainingTime.setText(String.format("%02d:%02d", countdownStarter / 60, countdownStarter % 60));
                countdownStarter--;

                if (countdownStarter < 0) {
                    timer.cancel();
                    openNextScene();
                }
            }
        }, 0, 1000);

    }

    public void openNextScene() {
        AddressingUtils.setFields(maskField.getText(), hostsField.getText(),
                networkAddressField.getText(), broadcastAddressField.getText());
        Platform.runLater(() -> {
            try {
                AddressingUtils.openNextScene(resultsButton, "fxml/results.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void requestFocus(KeyEvent keyEvent, TextField field) {
        if (keyEvent.getCode() == KeyCode.TAB) {
            field.requestFocus();
        }
    }
}
