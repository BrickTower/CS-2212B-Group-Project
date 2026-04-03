package com.mapstob.group35.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * tests for the Stats class
 * covers wpm tracking, accuracy, waves, coins, and mistake tracking
 */
public class StatsTest {

    private Stats stats;

    // fresh stats object before each test so nothing bleeds over
    @BeforeEach
    public void setUp() {
        stats = new Stats();
    }

    // everything should start at zero when you first make a stats object
    @Test
    public void testInitialValues() {
        assertEquals(0.0, stats.getAverageWPM(), 0.001);
        assertEquals(0.0, stats.getAverageAccuracy(), 0.001);
        assertEquals(0.0, stats.getPeakWPM(), 0.001);
        assertEquals(0, stats.getTotalWaves());
        assertEquals(0, stats.getPeakWaves());
        assertEquals(0, stats.getTotalCoins());
        assertEquals(0, stats.getPeakCoins());
        assertEquals(0, stats.getTotalMistakes());
        assertEquals(0, stats.getLowestMistakes());
    }

    // basic single game update - wpm and accuracy should reflect what we passed in
    @Test
    public void testUpdateStats_singleGame() {
        stats.updateStats(60.0, 0.9, 5, 100, 3);

        // average wpm is stored as wpm/60 per the implementation
        assertEquals(60.0 / 60.0, stats.getAverageWPM(), 0.001);
        // accuracy = (totalAccuSum / gamesPlayed) * 100
        assertEquals(90.0, stats.getAverageAccuracy(), 0.001);
        assertEquals(60.0, stats.getPeakWPM(), 0.001);
        assertEquals(5, stats.getTotalWaves());
        assertEquals(5, stats.getPeakWaves());
        assertEquals(100, stats.getTotalCoins());
        assertEquals(100, stats.getPeakCoins());
        assertEquals(3, stats.getTotalMistakes());
        assertEquals(3, stats.getLowestMistakes());
    }

    // peak wpm should only update when a new higher value comes in
    @Test
    public void testUpdateStats_peakWPMUpdates() {
        stats.updateStats(50.0, 0.8, 3, 50, 2);
        stats.updateStats(80.0, 0.9, 4, 60, 1);

        assertEquals(80.0, stats.getPeakWPM(), 0.001);
    }

    // if the second game has lower wpm the peak should stay the same
    @Test
    public void testUpdateStats_peakWPMDoesNotDecrease() {
        stats.updateStats(100.0, 1.0, 10, 200, 0);
        stats.updateStats(40.0, 0.5, 2, 50, 5);

        assertEquals(100.0, stats.getPeakWPM(), 0.001);
    }

    // total waves and peak waves track independently - totals add up, peak only goes up
    @Test
    public void testUpdateStats_wavesAccumulate() {
        stats.updateStats(60.0, 0.9, 5, 100, 1);
        stats.updateStats(60.0, 0.9, 3, 100, 1);

        assertEquals(8, stats.getTotalWaves());
        assertEquals(5, stats.getPeakWaves()); // first game had more waves
    }

    // coins should stack across games, peak only tracks best single run
    @Test
    public void testUpdateStats_coinsAccumulate() {
        stats.updateStats(60.0, 0.8, 3, 200, 1);
        stats.updateStats(60.0, 0.8, 3, 50, 1);

        assertEquals(250, stats.getTotalCoins());
        assertEquals(200, stats.getPeakCoins()); // first run was better
    }

    // mistakes total up and lowest mistakes reflects the best (fewest) run
    @Test
    public void testUpdateStats_mistakesTrackedCorrectly() {
        stats.updateStats(60.0, 0.8, 3, 100, 10);
        stats.updateStats(60.0, 0.8, 3, 100, 2);
        stats.updateStats(60.0, 0.8, 3, 100, 6);

        assertEquals(18, stats.getTotalMistakes());
        assertEquals(2, stats.getLowestMistakes()); // second game was cleanest
    }

    // lowestMistakes starts at 0 so the first game should always set it
    @Test
    public void testUpdateStats_lowestMistakesFirstGame() {
        stats.updateStats(60.0, 0.8, 3, 100, 7);
        assertEquals(7, stats.getLowestMistakes());
    }

    // zero mistakes is a valid run and should be tracked as the lowest possible
    @Test
    public void testUpdateStats_zeroMistakes() {
        stats.updateStats(60.0, 1.0, 5, 150, 0);
        assertEquals(0, stats.getLowestMistakes());
        assertEquals(0, stats.getTotalMistakes());
    }

    // make sure accuracy averages correctly across two different games
    @Test
    public void testUpdateStats_accuracyAveragesTwoGames() {
        stats.updateStats(60.0, 1.0, 5, 100, 0);  // 100% accuracy
        stats.updateStats(60.0, 0.5, 3, 50, 2);   // 50% accuracy

        // (1.0 + 0.5) / 2 * 100 = 75.0
        assertEquals(75.0, stats.getAverageAccuracy(), 0.001);
    }

    // running many games in a row to make sure nothing breaks with accumulation
    @Test
    public void testUpdateStats_multipleGamesAccumulate() {
        for (int i = 0; i < 5; i++) {
            stats.updateStats(60.0, 0.8, 4, 100, 3);
        }

        assertEquals(20, stats.getTotalWaves());   // 5 games * 4 waves
        assertEquals(500, stats.getTotalCoins());  // 5 games * 100 coins
        assertEquals(15, stats.getTotalMistakes()); // 5 games * 3 mistakes
    }
}