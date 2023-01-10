package com.ilyap.addressing.controllers;

import com.ilyap.addressing.exceptions.NextSceneException;
import com.ilyap.addressing.interfaces.FocusSwitchable;
import com.ilyap.addressing.interfaces.SceneTransitionable;
import com.ilyap.addressing.AddressingUtils;
import com.ilyap.addressing.IPv4;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class InputController implements SceneTransitionable, FocusSwitchable {

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
    private ImageView pauseButton;

    @FXML
    private ImageView continueButton;

    @FXML
    private Text remainingTime;

    @FXML
    private Button resultsButton;

    private int countdownTime;
    private int timeLeft;
    private Timer lastTimer;


    @FXML
    void initialize() {
        int initialInterval = AddressingUtils.getTimerInterval();
        remainingTime.setText(String.format("%02d:%02d", initialInterval / 60, initialInterval % 60));

        IPv4 ipv4 = AddressingUtils.getIPv4();
        currentAddressField.setText(ipv4.getIPv4());

        lastTimer = startTimer(initialInterval);

        maskField.setOnKeyPressed(keyEvent -> switchFocus(keyEvent, hostsField));
        hostsField.setOnKeyPressed(keyEvent -> switchFocus(keyEvent, networkAddressField));
        networkAddressField.setOnKeyPressed(keyEvent -> switchFocus(keyEvent, broadcastAddressField));
        broadcastAddressField.setOnKeyPressed(keyEvent -> switchFocus(keyEvent, maskField));

        resultsButton.setOnAction(actionEvent -> openNextScene());
        broadcastAddressField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                openNextScene();
            }
        });

        pauseButton.setOnMouseClicked(mouseEvent -> timeLeft = stopTimer(lastTimer));

        continueButton.setOnMouseClicked(mouseEvent -> lastTimer = startTimer(timeLeft));
    }

    public Timer startTimer(int interval) {
        pauseButton.setVisible(true);
        continueButton.setVisible(false);

        Timer timer = new Timer();
        countdownTime = interval;

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                remainingTime.setText(String.format("%02d:%02d", countdownTime / 60, countdownTime % 60));
                countdownTime--;

                if (countdownTime < 0) {
                    timer.cancel();
                    openNextScene();
                }
            }
        }, 500, 1000);

        return timer;
    }

    public int stopTimer(Timer timer) {
        pauseButton.setVisible(false);
        continueButton.setVisible(true);
        timer.cancel();

        return countdownTime;
    }

    @Override
    public void openNextScene() {
        lastTimer.cancel();
        AddressingUtils.setFields(maskField.getText(), hostsField.getText(),
                networkAddressField.getText(), broadcastAddressField.getText());
        Platform.runLater(() -> {
            try {
                AddressingUtils.openNextScene(resultsButton, "fxml/results.fxml");
            } catch (IOException e) {
                throw new NextSceneException(e.getMessage());
            }
        });
    }

    @Override
    public void switchFocus(KeyEvent keyEvent, TextField field) {
        if (keyEvent.getCode() == KeyCode.TAB) {
            field.requestFocus();
        }
    }
}
