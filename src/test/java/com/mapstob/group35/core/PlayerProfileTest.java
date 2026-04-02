package com.mapstob.group35.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerProfileTest {

    private PlayerProfile player;
    private final String TEST_USER = "EthanG";
    private final String TEST_PASS = "secure123";

    @BeforeEach
    void setUp() {
        player = new PlayerProfile(TEST_USER, TEST_PASS);
    }

    @Test
    @DisplayName("Constructor should initialize fields correctly")
    void testConstructor() {
        assertEquals(TEST_USER, player.getUserName());
        assertEquals(TEST_PASS, player.getPassword());
        assertEquals(1, player.getLevel(), "New players should start at level 1");
        assertEquals(0, player.getCoins(), "New players should start with 0 coins");
        assertNotNull(player.getPlayerStats(), "Stats object should be initialized");
    }

    @Test
    @DisplayName("addCoins should increase coin balance")
    void testAddCoinsSuccess() {
        player.addCoins(50);
        assertEquals(50, player.getCoins());
        player.addCoins(10);
        assertEquals(60, player.getCoins());
    }

    @Test
    @DisplayName("addCoins should throw exception for zero or negative values")
    void testAddCoinsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> player.addCoins(0), 
            "Should throw for 0 coins");
        assertThrows(IllegalArgumentException.class, () -> player.addCoins(-10), 
            "Should throw for negative coins");
    }

    @Test
    @DisplayName("spendCoins should return true and subtract balance if affordable")
    void testSpendCoinsSuccess() {
        player.addCoins(100);
        boolean result = player.spendCoins(40);
        assertTrue(result, "Should return true when user has enough coins");
        assertEquals(60, player.getCoins());
    }

    @Test
    @DisplayName("spendCoins should return false if balance is too low")
    void testSpendCoinsInsufficientFunds() {
        player.addCoins(10);
        boolean result = player.spendCoins(50);
        assertFalse(result, "Should return false when funds are insufficient");
        assertEquals(10, player.getCoins(), "Balance should not change on failed purchase");
    }

    @Test
    @DisplayName("levelUp and resetLevel should modify difficulty level")
    void testLevelLogic() {
        player.levelUp();
        assertEquals(2, player.getLevel());
        player.levelUp();
        assertEquals(3, player.getLevel());
        
        player.resetLevel();
        assertEquals(1, player.getLevel());
    }

    @Test
    @DisplayName("resetStats should replace the current stats object")
    void testResetStats() {
        Stats initialStats = player.getPlayerStats();
        player.resetStats();
        Stats newStats = player.getPlayerStats();
        
        assertNotNull(newStats);
        assertNotSame(initialStats, newStats, "resetStats should create a new Stats instance");
    }
}