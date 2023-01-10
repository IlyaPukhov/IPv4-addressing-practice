package com.ilyap.addressing.interfaces;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public interface FocusSwitchable {
    void switchFocus(KeyEvent keyEvent, TextField field);
}
