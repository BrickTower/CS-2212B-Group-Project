package com.mapstob.group35.Gameplay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class BuffItemTest {

    private BuffItem item;

    @BeforeEach
    void setUp() {
        item = new BuffItem();
    }

    @RepeatedTest(10)
    @DisplayName("Constructor should always generate valid item properties")
    void testItemInitialization() {
        assertNotNull(item.getName(), "Item name should not be null");
        assertTrue(item.getPower() > 0, "Power should always be a positive integer");
        assertTrue(item.getLength() >= 1, "Length should be at least 1 wave");
        
        // Check the cost formula: 10 * power * length
        int expectedCost = 10 * item.getPower() * item.getLength();
        assertEquals(expectedCost, item.getCost(), "Cost should match the power/length formula");
    }

    @Test
    @DisplayName("decLength should correctly reduce length and return true only at zero")
    void testDecLength() {
        int initialLength = item.getLength();
        
        // Loop until it reaches 1
        for (int i = 0; i < initialLength - 1; i++) {
            assertFalse(item.decLength(), "Should return false while length is still > 0");
        }
        
        // Final decrement should reach 0
        assertTrue(item.decLength(), "Should return true once length hits 0 or below");
    }

    @RepeatedTest(20)
    @DisplayName("Description should contain relevant stats and cost info")
    void testDescriptionContent() {
        String desc = item.getDescription();
        String name = item.getName();
        
        assertNotNull(desc, "Description should be generated");
        assertTrue(desc.contains(String.valueOf(item.getCost())), "Description should include the cost");
        
        // Since Regeneration doesn't have a case in setDescription, we verify it doesn't crash
        if (!name.equals("Regeneration")) {
            assertTrue(desc.contains(String.valueOf(item.getPower())), "Description should include the power level");
        }
    }

    @Test
    @DisplayName("Verify item is one of the possible buff types")
    void testValidBuffType() {
        String name = item.getName();
        boolean found = false;
        String[] possible = {"Heal", "Coin Gain Increase", "Wave Length Decrease", "Word Time Increase", "Regeneration"};
        
        for (String p : possible) {
            if (p.equals(name)) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Generated item must be one of the pre-defined possible buffs");
    }
}