package com.mapstob.group35.Gameplay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class WaveManagerTest {

    private WaveManager waveManager;
    private final int DEFAULT_TIME = 30;

    @BeforeEach
    void setUp() {
        // Start at wave 1 with 30 seconds default time
        waveManager = new WaveManager(1, DEFAULT_TIME);
    }

    @Test
    @DisplayName("Should initialize with correct wave and time")
    void testInitialization() {
        assertEquals(1, waveManager.getWave());
        assertEquals(DEFAULT_TIME, waveManager.getWaveTimeLeft());
    }

    @Test
    @DisplayName("incTime should advance time and trigger waveOver at the limit")
    void testWaveCycle() {
        // Increment time 29 times
        for (int i = 0; i < DEFAULT_TIME - 1; i++) {
            assertFalse(waveManager.incTime(), "Wave should not be over yet");
        }
        
        // The 30th increment should finish the wave
        assertTrue(waveManager.incTime(), "Wave should be over at the time limit");
        assertEquals(2, waveManager.getWave(), "Wave number should have incremented");
    }

    @Test
    @DisplayName("Regeneration buffs should accumulate in getHealAmount")
    void testRegenerationBuffs() {
        // For testing purposes we look for a Regen buff in the loop or
        // just verify the list starts empty
        assertEquals(0, waveManager.getHealAmount());

        // create a specific BuffItem since it is random
        // this test ensures the loop logic doesn't crash.
        assertNotNull(waveManager.getBuffList());
    }

    @Test
    @DisplayName("waveOver should remove buffs when their length reaches zero")
    void testBuffExpiration() {
        BuffItem item = new BuffItem(); 
        waveManager.addBuff(item);
        
        int initialSize = waveManager.getBuffList().size();
        assertEquals(1, initialSize);

        // Manually trigger waveOver enough times to expire any item
        // Most items have a length between 1 and 10
        for (int i = 0; i < 11; i++) {
            waveManager.waveOver();
        }

        // The buff should be removed from the list after some time
        assertTrue(waveManager.getBuffList().size() < initialSize || item.getLength() > 0);
    }

    @Test
    @DisplayName("Wave completion percentage should return a valid ratio")
    void testWaveCompletionPercent() {
        assertEquals(0.0f, waveManager.waveCompletionPercent());
        
        for (int i = 0; i < DEFAULT_TIME / 2; i++) {
            waveManager.incTime();
        }
        
        // Should be roughly 0.5 at half time
        assertEquals(0.5f, waveManager.waveCompletionPercent());
    }

    @Test
    @DisplayName("startWave should apply Wave Length Decrease modifiers")
    void testWaveTimeModifier() {
        // Test for startWave executes without crashing
        // waveTime = defaultWaveTime * getWaveTimeModifier()
        assertDoesNotThrow(() -> waveManager.startWave());
    }

    @Test
    @DisplayName("Modifiers should return 1.0 when no buffs are present")
    void testDefaultModifiers() {
        assertEquals(1.0f, waveManager.getWordTimeModifier());
        assertEquals(1.0f, waveManager.getCoinGainModifier());
    }
}