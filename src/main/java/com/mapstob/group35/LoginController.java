package com.mapstob.group35;

import com.mapstob.group35.core.ParentProfile;
import com.mapstob.group35.core.PlayerProfile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.util.List;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField parentalField;

    @FXML
    private Button backButton;

    @FXML
    private CheckBox newParentBox;

    @FXML
    private void handleLogin() {
        System.out.println("Logging in as: " + usernameField.getText());
    }

    @FXML
    private void handleRegister() {
        // GET TEXT FROM USERNAME AND PASSWORD FIELDS
        String newUserName = usernameField.getText();
        String newPassword = passwordField.getText();

        // DETERMINE WHETHER OR NOT IT IS A PARENT OR PLAYER PROFILE WE'RE MAKING
        if (newParentBox.isSelected()) {
            ParentProfile newParent = new ParentProfile(newUserName, newPassword);
            try {
                List<PlayerProfile> currentList = DataManager.loadList();
                currentList.add(newParent);
                DataManager.saveList(currentList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            PlayerProfile newPlayer = new PlayerProfile(newUserName, newPassword);
            try {
                List<PlayerProfile> currentList = DataManager.loadList();
                currentList.add(newPlayer);
                DataManager.saveList(currentList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void handleExit(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("baseMenu.fxml"));
        backButton.getScene().setRoot(load.load());
    }
}