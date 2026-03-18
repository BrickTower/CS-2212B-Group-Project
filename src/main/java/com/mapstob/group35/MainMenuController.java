package com.mapstob.group35;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController {
    private Button logIn;
    private Button quit;
    private Button leaderboard;
    private Button tutorial;

    // CLOSE GAME ON QUIT PRESS
    @FXML
    protected void onQuit() {
        Platform.exit(); // CLOSE GAME
    }
}
