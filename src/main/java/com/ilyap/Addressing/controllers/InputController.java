package com.ilyap.Addressing.controllers;

import com.ilyap.Addressing.AddressingUtils;
import com.ilyap.Addressing.IPv4;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

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

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            int countdownStarter = AddressingUtils.getTimerInterval();

            public void run() {
                remainingTime.setText(String.format("%02d:%02d", countdownStarter / 60, countdownStarter % 60));
                countdownStarter--;

                if (countdownStarter < 0) {
                    openNextScene();
                    scheduler.shutdown();
                }
            }
        }, 0, 1, SECONDS);
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
