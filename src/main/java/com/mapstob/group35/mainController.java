package com.mapstob.group35;

import com.mapstob.group35.Gameplay.GameGUIManager;
import com.mapstob.group35.Gameplay.GameManager;
import com.mapstob.group35.core.ParentProfile;
import com.mapstob.group35.core.PlayerProfile;
import com.mapstob.group35.core.Stats;
import com.mapstob.group35.core.loggedUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class mainController {
    @FXML
    private Button newGame;
    @FXML
    private Button backButton;
    @FXML
    private VBox leaderboardBox;
    @FXML
    private Button parentalButton;

    // THIS LINE CREATES A CONNECTION TO THE LOGGED USER CLASS
    private loggedUser account = new loggedUser();
    // THIS LINE PULLS THE CURRENTLY SAVED USER (WHICH SHOULD BE ESTABLISHED IN LOGIN) AND SAVES IT TO A VARIABLE
    private PlayerProfile currentUser = account.getCurrUser();

    //temporary var
    private boolean runExists = (currentUser.getPlayerStats().getCurrentWave() > 0);

    @FXML
    private void initialize() {
        runButtonText();
        updateLeaderboard();
    }

    /**
     * Checks if a Run is currently occurring, and adjusts text accordingly.
     * @author Shaun Zhang
     */
    private void runButtonText(){
        if (runExists){
            assert newGame != null;
            newGame.setText("Continue Run");
        }
        else{
            assert newGame != null;
            newGame.setText("New Run");
        }
    }

    /**
     * Updates the leaderboard on right-hand side of Main Menu
     * @author Tamaki Konan
     */
    private void updateLeaderboard() {
        List<Object> users = DataManager.getAllUsersSortedByWaves();

        leaderboardBox.getChildren().remove(2, leaderboardBox.getChildren().size());

        int rank = 1;

        for (Object u : users) {
            String name;
            int wave;

            if (u instanceof PlayerProfile p) {
                name = p.getUserName();
                wave = p.getPlayerStats().getPeakWaves();
            } else {
                ParentProfile p = (ParentProfile) u;
                name = p.getUserName();
                wave = p.getPlayerStats().getPeakWaves();
            }

            HBox row = new HBox();
            row.setSpacing(10);

            Text nameText = new Text(rank + ". " + name);
            nameText.setFill(Color.WHITE);
            nameText.setFont(Font.font("Consolas", 12));

            Text waveText = new Text(String.valueOf(wave));
            waveText.setFill(Color.WHITE);
            waveText.setFont(Font.font("Consolas", 12));

            row.getChildren().addAll(nameText, waveText);
            leaderboardBox.getChildren().add(row);

            rank++;
            if (rank > 10) break;
        }
    }

    /**
        Moves to the tutorial screen
        @author Ethan Gregory
     */
    @FXML
    protected void goToTutorial() {
        try {
            SceneManager.loadTutorial();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Moves to the Game Screen. If the Current User happens to have Stats of a previous game, resume their progress.
     * @author Shaun Zhang
     */
    @FXML
    protected void StartGame() {
        GameManager gm;

        if (runExists) {
            Stats userStats = currentUser.getPlayerStats();
            gm = new GameManager(100, 100, userStats.getCurrentWave(), 10 + userStats.getCurrentWave(), currentUser.getCoins(), 10);
        }
        else{//if first run or if first run after a death
            gm = new GameManager();
        }
        try {
            SceneManager.loadGame(gm);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void goToStats() {
        try {
            SceneManager.loadStats();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * GUI Transition to Parental Controls screen. This function will only work if the currentUser is a {@link ParentProfile}.
     * Otherwise, throw a unique error message over text.
     *
     * @author Ethan Gregory
     */
    @FXML
    protected void goToParentalControls() {
        // GUARD AGAINST PLAYER PROFILE ACCESS
        if (!(currentUser instanceof ParentProfile)) {
            parentalButton.setText("Not a Parent!");
        } else {
            try {
                SceneManager.loadParentalControls();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Goes back to Base Menu, to allow for quitting / new user login
     * @author Ethan Gregory
     */
    @FXML
    protected void backToMainMenu() {
        try {
            SceneManager.loadBaseMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

