package com.mapstob.group35.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * tests for loggedUser
 * this class is basically a global session holder - we test that it stores and retrieves correctly
 * note: currUser is static so tests can bleed into each other - order matters here
 * each test resets state via setCurrUser to stay clean
 */
public class loggedUserTest {

    private loggedUser loggedUser;
    private PlayerProfile player;
    private ParentProfile parent;

    // fresh instance each time - but remember the field is static so state persists across tests
    @BeforeEach
    public void setUp() {
        loggedUser = new loggedUser();
        player = new PlayerProfile("playerUser", "pass");
        parent = new ParentProfile("parentUser", "pass");
    }

    // setting a player and getting it back should return the same object
    @Test
    public void testSetAndGetCurrUser_playerProfile() {
        loggedUser.setCurrUser(player);
        PlayerProfile result = loggedUser.getCurrUser();
        assertEquals(player, result);
    }

    // setting a parent and getting it back should return it cast as parentprofile
    @Test
    public void testSetAndGetCurrUser_parentProfile() {
        loggedUser.setCurrUser(parent);
        PlayerProfile result = loggedUser.getCurrUser();
        assertTrue(result instanceof ParentProfile);
    }

    // when a parent is stored it should come back as a parentprofile not just a playerprofile
    @Test
    public void testGetCurrUser_returnsParentCastCorrectly() {
        loggedUser.setCurrUser(parent);
        PlayerProfile result = loggedUser.getCurrUser();
        assertNotNull(result);
        assertEquals("parentUser", result.getUserName());
    }

    // overwriting the user should replace the previous one
    @Test
    public void testSetCurrUser_overwritesPreviousUser() {
        loggedUser.setCurrUser(player);
        loggedUser.setCurrUser(parent);
        PlayerProfile result = loggedUser.getCurrUser();
        assertTrue(result instanceof ParentProfile);
        assertEquals("parentUser", result.getUserName());
    }

    // since currUser is static all instances of loggedUser share the same user
    @Test
    public void testStaticField_sharedAcrossInstances() {
        loggedUser instance1 = new loggedUser();
        loggedUser instance2 = new loggedUser();

        instance1.setCurrUser(player);
        PlayerProfile result = instance2.getCurrUser();

        // both instances see the same player because the field is static
        assertEquals(player, result);
    }

    // a regular player set via setCurrUser should not come back as a parent
    @Test
    public void testGetCurrUser_plainPlayerIsNotParent() {
        loggedUser.setCurrUser(player);
        PlayerProfile result = loggedUser.getCurrUser();
        assertFalse(result instanceof ParentProfile);
    }
}