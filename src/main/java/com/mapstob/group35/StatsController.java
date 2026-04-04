package com.mapstob.group35;

import com.mapstob.group35.core.PlayerProfile;
import com.mapstob.group35.core.Stats;
import com.mapstob.group35.core.loggedUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Controller for the Statistics screen.
 * <p>
 * Handles all UI interactions on the Stats screen, including
 * navigating back to the Main Menu.
 * </p>
 *
 * @author Amlan Tamaki Konan
 */

public class StatsController {

    /**
     * The exit button that returns the user to the Main Menu.
     */
    @FXML
    private Button exitButton;

    /** Displays the player's username at the top of the screen. */
    @FXML
    private Text playerName;

    // STATISTICS (totals)
    /** Displays the total number of waves completed across all runs. */
    @FXML
    private Text totalWaves;

    /** Displays the average WPM across all runs. */
    @FXML
    private Text totalWPM;

    /** Displays the total number of mistakes made across all runs. */
    @FXML
    private Text totalMistakes;

    // ALL TIME HIGHSCORES
    /** Displays the highest WPM ever achieved in a single run. */
    @FXML
    private Text altWPM;

    /** Displays the highest number of waves ever reached in a single run. */
    @FXML
    private Text altWaves;

    /** Displays the highest number of coins ever earned in a single run. */
    @FXML
    private Text altCoinsCollected;

    /** Displays the lowest number of mistakes ever made in a single run. */
    @FXML
    private Text altMistakes;

    // CURRENT RUN SCORES
    /** Displays the WPM achieved in the current run. */
    @FXML
    private Text currWPM;

    /** Displays the number of waves reached in the current run. */
    @FXML
    private Text currWaves;

    /** Displays the number of coins earned in the current run. */
    @FXML
    private Text currCoinsCollected;

    /** Displays the number of mistakes made in the current run. */
    @FXML
    private Text currMistakes;

    /**
     * Initializes the Stats screen by loading the current player's data
     * and populating all stat fields.
     * <p>
     * Called automatically by JavaFX when the FXML is loaded.
     * </p>
     * @author Tamaki Konan
     */
    @FXML
    public void initialize() {
        // DEFAULT: Load the currently logged-in user
        loggedUser session = new loggedUser();
        loadPlayerData(session.getCurrUser());
    }

    /**
     * This method can be called from parentalController to change
     * the displayed stats to a specific student.
     */
    public void setPlayer(PlayerProfile player) {
        loadPlayerData(player);
    }

    private void loadPlayerData(PlayerProfile player) {
        if (player == null) return;

        Stats stats = player.getPlayerStats();
        playerName.setText(player.getUserName());

        // STATISTICS (totals)
        totalWaves.setText(String.valueOf(stats.getTotalWaves()));
        totalWPM.setText(String.format("%.1f", stats.getAverageWPM()));
        totalMistakes.setText(String.valueOf(stats.getTotalMistakes()));

        // HIGHSCORES
        altWPM.setText(String.format("%.1f", stats.getPeakWPM()));
        altWaves.setText(String.valueOf(stats.getPeakWaves()));
        altCoinsCollected.setText(String.valueOf(stats.getPeakCoins()));
        altMistakes.setText(String.valueOf(stats.getLowestMistakes()));

        // CURRENT RUN
        currCoinsCollected.setText(String.valueOf(player.getCoins()));
    }


    /**
     * Handles the exit button click event.
     * <p>
     * Loads and transitions the scene back to the Main Menu screen.
     * </p>
     *
     * @throws Exception if the mainMenu.fxml file cannot be loaded
     */
    @FXML
    private void handleExit() throws Exception {
        try {
            SceneManager.loadMainMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
