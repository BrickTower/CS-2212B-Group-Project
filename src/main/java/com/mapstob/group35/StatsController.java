package com.mapstob.group35;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class StatsController {
    @FXML
    private Button exitButton;

    @FXML
    private void handleExit() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
