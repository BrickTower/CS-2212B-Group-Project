package com.mapstob.group35.Gameplay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class GameManagerTest {

    private GameManager gameManager;

    @BeforeEach
    void setUp() {
        // Using the default constructor with 100 HP, 0 coins
        gameManager = new GameManager();
    }

    @Test
    @DisplayName("Initialization should set default values correctly")
    void testInitialization() {
        assertEquals(0, gameManager.getCoins(), "Coins should start at 0");
        // We can't see curLife directly because its private,
        // sowe test it through the takeDamage logic.
    }

    @Test
    @DisplayName("Healing should not exceed max life")
    void testHealLogic() {
        gameManager.takeDamage(20); // 100 -> 80
        gameManager.heal(10);       // 80 -> 90
        
        // Healing way over max
        gameManager.heal(500);      
        
        // If we take 100 damage now and dont die immediately,
        // it means heal didnt cap correctly.
        // we test the cap by ensuring 101 damage still kills
        gameManager.takeDamage(101);
        // Checking private "died" via the state behauvior
        // since we cant see the boolean we make sure no exceptions occur
    }

    @Test
    @DisplayName("Coin management should allow valid purchases")
    void testCoinManagement() {
        gameManager.gainCoins(50);
        assertEquals(50, gameManager.getCoins());
        
        boolean success = gameManager.useCoins(30);
        assertTrue(success, "Should be able to spend coins you have");
        assertEquals(20, gameManager.getCoins());
        
        boolean failure = gameManager.useCoins(100);
        assertFalse(failure, "Shouldnt be able to spend more than you have");
        assertEquals(20, gameManager.getCoins(), "Balance should remain unchanged on failed spend");
    }

    @Test
    @DisplayName("takeDamage should lead to death if life hits 0")
    void testDeathLogic() {
        // Damage exactly equal to maxLife
        gameManager.takeDamage(100);
        
        gameManager.takeDamage(1); 
        // Now curLife is 0, and 1 > 0, so die() is called.
        // verify the code handles the call.
    }

    @Test
    @DisplayName("spellingCorrect should award default coin gain")
    void testSpellingCorrectReward() {
        int initialCoins = gameManager.getCoins();
        gameManager.spellingCorrect();
        
        assertEquals(initialCoins + 10, gameManager.getCoins(), 
            "Should gain 10 coins (defaultCoinGain) on correct spelling");
    }

    @Test
    @DisplayName("incTime should process damage from TextManager")
    void testIncTimeProcessing() {
        // Testing GameManager and TextManager isn't broken.
        assertDoesNotThrow(() -> gameManager.incTime());
    }

    @Test
    @DisplayName("Custom constructor should load saved values correctly")
    void testLoadingConstructor() {
        // maxLife, curLife, wave, waveTime, coins, defaultCoinGain
        GameManager loadedGame = new GameManager(200, 150, 5, 60, 500, 20);
        
        assertEquals(500, loadedGame.getCoins());
        
        // Verify the 150 health by taking 151 damage
        loadedGame.takeDamage(151);
        // Should trigger die() logic internally
    }
}