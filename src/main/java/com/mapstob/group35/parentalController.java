package com.mapstob.group35;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mapstob.group35.core.ParentProfile;
import com.mapstob.group35.core.PlayerProfile;
import com.mapstob.group35.core.Stats;
import com.mapstob.group35.core.loggedUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Controller for Parental Controls, handling all UI interactions, including resetting Passwords amd resetting Scoreboards.
 * @version 1
 */
public class parentalController {
    private loggedUser account = new loggedUser();
    private ParentProfile currentUser = (ParentProfile) (account.getCurrUser());

    @FXML
    private ChoiceBox<PlayerProfile> managedPlayers;
    @FXML
    private Label leftError;
    @FXML
    private Label rightError;
    @FXML
    private TextField newPlayerName;
    @FXML
    private PasswordField resetField;
    @FXML
    private PasswordField newPlayerPass;
    @FXML
    private Button backButton;

    @FXML
    protected void returnToMainMenu() {
        try {
            SceneManager.loadMainMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reset the {@link Stats} of specifically one player from the Parent's dropdown list.
     * @author Ethan Gregory
     */
    @FXML
    protected void resetPlayersStats() {
        if (managedPlayers.getValue() == null) { // MAKE SURE USER IS SELECTED
            leftError.setVisible(true);
            leftError.setText("User not selected.");
        } else {
            PlayerProfile student = managedPlayers.getValue();
            student.parentReset();
            leftError.setVisible(true);
            leftError.setText("Stats reset.");
        }
    }

    /**
     * Reset the {@link Stats} of every player that the Parent is managing.
     * @author Ethan Gregory
     */
    @FXML
    protected void resetAllPlayersStats() {
        for (PlayerProfile player : currentUser.getPlayersManaged()) {
            player.parentReset();
        }
        leftError.setVisible(true);
        leftError.setText("All Players' stats reset.");
    }


    /**
     * Resets the selected Player's password to whatever is inputted in the box. Throws a small Error Message if it can't succeed.
     * @author Ethan Gregory
     */
    @FXML
    protected void resetPlayersPassword() {
        String newPassword = resetField.getText();
        if (managedPlayers.getValue() == null) { // MAKE SURE USER IS SELECTED
            leftError.setVisible(true);
            leftError.setText("User not selected.");
        } else if (newPassword.isBlank()) { // MAKE SURE PASSWORD IS ENTERED
            leftError.setVisible(true);
            leftError.setText("Password not entered");
        } else {
            PlayerProfile student = managedPlayers.getValue();
            student.setPassword(newPassword);
            leftError.setVisible(true);
            leftError.setText("Password changed");
        }
    }

    /**
     * Grabs all players from the Current Parent and logs them into the ChoiceBox
     * Only called when adding a new User and entering this screen (latter is why it's public)
     */
    public void getParentsPlayers() {
        LinkedList<PlayerProfile> allPlayers = currentUser.getPlayersManaged();
        // CODE TO IMPLEMENT CHANGES TO COMBO BOX BASED OFF THIS (FIRST EXAMPLE): https://www.geeksforgeeks.org/java/javafx-combobox-with-examples/
        SortedList<PlayerProfile> comboBox = new SortedList<PlayerProfile>(FXCollections.observableArrayList(allPlayers));
        managedPlayers.setItems(comboBox);
    }

    /**
     * Adds a new Player to the game. They are located within the Parent's playersManaged.
     *
     * @author Ethan Gregory
     */
    @FXML
    private void addPlayer() {
        try {

            String userName = newPlayerName.getText(); // GET TEXT
            String passWord = newPlayerPass.getText();

            if (userName.isBlank() || passWord.isBlank()) { // GUARD
                throw new IllegalArgumentException("Fields are not fully filled");
            }

            // ANOTHER GUARD
            List<ParentProfile> parents = DataManager.loadList("parents.json", new TypeReference<List<ParentProfile>>() {
            }); // GET JSON

            int counter = 0; // FIND INDEX IN FOR LOOP
            for (ParentProfile parent : parents) {
                int counter2 = 0;
                List<PlayerProfile> players = parent.getPlayersManaged();
                for (PlayerProfile player : players) {
                    if (player.getUserName().equals(userName)) { // USERNAME CHECK
                        throw new IllegalArgumentException("User already exists.");
                    }
                    counter2++;
                }
                counter++;
            }
            DataManager.saveList("parents.json", parents);

            PlayerProfile newPlayer = new PlayerProfile(userName, passWord);
            currentUser.addPlayer(newPlayer);

            newPlayerName.clear();
            newPlayerPass.clear();
            getParentsPlayers(); // UPDATE COMBO BOX

        } catch (IllegalArgumentException e) {
            rightError.setVisible(true);
            rightError.setText(e.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
