package com.mapstob.group35;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;


import java.io.IOException;

/**
 * Controller for BotSpam's Main Menu. Controls Main Menu, allowing to quit game and change to the different screens. (Login, etc.)
 * <br>
 * @author Ethan Gregory
 *
 */
public class mainController {
    @FXML
    private Button logIn;
    @FXML
    private Button leaderboard;
    @FXML
    private Button tutorial;

    /*
        Closes the program when the Main Menu's Quit Button is Pressed
        @author Ethan Gregory
     */
    @FXML
    protected void onQuit() {
        Platform.exit(); // CLOSE GAME
    }

    /*
        Moves
     */
    @FXML
    protected void goToTutorial() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("tutorialScreen.fxml"));
        tutorial.getScene().setRoot(load.load());
    }
}
