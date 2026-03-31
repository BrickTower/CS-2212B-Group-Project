package com.mapstob.group35;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {
    @FXML
    private Button logIn;
    private Button quit;
    private Button leaderboard;
    private Button tutorial;

    @FXML
    private void onLogInClick() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login_Page.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) logIn.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
