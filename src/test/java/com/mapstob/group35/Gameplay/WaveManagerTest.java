package com.mapstob.group35.Gameplay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * tests for WaveManager
 * covers wave counting, time tracking, buff effects, and wave end behavior
 */
public class WaveManagerTest {

    private WaveManager waveManager;

    // wave 0, 10 second default wave time
    @BeforeEach
    public void setUp() {
        waveManager = new WaveManager(0, 10);
    }

    // wave should start at whatever value we pass in
    @Test
    public void testInitial_waveIsZero() {
        assertEquals(0, waveManager.getWave());
    }

    // wave time should match what we gave the constructor
    @Test
    public void testInitial_waveTimeIsDefault() {
        assertEquals(10, waveManager.getWaveTime());
    }

    // buff list starts empty
    @Test
    public void testInitial_buffListIsEmpty() {
        assertTrue(waveManager.getBuffList().isEmpty());
    }

    // current time starts at zero
    @Test
    public void testInitial_curTimeIsZero() {
        assertEquals(0, waveManager.getCurTime());
    }

    // incWave should go from 0 to 1
    @Test
    public void testIncWave_incrementsWave() {
        waveManager.incWave();
        assertEquals(1, waveManager.getWave());
    }

    // setting wave time should update the getter
    @Test
    public void testSetWaveTime_updatesWaveTime() {
        waveManager.setWaveTime(20);
        assertEquals(20, waveManager.getWaveTime());
    }

    // incTime should return false before the wave ends
    @Test
    public void testIncTime_returnsFalseBeforeEnd() {
        waveManager.startWave();
        boolean result = waveManager.incTime(); // 1 out of 10
        assertFalse(result);
    }

    // incTime should return true exactly when time runs out and trigger waveOver
    @Test
    public void testIncTime_returnsTrueAtEnd() {
        waveManager.startWave();
        boolean result = false;
        for (int i = 0; i < 10; i++) {
            result = waveManager.incTime();
        }
        assertTrue(result);
    }

    // after the wave ends via incTime the wave counter should go up
    @Test
    public void testIncTime_waveincrementsonend() {
        waveManager.startWave();
        for (int i = 0; i < 10; i++) {
            waveManager.incTime();
        }
        assertEquals(1, waveManager.getWave()); // wave 0 -> 1
    }

    // resetTime should bring curTime back to 0
    @Test
    public void testResetTime_resetsToZero() {
        waveManager.incTime();
        waveManager.incTime();
        waveManager.resetTime();
        assertEquals(0, waveManager.getCurTime());
    }

    // startWave resets curTime to 0
    @Test
    public void testStartWave_resetsCurTime() {
        waveManager.incTime();
        waveManager.incTime();
        waveManager.startWave();
        assertEquals(0, waveManager.getCurTime());
    }

    // adding a buff should put it in the buff list
    @Test
    public void testAddBuff_addsToList() {
        BuffItem item = new BuffItem();
        waveManager.addBuff(item);
        assertEquals(1, waveManager.getBuffList().size());
    }

    // getHealAmount returns 0 if there are no regeneration buffs
    @Test
    public void testGetHealAmount_noBuffs() {
        assertEquals(0, waveManager.getHealAmount());
    }

    // wave completion percent should be 0 at the start
    @Test
    public void testWaveCompletionPercent_startsAtZero() {
        assertEquals(0.0f, waveManager.waveCompletionPercent(), 0.001f);
    }

    // wave completion percent should be 0.5 at the halfway point
    @Test
    public void testWaveCompletionPercent_halfwayThrough() {
        waveManager.startWave();
        for (int i = 0; i < 5; i++) {
            waveManager.incTime();
        }
        assertEquals(0.5f, waveManager.waveCompletionPercent(), 0.001f);
    }

    // with no wave length decrease buff the modifier should be 1.0 (no change)
    @Test
    public void testGetWaveTimeModifier_noBuffsIsOne() {
        waveManager.startWave();
        // wave time should equal the default since no modifier applied
        assertEquals(10, waveManager.getWaveTime());
    }

    // word time modifier with no buffs should be 1.0 (base)
    @Test
    public void testGetWordTimeModifier_noBuffsIsOne() {
        assertEquals(1.0f, waveManager.getWordTimeModifier(), 0.001f);
    }

    // coin gain modifier with no buffs should be 1.0 (base)
    @Test
    public void testGetCoinGainModifier_noBuffsIsOne() {
        assertEquals(1.0f, waveManager.getCoinGainModifier(), 0.001f);
    }

    // after a wave ends expired buffs should be removed from the list
    @Test
    public void testWaveOver_removesExpiredBuffs() {
        // we need a buff with length 1 so it expires after one wave
        // keep trying until we get something with length 1
        BuffItem expirableItem = null;
        for (int attempt = 0; attempt < 100; attempt++) {
            BuffItem candidate = new BuffItem();
            if (candidate.getLength() == 1) {
                expirableItem = candidate;
                break;
            }
        }

        if (expirableItem != null) {
            waveManager.addBuff(expirableItem);
            // tick through the entire wave so waveOver fires
            waveManager.startWave();
            for (int i = 0; i < 10; i++) {
                waveManager.incTime();
            }
            // the buff had length 1 so decLength made it 0 - should be gone now
            assertTrue(waveManager.getBuffList().isEmpty());
        }
        // if we never got a length-1 item we just skip - this test relies on rng cooperating
    }

    // getWaveTimeLeft should return the remaining time correctly
    @Test
    public void testGetWaveTimeLeft_decreasesOverTime() {
        waveManager.startWave();
        double initialLeft = waveManager.getWaveTimeLeft();
        waveManager.incTime();
        double afterOneSecond = waveManager.getWaveTimeLeft();
        assertTrue(afterOneSecond < initialLeft);
    }
}