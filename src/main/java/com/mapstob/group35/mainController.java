package com.mapstob.group35;

import com.mapstob.group35.core.ParentProfile;
import com.mapstob.group35.core.PlayerProfile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class mainController extends baseController{
    @FXML
    private Button tutorial;
    @FXML
    private Button backButton;

    private PlayerProfile currentUser;
    /*
        Moves
     */
    @FXML
    protected void goToTutorial() throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("tutorialScreen.fxml"));
        tutorial.getScene().setRoot(load.load());
    }
    public void setUser(PlayerProfile user) {
        if (user instanceof ParentProfile) { // IF MEMBER OF PARENTPROFILE (SUBCLASS) DECLARE IT AS SUCH
            this.currentUser = (ParentProfile) user;
        } else {
            this.currentUser = user;
        }
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
