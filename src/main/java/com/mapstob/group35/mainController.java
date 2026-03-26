package com.mapstob.group35;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class mainController {
    @FXML
    private Button tutorial;
    @FXML
    private Button backButton;
    /*
        Moves
     */
    @FXML
    protected void goToTutorial() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("tutorialScreen.fxml"));
        tutorial.getScene().setRoot(load.load());
    }

    @FXML
    protected void backToMainMenu() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("baseMenu.fxml"));
        backButton.getScene().setRoot(load.load());
    }
}
