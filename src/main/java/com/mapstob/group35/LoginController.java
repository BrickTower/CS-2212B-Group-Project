package com.mapstob.group35;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button backButton;

    @FXML
    private void handleLogin() {
        System.out.println("Logging in as: " + usernameField.getText());
    }

    @FXML
    private void handleRegister() {
        System.out.println("Registering: " + usernameField.getText());
    }

    @FXML

    FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/mapstob/group35/mainMenu.fxml")
    );
    @FXML
    private void handleExit() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}