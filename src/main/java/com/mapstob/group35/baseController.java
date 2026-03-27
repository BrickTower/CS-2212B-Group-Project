package com.mapstob.group35;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;


import java.io.IOException;

/**
 * Controller for BotSpam's Base Menu. Controls Base Menu, allowing to quit game and change to the different screens. (Login, etc.)
 * <br>
 * @author Ethan Gregory
 *
 */
public class baseController {
    @FXML
    private Button logIn;

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
    protected void goToLogin() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("Login_Page.fxml"));
        logIn.getScene().setRoot(load.load());
    }

}
