package com.mapstob.group35;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Controller for Tutorial Scene, handling the one UI interaction on this menu
 * @version 1
 */
public class tutorialController {
    @FXML
    private Button backButton;

    @FXML
    protected void EXIT_BUTTON_CLICKED()  {
        try {
            SceneManager.loadMainMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
