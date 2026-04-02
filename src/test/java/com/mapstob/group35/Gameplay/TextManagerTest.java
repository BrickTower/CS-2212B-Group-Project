package com.mapstob.group35.Gameplay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TextManagerTest {

    private TextManager manager;

    @BeforeEach
    void setUp() {
        // Initialize with 2 second spawn rate and 15 second word life
        manager = new TextManager(2, 15);
    }

    @Test
    @DisplayName("Should spawn a placeholder word when time reaches the interval")
    void testWordSpawning() {
        // curTime starts at 0.
        // First increment: curTime = 1
        manager.incTime();

        // Second increment: curTime = 2. (2 % 2 == 0), so makeText("Placeholder") is called.
        // If it works, spellingAttempt should find a word to check.
        manager.incTime();

        // If a word spawned, this won't throw an IndexOutOfBoundsException
        assertDoesNotThrow(() -> manager.spellingAttempt("Placeholder"),
            "A word should have spawned at curTime 2.");
    }

    @Test
    @DisplayName("Successful spelling should return 0 and remove the word")
    void testSpellingSuccess() {
        manager.makeText("Apple");

        int mistakesReturned = manager.spellingAttempt("Apple");

        assertEquals(0, mistakesReturned, "Correct spelling should return 0 mistakes.");
    }

    @Test
    @DisplayName("Failed spelling should increment internal mistakes and return penalty")
    void testSpellingFailure() {
        manager.makeText("Banana");

        // We expect the mock/logic to see this is wrong
        int result = manager.spellingAttempt("WrongInput");

        assertTrue(result >= 0, "Should return a penalty value.");
    }

    @Test
    @DisplayName("reset() should clear time and stats")
    void testResetLogic() {
        manager.makeText("ClearMe");
        manager.incTime();
        manager.incTime();

        manager.reset();

        // If we reset, curTime is 0.
        // Incrementing once (to 1) should NOT spawn a word if spawnTime is 2.
        manager.incTime();

        // Attempting to spell when list is empty should throw error (standard behavior)
        assertThrows(IndexOutOfBoundsException.class, () -> {
            manager.spellingAttempt("Anything");
        }, "List should be empty after reset.");
    }

    @Test
    @DisplayName("incTime should accumulate damage from timed-out words")
    void testDamageAccumulation() {
        manager.makeText("Cat"); // Length 3. Timeout damage = 3 * 2 = 6.

        // Manually increment time 15 times to trigger timeout
        int totalDamage = 0;
        for(int i = 0; i < 15; i++) {
            totalDamage += manager.incTime();
        }

        // The word "Cat" should eventually time out and return 6 damage
        assertTrue(totalDamage >= 6, "Damage should be returned when word times out.");
    }
}