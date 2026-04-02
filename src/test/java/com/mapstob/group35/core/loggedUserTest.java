package com.mapstob.group35.core;

import com.mapstob.group35.core.ParentProfile;
import com.mapstob.group35.core.PlayerProfile;
import com.mapstob.group35.core.loggedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the loggedUser class using actual Profile implementations.
 */
public class loggedUserTest {

    private loggedUser registry;

    @BeforeEach
    void setUp() {
        registry = new loggedUser();
        // since currUser is static, we must manually reset it to ensure com.mapstob.group35.test isolation
        registry.setCurrUser(null);
    }

    @Test
    @DisplayName("Should store and retrieve a standard PlayerProfile")
    void testSetAndGetPlayerProfile() {
        PlayerProfile player = new PlayerProfile("EthanG", "pass123");
        registry.setCurrUser(player);

        PlayerProfile retrieved = registry.getCurrUser();
        
        assertNotNull(retrieved);
        assertEquals("EthanG", retrieved.getUserName());
        assertEquals(1, retrieved.getLevel(), "New player should start at level 1");
        assertFalse(retrieved instanceof ParentProfile, "Standard player should not be a ParentProfile");
    }

    @Test
    @DisplayName("Should store and retrieve a ParentProfile and maintain its type")
    void testSetAndGetParentProfile() {
        ParentProfile parent = new ParentProfile("ParentUser", "securePass");
        registry.setCurrUser(parent);

        PlayerProfile retrieved = registry.getCurrUser();

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof ParentProfile, "Should be recognized as ParentProfile");
        assertEquals("ParentUser", retrieved.getUserName());
    }

    @Test
    @DisplayName("Static field should persist across different instances of loggedUser")
    void testStaticPersistence() {
        PlayerProfile player = new PlayerProfile("PersistentUser", "p1");
        
        // Set using instance A
        new loggedUser().setCurrUser(player);

        // Retrieve using instance B
        loggedUser instanceB = new loggedUser();
        assertEquals("PersistentUser", instanceB.getCurrUser().getUserName());
    }

    @Test
    @DisplayName("Should return null when no user is logged in")
    void testNullHandling() {
        registry.setCurrUser(null);
        assertNull(registry.getCurrUser());
    }

    @Test
    @DisplayName("Should correctly overwrite an existing logged-in user")
    void testOverwriteUser() {
        PlayerProfile user1 = new PlayerProfile("User1", "p1");
        ParentProfile user2 = new ParentProfile("User2", "p2");

        registry.setCurrUser(user1);
        assertEquals("User1", registry.getCurrUser().getUserName());

        registry.setCurrUser(user2);
        assertEquals("User2", registry.getCurrUser().getUserName());
        assertTrue(registry.getCurrUser() instanceof ParentProfile);
    }

    @Test
    @DisplayName("Polymorphic behavior: Parent acting as Player")
    void testParentAsPlayer() {
        // a ParentProfile is a PlayerProfile so it should be able to level up
        // even when retrieved through the loggedUser registry
        ParentProfile parent = new ParentProfile("GamingParent", "p123");
        registry.setCurrUser(parent);
        
        registry.getCurrUser().levelUp();
        
        assertEquals(2, registry.getCurrUser().getLevel(), "Parent should be able to level up like a player");
    }
}