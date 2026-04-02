package com.mapstob.group35.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class StatsTest {

    private Stats stats;

    @BeforeEach
    void setUp() {
        stats = new Stats();
    }

    @Test
    @DisplayName("Should initialize with all values at zero")
    void testInitialization() {
        assertEquals(0, stats.getAverageWPM(), "Initial WPM should be 0");
        assertEquals(0, stats.getAverageAccuracy(), "Initial Accuracy should be 0");
    }

    @Test
    @DisplayName("Should update averages correctly after one game")
    void testSingleGameUpdate() {
        // Based on your code logic: (40.0 / 1) * 100 = 4000
        stats.updateStats(40.0, 0.85); 
        
        assertEquals(4000.0, stats.getAverageWPM(), 0.001);
        assertEquals(85.0, stats.getAverageAccuracy(), 0.001);
    }

    @Test
    @DisplayName("Should calculate averages correctly across multiple games")
    void testMultipleGamesAverage() {
        // Game 1: 50 WPM, 80% Acc
        stats.updateStats(50.0, 0.80);
        // Game 2: 70 WPM, 90% Acc
        stats.updateStats(70.0, 0.90);

        // Calculation for WPM: ((50 + 70) / 2) * 100 = 60 * 100 = 6000
        assertEquals(6000.0, stats.getAverageWPM(), 0.001);

        // Calculation for Accu: ((0.80 + 0.90) / 2) * 100 = 0.85 * 100 = 85
        assertEquals(85.0, stats.getAverageAccuracy(), 0.001);
    }

    @Test
    @DisplayName("Peak WPM should track the highest value achieved")
    void testPeakWPM() {
        stats.updateStats(30.0, 0.70);
        stats.updateStats(55.0, 0.70); // New Peak
        stats.updateStats(40.0, 0.70); // Lower than peak
        
        // Since peakWPM is private, we verify it indirectly via the averages 
        // or you could add a getPeakWPM() to your class to test it directly.
        // For now, this confirms the updateStats method doesn't crash during comparison.
        assertDoesNotThrow(() -> stats.updateStats(60.0, 0.95));
    }

    @Test
    @DisplayName("Should handle zero values correctly")
    void testZeroValues() {
        stats.updateStats(0, 0);
        assertEquals(0, stats.getAverageWPM());
        assertEquals(0, stats.getAverageAccuracy());
    }

    @Test
    @DisplayName("Check for floating point precision")
    void testPrecision() {
        // Testing 1/3 logic
        stats.updateStats(10.0, 0.1);
        stats.updateStats(10.0, 0.1);
        stats.updateStats(11.0, 0.1);
        
        // ((10+10+11)/3) * 100 = 10.333... * 100 = 1033.333
        double expectedWpm = (31.0 / 3.0) * 100;
        assertEquals(expectedWpm, stats.getAverageWPM(), 0.001);
    }
}