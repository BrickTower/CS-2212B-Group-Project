package com.mapstob.group35.Gameplay;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * tests for BuffItem
 * since buff type and power are randomly assigned we run some tests repeatedly
 * to catch edge cases that might only show up on certain random values
 */
public class BuffItemTest {

    // valid names the item can randomly pick from
    private static final String[] VALID_NAMES = {
        "Heal", "Coin Gain Increase", "Wave Length Decrease", "Word Time Increase", "Regeneration"
    };

    // just make sure constructing a buff doesnt blow up
    @Test
    public void testConstructor_doesNotThrow() {
        assertDoesNotThrow(() -> new BuffItem());
    }

    // name should always be one of the five valid buff types
    @RepeatedTest(20)
    public void testGetName_isValidBuffType() {
        BuffItem item = new BuffItem();
        String name = item.getName();
        boolean valid = false;
        for (String validName : VALID_NAMES) {
            if (validName.equals(name)) {
                valid = true;
                break;
            }
        }
        assertTrue(valid, "unexpected buff name: " + name);
    }

    // cost should always be positive - it would be weird to have a free or negative cost item
    @RepeatedTest(20)
    public void testGetCost_isPositive() {
        BuffItem item = new BuffItem();
        assertTrue(item.getCost() > 0, "cost should be positive but got: " + item.getCost());
    }

    // power should always be at least 1 no matter what buff type we get
    @RepeatedTest(20)
    public void testGetPower_isAtLeastOne() {
        BuffItem item = new BuffItem();
        assertTrue(item.getPower() >= 1, "power should be at least 1 but got: " + item.getPower());
    }

    // length should always be at least 1 - heal uses length 1, others can be longer
    @RepeatedTest(20)
    public void testGetLength_isAtLeastOne() {
        BuffItem item = new BuffItem();
        assertTrue(item.getLength() >= 1, "length should be at least 1 but got: " + item.getLength());
    }

    // description should never be null or empty
    @RepeatedTest(20)
    public void testGetDescription_isNotEmpty() {
        BuffItem item = new BuffItem();
        String desc = item.getDescription();
        assertNotNull(desc);
        assertFalse(desc.isEmpty(), "description should not be empty");
    }

    // decLength should reduce length by 1 and return false while there are uses remaining
    @Test
    public void testDecLength_returnsFalseWhenNotExpired() {
        // we need a buff with length > 1 so we keep trying until we get one
        // heal is always length 1 so we might need a few tries
        BuffItem item = null;
        for (int attempt = 0; attempt < 100; attempt++) {
            item = new BuffItem();
            if (item.getLength() > 1) break;
        }
        assertNotNull(item);
        if (item.getLength() > 1) {
            boolean used = item.decLength();
            assertFalse(used); // should not be expired yet
        }
        // if we only got heals we skip silently - thats just bad luck
    }

    // decLength on an item with length 1 should return true (item is now expired)
    @Test
    public void testDecLength_returnsTrueWhenExpired() {
        // keep creating until we get a heal which always starts at length 1
        BuffItem item = null;
        for (int attempt = 0; attempt < 100; attempt++) {
            item = new BuffItem();
            if (item.getLength() == 1) break;
        }
        assertNotNull(item);
        if (item.getLength() == 1) {
            boolean expired = item.decLength();
            assertTrue(expired);
        }
    }

    // cost formula is 10 * power * length so verify it lines up
    @RepeatedTest(20)
    public void testCost_matchesPowerAndLength() {
        BuffItem item = new BuffItem();
        int expectedCost = 10 * item.getPower() * item.getLength();
        assertEquals(expectedCost, item.getCost());
    }

    // description content should mention the buff name or relevant info
    @RepeatedTest(20)
    public void testDescription_containsRelevantInfo() {
        BuffItem item = new BuffItem();
        String desc = item.getDescription();
        // all descriptions mention either hp, coins, wave, time, or regeneration
        boolean mentionsSomethingUseful =
            desc.contains("HP") ||
            desc.contains("coins") ||
            desc.contains("wave") ||
            desc.contains("word") ||
            desc.contains("Regenerate") ||
            desc.contains("Heals");
        assertTrue(mentionsSomethingUseful, "description didnt mention anything recognizable: " + desc);
    }
}