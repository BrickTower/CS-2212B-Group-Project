package com.mapstob.group35;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class tutorialController extends mainController {
    @FXML
    private Button backButton;

    @FXML
    protected void backToMainMenu() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("baseMenu.fxml"));
        backButton.getScene().setRoot(load.load());
    }
}
