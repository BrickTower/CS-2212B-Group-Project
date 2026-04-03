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
    /** The current wave of the player's current game */
    @JsonProperty("currentWave")
    private int currentWave;
    /** The total amount of waves the Player has completed across all games */
    @JsonProperty("totalWaves")
    private int totalWaves;
    /** The highest amount of waves the Player has reached in a single run */
    @JsonProperty("peakWaves")
    private int peakWaves;
    /** The total amount of coins the Player has earned across all games */
    @JsonProperty("totalCoins")
    private int totalCoins;
    /** The highest amount of coins the Player has earned in a single run */
    @JsonProperty("peakCoins")
    private int peakCoins;
    /** The total amount of mistakes the Player has made across all games */
    @JsonProperty("totalMistakes")
    private int totalMistakes;
    /** The lowest amount of mistakes the Player has made in a single run */
    @JsonProperty("lowestMistakes")
    private int lowestMistakes;

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
        this.currentWave = 0;
        this.totalWaves = 0;
        this.peakWaves = 0;
        this.totalCoins = 0;
        this.peakCoins = 0;
        this.totalMistakes = 0;
        this.lowestMistakes = 0;
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
    public void updateStats(double wpm, double accuracy, int waves, int coins, int mistakes) {
        // INCREASE GAMES PLAYED
        if (wpm > this.peakWPM) {
            this.peakWPM = (float) wpm;
        }

        // UPDATE AVERAGES
        this.totalWPM += wpm;
        this.totalGamesPlayed++;
        this.averageAccuracy = accuracy;
        this.averageWPM = (wpm / 60);

        //
        this.totalWaves += waves;
        if (waves > this.peakWaves) this.peakWaves = waves;

        this.totalCoins += coins;
        if (coins > this.peakCoins) this.peakCoins = coins;

        this.totalMistakes += mistakes;
        if (this.lowestMistakes == 0 || mistakes < this.lowestMistakes) this.lowestMistakes = mistakes;
    }

    /**
     * Returns the Player's current wave of the game
     * @return An Integer representation of the current wave (defaults to 0 if dead)
     * @author Ethan Gregory
     */
    public int getCurrentWave() {
        return this.currentWave;
    }

    /**
     * Sets the Player's current wave in the game. Should be used for saving, and when the player dies, it should be set back to 0.
     * @param newWave Integer value showing the current wave that the Player is on.
     * @author Ethan Gregory
     */
    public void setCurrWave(int newWave) {
        this.currentWave = newWave;
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

    /**
     * Returns the Player's highest recorded WPM across all games.
     * @return A Double representation of the Player's peak WPM (i.e. 100.0)
     * @author Amlan Tamaki Konan
     */
    public double getPeakWPM() { return this.peakWPM; }
    /**
     * Returns the total number of waves the Player has completed across all games.
     * @return An Integer representation of the total waves completed
     */
    public int getTotalWaves() { return this.totalWaves; }

    /**
     * Returns the highest number of waves the Player has reached in a single run.
     * @return An Integer representation of the peak waves reached
     */
    public int getPeakWaves() { return this.peakWaves; }

    /**
     * Returns the total number of coins the Player has earned across all games.
     * @return An Integer representation of the total coins earned
     */
    public int getTotalCoins() { return this.totalCoins; }

    /**
     * Returns the highest number of coins the Player has earned in a single run.
     * @return An Integer representation of the peak coins earned
     */
    public int getPeakCoins() { return this.peakCoins; }

    /**
     * Returns the total number of mistakes the Player has made across all games.
     * @return An Integer representation of the total mistakes made
     */
    public int getTotalMistakes() { return this.totalMistakes; }

    /**
     * Returns the lowest number of mistakes the Player has made in a single run.
     * @return An Integer representation of the lowest mistakes made in a single run
     */
    public int getLowestMistakes() { return this.lowestMistakes; }
}


