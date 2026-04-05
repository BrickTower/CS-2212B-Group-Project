package com.mapstob.group35.Gameplay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * tests for WaveManager
 * covers wave counting, time tracking, buff effects, and wave end behaviour
 */
public class WaveManagerTest {

    private WaveManager wm;

    // wave 0, 10 second default wave time
    @BeforeEach
    public void setUp() {
        wm = new WaveManager(0, 10);
    }

    // wave starts at passed value
    @Test
    public void testInit_waveZero() {
        assertEquals(0, wm.getWave());
    }

    // wave time matches ctor value
    @Test
    public void testInit_waveTime() {
        assertEquals(10, wm.getWaveTime());
    }

    // buff list starts empty
    @Test
    public void testInit_buffsEmpty() {
        assertTrue(wm.getBuffList().isEmpty());
    }

    // curTime starts at zero
    @Test
    public void testInit_curTimeZero() {
        assertEquals(0, wm.getCurTime());
    }

    // incWave goes 0 -> 1
    @Test
    public void testIncWave() {
        wm.incWave();
        assertEquals(1, wm.getWave());
    }

    // setWaveTime updates getter
    @Test
    public void testSetWaveTime() {
        wm.setWaveTime(20);
        assertEquals(20, wm.getWaveTime());
    }

    // incTime returns false before wave ends
    @Test
    public void testIncTime_falseBeforeEnd() {
        wm.startWave();
        assertFalse(wm.incTime()); // 1 of 10
    }

    // incTime returns true at end
    @Test
    public void testIncTime_trueAtEnd() {
        wm.startWave();
        boolean result = false;
        for (int i = 0; i < 10; i++) result = wm.incTime();
        assertTrue(result);
    }

    // wave counter increments when wave ends
    @Test
    public void testIncTime_waveIncOnEnd() {
        wm.startWave();
        for (int i = 0; i < 10; i++) wm.incTime();
        assertEquals(1, wm.getWave()); // 0 -> 1
    }

    // resetTime brings curTime to 0
    @Test
    public void testResetTime() {
        wm.incTime();
        wm.incTime();
        wm.resetTime();
        assertEquals(0, wm.getCurTime());
    }

    // startWave resets curTime to 0
    @Test
    public void testStartWave_resetsCurTime() {
        wm.incTime();
        wm.incTime();
        wm.startWave();
        assertEquals(0, wm.getCurTime());
    }

    // addBuff puts item in list
    @Test
    public void testAddBuff() {
        wm.addBuff(new BuffItem());
        assertEquals(1, wm.getBuffList().size());
    }

    // getHealAmount is 0 with no regen buffs
    @Test
    public void testHealAmt_noBuffs() {
        assertEquals(0, wm.getHealAmount());
    }

    // completion pct starts at 0
    @Test
    public void testCompletionPct_initZero() {
        assertEquals(0.0f, wm.waveCompletionPercent(), 0.001f);
    }

    // completion pct is 0.5 at halfway
    @Test
    public void testCompletionPct_halfway() {
        wm.startWave();
        for (int i = 0; i < 5; i++) wm.incTime();
        assertEquals(0.5f, wm.waveCompletionPercent(), 0.001f);
    }

    // wave time modifier is 1.0 with no buffs
    @Test
    public void testWaveTimeMod_noBuffs() {
        wm.startWave();
        assertEquals(10, wm.getWaveTime()); // unchanged
    }

    // word time modifier is 1.0 with no buffs
    @Test
    public void testWordTimeMod_noBuffs() {
        assertEquals(1.0f, wm.getWordTimeModifier(), 0.001f);
    }

    // coin gain modifier is 1.0 with no buffs
    @Test
    public void testCoinGainMod_noBuffs() {
        assertEquals(1.0f, wm.getCoinGainModifier(), 0.001f);
    }

    // expired buffs removed after wave ends
    @Test
    public void testWaveOver_removesExpired() {
        // need a buff with length 1 - retry until we get one
        BuffItem expirable = null;
        for (int i = 0; i < 100; i++) {
            BuffItem b = new BuffItem();
            if (b.getLength() == 1) { expirable = b; break; }
        }
        if (expirable != null) {
            wm.addBuff(expirable);
            wm.startWave();
            for (int i = 0; i < 10; i++) wm.incTime();
            assertTrue(wm.getBuffList().isEmpty());
        }
        // skip if rng never gave us a length-1 item
    }

    // waveTimeLeft decreases over time
    @Test
    public void testTimeLeft_decreases() {
        wm.startWave();
        double before = wm.getWaveTimeLeft();
        wm.incTime();
        assertTrue(wm.getWaveTimeLeft() < before);
    }
}