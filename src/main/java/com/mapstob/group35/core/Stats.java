package com.mapstob.group35.core;

// FIX FOR JSON AUTO DETECT FROM GITHUB COPILOT: https://github.com/copilot/share/c03d4088-4924-8c05-b150-284ec451014e. CODE THAT IS NOT ANNOTATIONS IS HUMAN WRITTEN
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE // ADD THIS
)
public class Stats {
    /** A sum of all WPM entered by the Player during games*/
    @JsonProperty("totalWPM")
    private double totalWPM;
    /** The average WPM entered throughout all the player's games */
    @JsonProperty("avgWPM")
    private double averageWPM;
    /** The highest WPM the Player has managed to achieve*/
    @JsonProperty("peakWPM")
    private double peakWPM;
    /** A sum of all the accuracy scores the Player achieved during their game*/
    @JsonProperty("accuSum")
    private double totalAccuSum;
    /** The Accuracy of the Player achieved during the games they've played */
    @JsonProperty("averageAccuracy")
    private double averageAccuracy;
    /** The total amount of games the Player has done.*/
    @JsonProperty("gamesPlayed")
    private int totalGamesPlayed;

    /**
     Stats constructor. Initializes a new Stats object.

     @author Ethan Gregory
     */
    public Stats() {
        this.totalWPM = 0;
        this.averageWPM = 0;
        this.peakWPM = 0;
        this.totalGamesPlayed = 0;
        this.totalAccuSum = 0;
        this.averageAccuracy = 0;
    }

    /**
     * Takes the WPM and Accuracy score calculated over the course of a Game's Round and updates the player's corresponding Stats.
     * For specifics: <br>
     * - If WPM exceeds any current previous WPM value, update peakWPM.<br>
     * - Update the averages for Accuracy and WPM<br><br>
     * This function should only be called <b>once</b> after Game end, as it updates a counter of games played for the accuracy calculations.
     * @param wpm A {@link Double} representation of words typed per minute (i.e. 25.3 WPM)
     * @param accuracy A percentage amount of words typed correctly over the course of the round (3 out of 4 typed correctly = 75.0)
     * @author Ethan Gregory
     */
    public void updateStats(double wpm, double accuracy) {
        // INCREASE GAMES PLAYED
        if (wpm > this.peakWPM) {
            this.peakWPM = (float) wpm;
        }

        // UPDATE AVERAGES
        this.totalWPM += wpm;
        this.totalGamesPlayed++;
        this.totalAccuSum += accuracy; // ADD ACCURACY SCORE TO TOTAL SUM
        this.averageAccuracy = (this.totalAccuSum / this.totalGamesPlayed) * 100; // DIVIDE BY GAMES PLAYED AND MULTIPLIED BY 100 TO GET ACCURACY
        this.averageWPM = (wpm / 60);


    }

    /**
     * Returns the Player's average WPM (words per minute)
     * @return A Double representation of the Average WPM, as a percentage (i.e. 75%)
     */
    public double getAverageWPM() {
        return this.averageWPM;
    }

    /**
     * Returns the Player's Accuracy, across all games.
     * @return A Double representation of the Player's Accuracy, as a percentage (i.e. 75%)
     */
    public double getAverageAccuracy() {
        return this.averageAccuracy;
    }
}
