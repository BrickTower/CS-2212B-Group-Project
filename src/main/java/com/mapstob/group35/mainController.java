package com.mapstob.group35;

import com.mapstob.group35.Gameplay.GameGUIManager;
import com.mapstob.group35.Gameplay.GameManager;
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

    //temporary var
    private boolean runExists = false;


    private void runButtonText(){
        if (runExists){
            assert newGame != null;
            newGame.setText("Continue Run");
        }
        else{
            assert newGame != null;
            newGame.setText("New Run");
        }
    }

    /*
        Moves
     */
    @FXML
    protected void goToTutorial() {
        try {
            SceneManager.loadTutorial();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void StartGame() {
        GameManager gm;

        if (runExists) {
            //TODO load information into gameManager
            gm = new GameManager();
        }
        else{//if first run or if first run after a death
            gm = new GameManager();
        }
        try {
            SceneManager.loadGame(gm);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void goToStats() {
        try {
            SceneManager.loadStats();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                SceneManager.loadParentalControls();
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

