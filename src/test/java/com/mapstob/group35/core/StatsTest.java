package com.mapstob.group35.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * tests for the Stats class
 * covers wpm tracking, accuracy, waves, coins, mistake tracking, playtime, wave state, and death
 *
 */
public class StatsTest {

    private Stats stats;

    // fresh stats object before each test so nothing bleeds over
    @BeforeEach
    public void setUp() {
        stats = new Stats();
    }

    // everything should start at zero when you first make a stats object
    // now includes currentWave and totalPlaytime
    @Test
    public void testInitialVal() {
        assertEquals(0.0, stats.getAverageWPM(), 0.001);
        assertEquals(0.0, stats.getAverageAccuracy(), 0.001);
        assertEquals(0.0, stats.getPeakWPM(), 0.001);
        assertEquals(0, stats.getTotalWaves());
        assertEquals(0, stats.getPeakWaves());
        assertEquals(0, stats.getTotalCoins());
        assertEquals(0, stats.getPeakCoins());
        assertEquals(0, stats.getTotalMistakes());
        assertEquals(0, stats.getLowestMistakes());
        assertEquals(0, stats.getCurrentWave());
        assertEquals(0, stats.getTotalPlaytime());
    }

    // basic single game update - all fields should reflect what we passed in
    @Test
    public void testSingleGame() {
        stats.updateStats(60.0, 0.9, 5, 100, 3, 120);

        // average wpm is stored as wpm/60 per the implementation
        assertEquals(1, stats.getAverageWPM(), 0.001);
        // accuracy is now set directly from the passed value
        assertEquals(0.9, stats.getAverageAccuracy(), 0.001);
        assertEquals(60.0, stats.getPeakWPM(), 0.001);
        assertEquals(5, stats.getTotalWaves());
        assertEquals(5, stats.getPeakWaves());
        assertEquals(100, stats.getTotalCoins());
        assertEquals(100, stats.getPeakCoins());
        assertEquals(3, stats.getTotalMistakes());
        assertEquals(3, stats.getLowestMistakes());
        assertEquals(120, stats.getTotalPlaytime());
    }

    // peak wpm should only update when a new higher value comes in
    @Test
    public void testPeakWPM() {
        stats.updateStats(50.0, 0.8, 3, 50, 2, 60);
        stats.updateStats(80.0, 0.9, 4, 60, 1, 90);

        assertEquals(80.0, stats.getPeakWPM(), 0.001);
    }

    // if the second game has lower wpm the peak should stay the same
    @Test
    public void testWPMDecrease() {
        stats.updateStats(100.0, 1.0, 10, 200, 0, 200);
        stats.updateStats(40.0, 0.5, 2, 50, 5, 40);

        assertEquals(100.0, stats.getPeakWPM(), 0.001);
    }

    // accuracy is now set directly - the last call wins, no averaging
    @Test
    public void testDirectAcc() {
        stats.updateStats(60.0, 1.0, 5, 100, 0, 60);
        stats.updateStats(60.0, 0.5, 3, 50, 2, 60);

        // old behaviour was to average these - new behaviour just stores the latest value
        assertEquals(0.5, stats.getAverageAccuracy(), 0.001);
    }

    // total waves should still accumulate across games
    @Test
    public void testAccumulate() {
        stats.updateStats(60.0, 0.9, 5, 100, 1, 60);
        stats.updateStats(60.0, 0.9, 3, 100, 1, 60);

        assertEquals(8, stats.getTotalWaves());
        assertEquals(5, stats.getPeakWaves()); // first game had more waves
    }

    // totalCoins is now a direct assignment - it reflects the most recent run only
    // this is different from the old behaviour where coins accumulated
    @Test
    public void tesTotalCoins() {
        stats.updateStats(60.0, 0.8, 3, 200, 1, 60);
        stats.updateStats(60.0, 0.8, 3, 50, 1, 60);

        // coins should be 50 (last run) not 250 (sum)
        assertEquals(50, stats.getTotalCoins());
    }

    // peakCoins should still track the highest single run regardless of direct assignment
    @Test
    public void testTracksHighestRun() {
        stats.updateStats(60.0, 0.8, 3, 200, 1, 60);
        stats.updateStats(60.0, 0.8, 3, 50, 1, 60);

        assertEquals(200, stats.getPeakCoins()); // first run was better
    }

    // mistakes still accumulate across games
    @Test
    public void testMistakesAcc() {
        stats.updateStats(60.0, 0.8, 3, 100, 10, 60);
        stats.updateStats(60.0, 0.8, 3, 100, 2, 60);
        stats.updateStats(60.0, 0.8, 3, 100, 6, 60);

        assertEquals(18, stats.getTotalMistakes());
        assertEquals(2, stats.getLowestMistakes()); // second game was cleanest
    }

    // lowestMistakes starts at 0 so the first game should always set it
    @Test
    public void testLowMistake() {
        stats.updateStats(60.0, 0.8, 3, 100, 7, 60);
        assertEquals(7, stats.getLowestMistakes());
    }

    // zero mistakes is a valid run and should be stored as lowest
    @Test
    public void testZeroMistakes() {
        stats.updateStats(60.0, 1.0, 5, 150, 0, 60);
        assertEquals(0, stats.getLowestMistakes());
        assertEquals(0, stats.getTotalMistakes());
    }

    // playtime should accumulate across games since it uses +=
    @Test
    public void testPlaytimeAcc() {
        stats.updateStats(60.0, 0.8, 3, 100, 2, 90);
        stats.updateStats(60.0, 0.8, 3, 100, 2, 45);

        assertEquals(135, stats.getTotalPlaytime());
    }

    // playtime starting at zero and adding one game
    @Test
    public void testPlaytimeSingle() {
        stats.updateStats(60.0, 0.8, 3, 100, 2, 300);
        assertEquals(300, stats.getTotalPlaytime());
    }

    // setCurrWave should update getCurrentWave
    @Test
    public void testUpdateCurr() {
        stats.setCurrWave(7);
        assertEquals(7, stats.getCurrentWave());
    }

    // getCurrentWave starts at zero before anything is set
    @Test
    public void testCurrZero() {
        assertEquals(0, stats.getCurrentWave());
    }

    // death should reset currentWave and totalCoins back to 0
    @Test
    public void testDeathWaveCoins() {
        stats.updateStats(60.0, 0.8, 3, 200, 2, 60);
        stats.setCurrWave(5);

        stats.death();

        assertEquals(0, stats.getCurrentWave());
        assertEquals(0, stats.getTotalCoins());
    }

    // death should not touch peakCoins - that record should survive death
    @Test
    public void testDeathPeakCoins() {
        stats.updateStats(60.0, 0.8, 3, 200, 2, 60);
        stats.death();

        assertEquals(200, stats.getPeakCoins());
    }

    // death should not touch mistakes, waves, playtime, or wpm - only coins and current wave
    @Test
    public void testDeathReset() {
        stats.updateStats(60.0, 0.8, 3, 100, 5, 60);
        stats.death();

        assertEquals(3, stats.getTotalWaves());
        assertEquals(5, stats.getTotalMistakes());
        assertEquals(60, stats.getTotalPlaytime());
        assertEquals(60.0, stats.getPeakWPM(), 0.001);
    }

    // resetPeakWaves should bring peakWaves back to 0
    @Test
    public void testResetPeak() {
        stats.updateStats(60.0, 0.8, 10, 100, 2, 60);
        assertEquals(10, stats.getPeakWaves()); // confirm it was set first

        stats.resetPeakWaves();
        assertEquals(0, stats.getPeakWaves());
    }

    // resetPeakWaves on a fresh object should be harmless
    @Test
    public void testResetPeakNew() {
        assertDoesNotThrow(() -> stats.resetPeakWaves());
        assertEquals(0, stats.getPeakWaves());
    }

    // running many games should still accumulate waves, mistakes, and playtime correctly
    // coins and accuracy are direct assignments so only the last value matters for those
    @Test
    public void testUpdateStatsAcc() {
        for (int i = 0; i < 5; i++) {
            stats.updateStats(60.0, 0.8, 4, 100, 3, 60);
        }

        assertEquals(20, stats.getTotalWaves());     // 5 games * 4 waves - accumulates
        assertEquals(100, stats.getTotalCoins());    // direct assignment - last run value only
        assertEquals(15, stats.getTotalMistakes());  // 5 games * 3 mistakes - accumulates
        assertEquals(300, stats.getTotalPlaytime()); // 5 games * 60 seconds - accumulates
    }
}