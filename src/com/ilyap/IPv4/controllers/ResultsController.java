package com.ilyap.IPv4.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.ilyap.IPv4.AddressingUtils;
import com.ilyap.IPv4.IPv4;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ResultsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchor;

    @FXML
    private TextField broadcastAddressField;

    @FXML
    private ImageView broadcastCorrect;

    @FXML
    private Text correctAnswer;

    @FXML
    private TextField currentAddressField;

    @FXML
    private ImageView explosion;

    @FXML
    private ImageView firework;

    @FXML
    private Button homeButton;

    @FXML
    private TextField hostsField;

    @FXML
    private ImageView hostsCorrect;

    @FXML
    private Text incorrectAnswer;

    @FXML
    private TextField maskField;

    @FXML
    private ImageView maskCorrect;

    @FXML
    private TextField networkAddressField;

    @FXML
    private ImageView networkCorrect;

    @FXML
    void initialize() {
        IPv4 ipv4 = AddressingUtils.getIPv4();
        currentAddressField.setText(ipv4.getIPv4());

        maskField.setText(AddressingUtils.getMask());
        maskCorrect.setVisible(maskField.getText().equals(ipv4.getMask()));

        hostsField.setText(AddressingUtils.getHosts());
        hostsCorrect.setVisible(hostsField.getText().equals(ipv4.getHosts()));

        networkAddressField.setText(AddressingUtils.getNetwork());
        networkCorrect.setVisible(networkAddressField.getText().equals(ipv4.getNetworkAddress()));

        broadcastAddressField.setText(AddressingUtils.getBroadcast());
        broadcastCorrect.setVisible(broadcastAddressField.getText().equals(ipv4.getBroadcastAddress()));

        answersCheck();

        homeButton.setOnAction(actionEvent -> openNextScene());
        anchor.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                openNextScene();
            }
        });
    }

    private void answersCheck() {
        if (!(maskCorrect.isVisible() && hostsCorrect.isVisible()
                && networkCorrect.isVisible() && broadcastCorrect.isVisible())) {
            correctAnswer.setVisible(false);
            firework.setVisible(false);

            incorrectAnswer.setVisible(true);
            explosion.setVisible(true);
        }
    }


    public void openNextScene() {
        try {
            AddressingUtils.openNextScene(homeButton, "fxml/home.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}