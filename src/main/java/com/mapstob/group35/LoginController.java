package com.mapstob.group35;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

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
    private void handleLogin() {
        System.out.println("Logging in as: " + usernameField.getText());
    }

    @FXML
    private void handleRegister() {
        System.out.println("Registering: " + usernameField.getText());
    }

    @FXML
    private void handleExit(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        backButton.getScene().setRoot(load.load());
    }
}