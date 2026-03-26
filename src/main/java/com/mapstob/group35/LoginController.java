package com.mapstob.group35;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField parentalField;

    @FXML
    private void handleLogin() {
        System.out.println("Logging in as: " + usernameField.getText());
    }

    @FXML
    private void handleRegister() {
        System.out.println("Registering: " + usernameField.getText());
    }

    @FXML
    private void handleExit(javafx.event.ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}