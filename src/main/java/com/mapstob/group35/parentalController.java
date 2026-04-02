package com.mapstob.group35;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mapstob.group35.core.ParentProfile;
import com.mapstob.group35.core.PlayerProfile;
import com.mapstob.group35.core.loggedUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class parentalController {
    private loggedUser account = new loggedUser();
    private ParentProfile currentUser = (ParentProfile) (account.getCurrUser());

    @FXML
    private ChoiceBox<PlayerProfile> playersManaged;
    @FXML
    private Label leftError;
    @FXML
    private Label rightError;
    @FXML
    private TextField newPlayerName;
    @FXML
    private PasswordField newPlayerPass;
    @FXML
    private Button backButton;

    @FXML
    protected void returnToMainMenu() {
        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
            backButton.getScene().setRoot(load.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Grabs all players from the Current Parent and logs them into the ChoiceBox
     * Only called when adding a new User and entering this screen (latter is why it's public)
     */
    public void getParentsPlayers() {


        // CODE TO IMPLEMENT CHANGES TO COMBO BOX BASED OFF THIS (FIRST EXAMPLE): https://www.geeksforgeeks.org/java/javafx-combobox-with-examples/
        SortedList<PlayerProfile> comboBox = new SortedList<PlayerProfile>(FXCollections.observableArrayList());

    }

    /**
     * Adds a new Player to the game. They are saved to players.json, and then added to the Parent's list of managed Players.
     *
     * @author Ethan Gregory
     */
    @FXML
    private void addPlayer() {
        try {

            String userName = newPlayerName.getText();
            String passWord = newPlayerPass.getText();

            if (userName.isBlank() || passWord.isBlank()) {
                throw new IllegalArgumentException("Fields are not fully filled");
            }

            PlayerProfile newPlayer = new PlayerProfile(userName, passWord);
            currentUser.addPlayer(newPlayer);
            List<ParentProfile> parents = DataManager.loadList("parents.json", new TypeReference<List<ParentProfile>>() {
            });

            newPlayerName.clear();
            newPlayerPass.clear();

        } catch (IllegalArgumentException e) {
            rightError.setVisible(true);
            rightError.setText(e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
