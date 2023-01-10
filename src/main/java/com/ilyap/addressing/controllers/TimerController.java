package com.ilyap.addressing.controllers;

import com.ilyap.addressing.AddressingUtils;
import com.ilyap.addressing.interfaces.FocusSwitchable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import static java.lang.Integer.parseInt;

public class TimerController implements FocusSwitchable {

    @FXML
    private AnchorPane anchor;

    @FXML
    private TextField minutesField;

    @FXML
    private Button saveTimeButton;

    @FXML
    private TextField secondsField;

    @FXML
    void initialize() {
        int seconds = AddressingUtils.getTimerInterval();
        minutesField.setText(String.format("%02d", seconds / 60));
        secondsField.setText(String.format("%02d", seconds % 60));

        saveTimeButton.setOnAction(event -> saveTime());
        anchor.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                saveTime();
            }
        });

        minutesField.setOnKeyPressed(keyEvent -> switchFocus(keyEvent, secondsField));
        secondsField.setOnKeyPressed(keyEvent -> switchFocus(keyEvent, minutesField));

        addTextLimiter(minutesField, 2);
        addTextLimiter(secondsField, 2);
    }

    public void saveTime() {
        AddressingUtils.setTimerInterval(
                parseInt(minutesField.getText()) * 60 + parseInt(secondsField.getText()));
        saveTimeButton.getScene().getWindow().hide();
    }

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener((ov) -> {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(1, maxLength + 1);
                tf.setText(s);
            }
        });
    }

    @Override
    public void switchFocus(KeyEvent keyEvent, TextField field) {
        if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.LEFT
                || keyEvent.getCode() == KeyCode.TAB) {
            field.requestFocus();
        }
    }
}
