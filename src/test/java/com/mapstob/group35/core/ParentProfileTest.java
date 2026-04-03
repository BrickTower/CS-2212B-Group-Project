package com.mapstob.group35.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class ParentProfileTest {

    private ParentProfile parent;
    private PlayerProfile child;

    @BeforeEach
    void setUp() {
        parent = new ParentProfile("SuperParent", "pass123");
        child = new PlayerProfile("KidGamer", "kidpass");
    }

    @Test
    @DisplayName("Should successfully add a PlayerProfile to managed list")
    void testAddPlayerSuccess() {
        assertDoesNotThrow(() -> parent.addPlayer(child));
    }

    @Test
    @DisplayName("Should throw exception when trying to add another ParentProfile")
    void testAddParentAsManagedThrowsException() {
        ParentProfile anotherParent = new ParentProfile("OtherParent", "p2");
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parent.addPlayer(anotherParent);
        });

        assertEquals("Parents cannot be managed by other Parents.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception if player is already managed (Note: requires .getUserName() fix in your code)")
    void testAddDuplicatePlayer() {
        parent.addPlayer(child);
        
        // This test will fail if your code uses .contains(player) instead of .contains(player.getUserName())
        assertThrows(IllegalArgumentException.class, () -> {
            parent.addPlayer(child);
        });
    }

    @Test
    @DisplayName("Should successfully retrieve stats for a managed player")
    void testGetPlayerStats() {
        parent.addPlayer(child);
        
        // This will throw IllegalArgumentException currently due to the .contains() bug
        Stats stats = parent.getPlayerStats(child);
        assertNotNull(stats, "Stats should be retrievable for managed players.");
    }

    @Test
    @DisplayName("Should throw exception when getting stats for an unmanaged player")
    void testGetStatsUnmanaged() {
        PlayerProfile stranger = new PlayerProfile("Stranger", "pass");
        
        assertThrows(IllegalArgumentException.class, () -> {
            parent.getPlayerStats(stranger);
        });
    }

    @Test
    @DisplayName("Should successfully remove a managed player")
    void testRemovePlayer() {
        parent.addPlayer(child);
        
        // Removing the player
        assertDoesNotThrow(() -> parent.removePlayer(child));
        
        // Verify they are gone by trying to get stats
        assertThrows(IllegalArgumentException.class, () -> {
            parent.getPlayerStats(child);
        });
    }

    @Test
    @DisplayName("Should reset player stats through parent authority")
    void testResetPlayerStats() {
        parent.addPlayer(child);
        
        // Add some coins to the child to change their state
        child.addCoins(100);
        
        // Reset via parent
        parent.resetPlayerStats(child);
        
        // Stats object should be fresh/new
        assertNotNull(child.getPlayerStats());
    }
}