package com.mapstob.group35;

import com.mapstob.group35.core.ParentProfile;
import com.mapstob.group35.core.PlayerProfile;
import com.mapstob.group35.core.loggedUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class mainController {
    @FXML
    private Button tutorial;
    @FXML
    private Button backButton;

    private loggedUser account = new loggedUser();
    private PlayerProfile currentUser = account.getCurrUser();
    /*
        Moves
     */
    @FXML
    protected void goToTutorial() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("tutorialScreen.fxml"));
        tutorial.getScene().setRoot(load.load());
    }


    public void testFunction() { // QUICK TEST FUNCTION TO MAKE SURE SAVING USER DATA WORKS
        if (currentUser != null){
            System.out.println(currentUser.getUserName());
        }
    }

    @FXML
    protected void backToMainMenu() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("baseMenu.fxml"));
        backButton.getScene().setRoot(load.load());
    }
}
