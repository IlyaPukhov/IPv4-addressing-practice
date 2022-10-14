package com.ilyap.addressing;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public final class AddressingUtils {
    private static IPv4 ipv4;

    private static String mask;

    private static String hosts;
    private static String network;
    private static String broadcast;

    private static int timerInterval = 120;

    private AddressingUtils() {
    }

    public static void openNextScene(Button button, String path) throws IOException {
        button.getScene().getWindow().hide();
        AddressingUtils.setWindowScene(new Stage(), path);
    }

    public static void setWindowScene(Stage stage, String path) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(AddressingUtils.class.getResource(path)));
        stage.getIcons().add(
                new Image(Objects.requireNonNull(AddressingUtils.class.getResourceAsStream("assets/icon.png"))));
        stage.setTitle("IPv4 адресация");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    public static IPv4 getIPv4() {
        return ipv4;
    }

    public static void setIPv4(IPv4 ipv4) {
        AddressingUtils.ipv4 = ipv4;
    }

    public static void setFields(String mask, String hosts, String network, String broadcast) {
        AddressingUtils.mask = mask;
        AddressingUtils.hosts = hosts;
        AddressingUtils.network = network;
        AddressingUtils.broadcast = broadcast;
    }

    public static String getMask() {
        return mask;
    }

    public static String getHosts() {
        return hosts;
    }

    public static String getNetwork() {
        return network;
    }

    public static String getBroadcast() {
        return broadcast;
    }

    public static int getTimerInterval() {
        return timerInterval;
    }

    public static void setTimerInterval(int timerInterval) {
        AddressingUtils.timerInterval = timerInterval;
    }
}
