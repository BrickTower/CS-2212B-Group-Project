package com.mapstob.group35.Gameplay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TextUnitTest {

    private TextUnit unit;

    @BeforeEach
    void setUp() {
        unit = new TextUnit("Apple", 10);
    }

    @Test
    @DisplayName("Should initialize with correct text and time values")
    void testInitialization() {
        assertEquals("Apple", unit.getText());
        // Use (double) to match the return type of the method
        double limit = unit.getTimeLimit();
        assertEquals(10.0, limit);
    }

    @Test
    @DisplayName("incTime should increment current time")
    void testIncrementTime() {
        unit.incTime();
        assertEquals(1, unit.getCurTime());
        unit.incTime();
        assertEquals(2, unit.getCurTime());
    }

    @Test
    @DisplayName("checkTime should return true only when limit is reached or exceeded")
    void testCheckTime() {
        // At start (0/10)
        assertFalse(unit.checkTime());

        // Increment to 9/10
        for(int i = 0; i < 9; i++) unit.incTime();
        assertFalse(unit.checkTime());

        // Increment to 10/10
        unit.incTime();
        assertTrue(unit.checkTime(), "Should be true when curTime == timeLimit");

        // Increment to 11/10
        unit.incTime();
        assertTrue(unit.checkTime(), "Should be true when curTime > timeLimit");
    }

    @Test
    @DisplayName("getPercentTime should return correct float ratio")
    void testPercentTime() {
        unit.incTime(); // 1 / 10
        assertEquals(0.1f, unit.getPercentTime(), 0.001f);
        
        for(int i = 0; i < 4; i++) unit.incTime(); // 5 / 10
        assertEquals(0.5f, unit.getPercentTime(), 0.001f);
    }

    @Test
    @DisplayName("checkSpellingValidity should return 0 for perfect match")
    void testSpellingPerfectMatch() {
        assertEquals(0, unit.checkSpellingValidity("Apple"));
    }

    @Test
    @DisplayName("checkSpellingValidity should count mismatches correctly")
    void testSpellingMismatches() {
        // "Apply" vs "Apple" -> 1 mistake ('y' vs 'e')
        assertEquals(1, unit.checkSpellingValidity("Apply"));
        
        // "Axxle" vs "Apple" -> 2 mistakes
        assertEquals(2, unit.checkSpellingValidity("Axxle"));
        
        // "xxxxx" vs "Apple" -> 5 mistakes
        assertEquals(5, unit.checkSpellingValidity("xxxxx"));
    }

    @Test
    @DisplayName("CRASH TEST: Short input should not crash the game")
    void testShortInputHandling() {
        // Target is "Apple" (5 chars). Input is "App" (3 chars).
        // WARNING: Your current code WILL fail this test and throw an Exception.
        assertDoesNotThrow(() -> {
            unit.checkSpellingValidity("App");
        }, "The code should handle inputs shorter than the target text without crashing.");
    }
}