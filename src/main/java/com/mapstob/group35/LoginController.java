package com.mapstob.group35;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mapstob.group35.core.ParentProfile;
import com.mapstob.group35.core.PlayerProfile;
import com.mapstob.group35.core.loggedUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the Login Page, handling verifying any log in attempts, alongside creation of any new Parent accounts
 */
public class LoginController {

    /// Field to enter account username
    @FXML
    private TextField usernameField;

    /// Field to enter account password
    @FXML
    private PasswordField passwordField;

    /// Button to go back to Base Menu
    @FXML
    private Button backButton;

    /// Checkbox for determining if new account is Parent Profile
    @FXML
    private CheckBox newParentBox;

    /// Error message for invalid issues with log in (User already exists, etc.)
    @FXML
    private Label errorMessage;

    /**
     * Handles a login attempt, looking through the JSON file for any signs of correct account, and if so, pass into the Main
     * @author Ethan Gregory
     */
    @FXML
    private void handleLogin() {
        // GET TEXT FROM USERNAME AND PASSWORD FIELDS
        String userName = usernameField.getText();
        String passWord = passwordField.getText();

        // CHECK IF FIELDS ARE ALL FILLED, THROW ERROR MESSAGE IF NOT
        if (userName.isBlank() || passWord.isBlank()) {
            errorMessage.setText("Not all fields are filled."); // SET ERROR MESSAGE ON REGISTER
            if (!errorMessage.isVisible()) {
                errorMessage.setVisible(true);
            }
            return; // END PROGRAM EARLY
        }

        try {
            boolean userFound = false;
            PlayerProfile foundUser = null;

            List<ParentProfile> parents = DataManager.loadList("parents.json", new TypeReference<List<ParentProfile>>() {
            });

            for (ParentProfile parent : parents) { // CHECK PARENT PROFILES FIRST
                if (parent.getUserName().equals(userName) && parent.getPassword().equals(passWord)) {
                    userFound = true;
                    foundUser = parent;
                    break;
                } else if (!parent.getPlayersManaged().isEmpty()) {
                    List<PlayerProfile> players = parent.getPlayersManaged();
                    for (PlayerProfile player : players) {
                        if (player.getUserName().equals(userName) && player.getPassword().equals(passWord)) {
                            userFound = true;
                            foundUser = player;
                            break;
                        }
                    }
                }
            }

            if (userFound) {
                // SAVE USER TO OUR GLOBAL LOGGED USER BEFORE CHANGING SCENES
                loggedUser account = new loggedUser();
                account.setCurrUser(foundUser);

                // CHANGE TO MAIN MENU
                FXMLLoader load = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
                Parent root = load.load();
                backButton.getScene().setRoot(root);
            } else {
                throw new IllegalArgumentException("Incorrect username/password");
            }
        } catch (IllegalArgumentException e) {
            errorMessage.setText(e.getMessage()); // SET ERROR MESSAGE ON REGISTER
            if (!errorMessage.isVisible()) {
                errorMessage.setVisible(true);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Handles the registering of a new Profile. Checks to make sure username is not taken prior to registering, to prevent doubles.
     * @return Cleared userName and passWord fields if account registration was successful. Register button will
     */
    @FXML
    private void handleRegister() {
        // GET TEXT FROM USERNAME AND PASSWORD FIELDS
        String newUserName = usernameField.getText();
        String newPassword = passwordField.getText();

        // CHECK IF FIELDS ARE ALL FILLED, THROW ERROR MESSAGE IF NOT
        if (newUserName.isBlank() || newPassword.isBlank()) {
            errorMessage.setText("Not all fields are filled."); // SET ERROR MESSAGE ON REGISTER
            if (!errorMessage.isVisible()) {
                errorMessage.setVisible(true);
            }
            return; // END PROGRAM EARLY
        }
        try { // SURROUND ANYTHING INVOLVING LOADING FROM JSON IN TRY CATCH
            List<ParentProfile> parents = DataManager.loadList("parents.json", new TypeReference<List<ParentProfile>>() {
            });
            List<PlayerProfile> players = DataManager.loadList("players.json", new TypeReference<List<PlayerProfile>>() {
            });
            for (PlayerProfile player : players) { // USER EXISTENCE GUARDS
                if (player.getUserName().equals(newUserName)) {
                    throw new IllegalArgumentException("User exists.");
                }
            }
            for (ParentProfile parent : parents) {
                if (parent.getUserName().equals(newUserName)) {
                    throw new IllegalArgumentException("User exists.");
                }
            }

                // DETERMINE WHETHER IT IS A PARENT OR PLAYER PROFILE WE'RE MAKING
                if (newParentBox.isSelected()) {
                    ParentProfile newParent = new ParentProfile(newUserName, newPassword);
                    parents.add(newParent);
                    DataManager.saveList("parents.json", parents);
                    // CLEAR FIELDS UPON SUCCESSFUL REGISTERING
                    usernameField.clear();
                    passwordField.clear();
                } else {
                    PlayerProfile newPlayer = new PlayerProfile(newUserName, newPassword);
                    players.add(newPlayer);
                    DataManager.saveList("players.json", players);
                    // CLEAR FIELDS UPON SUCCESSFUL REGISTERING
                    usernameField.clear();
                    passwordField.clear();
                }
        } catch (IOException e) { // THROW RUNTIME EXCEPTION IF SPECIFICALLY IO
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) { // IF NATURAL CAUGHT ERROR, OUTPUT ERROR MESSAGE
            errorMessage.setText(e.getMessage()); // SET ERROR MESSAGE ON REGISTER
            if (!errorMessage.isVisible()) {
                errorMessage.setVisible(true);
            }
        }

    }

    @FXML
    private void handleExit(ActionEvent event) throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("baseMenu.fxml"));
        backButton.getScene().setRoot(load.load());
    }
}