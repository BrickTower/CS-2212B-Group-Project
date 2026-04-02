package com.mapstob.group35;

import com.mapstob.group35.core.ParentProfile;
import com.mapstob.group35.core.PlayerProfile;
import com.mapstob.group35.core.loggedUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class mainController {
    @FXML
    private Button newGame;
    @FXML
    private Button tutorial;
    @FXML
    private Button backButton;
    @FXML
    private Button statsButton;
    @FXML
    private Button parentalButton;

    // THIS LINE CREATES A CONNECTION TO THE LOGGED USER CLASS
    private loggedUser account = new loggedUser();
    // THIS LINE PULLS THE CURRENTLY SAVED USER (WHICH SHOULD BE ESTABLISHED IN LOGIN) AND SAVES IT TO A VARIABLE
    private PlayerProfile currentUser = account.getCurrUser();

    /*
        Moves
     */
    @FXML
    protected void goToTutorial() {
        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource("tutorialScreen.fxml"));
            tutorial.getScene().setRoot(load.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void testFunction() { // QUICK TEST FUNCTION TO MAKE SURE SAVING USER DATA WORKS
        if (currentUser != null) {
            currentUser.addCoins(15);
        }
    }

    /**
     * GUI Transition to Parental Controls screen. This function will only work if the currentUser is a {@link ParentProfile}.
     * Otherwise, throw a unique error message over text for a couple seconds.
     *
     * @author Ethan Gregory
     */
    @FXML
    protected void goToParentalControls() {
        // GUARD AGAINST PLAYER PROFILE ACCESS
        if (!(currentUser instanceof ParentProfile)) {
            parentalButton.setText("Not a Parent!");
        } else {
            try {
                FXMLLoader load = new FXMLLoader(getClass().getResource("parentalScreen.fxml"));
                Parent root = load.load();
                parentalController setup = load.getController();
                setup.getParentsPlayers(); // POPULATE LIST WITH MAIN OPTIONS
                parentalButton.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    protected void backToMainMenu() {
        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource("baseMenu.fxml"));
            backButton.getScene().setRoot(load.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

