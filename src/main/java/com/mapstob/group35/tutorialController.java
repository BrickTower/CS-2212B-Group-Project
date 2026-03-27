package com.mapstob.group35;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class tutorialController extends baseController {
    @FXML
    private Button backButton;

    @FXML
    protected void backToMainMenu() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        backButton.getScene().setRoot(load.load());
    }
}
