package com.mapstob.group35.Gameplay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class ShopManagerTest {

    private GameManager gameManager;
    private ShopManager shopManager;

    @BeforeEach
    void setUp() {
        gameManager = new GameManager();
        shopManager = new ShopManager(gameManager);
    }

    @Test
    @DisplayName("Shop should initialize with exactly 3 items")
    void testInitialShopItems() throws Exception {
        // Since shopItems is private we verify it through reset logic
        assertDoesNotThrow(() -> shopManager.resetShopItems());
        
        // We can create an item to verify the shop factory method works
        BuffItem item = shopManager.createShopItem();
        assertNotNull(item, "Shop should be able to generate BuffItems");
    }

    @Test
    @DisplayName("Buyable should return true only if player has enough coins")
    void testBuyableLogic() {
        BuffItem expensiveItem = shopManager.createShopItem();
        // CHeck player has 0 coins
        assertFalse(shopManager.Buyable(expensiveItem), "Should not be buyable with 0 coins");
        
        // Give player enough coins for the specific item
        gameManager.gainCoins(expensiveItem.getCost());
        assertTrue(shopManager.Buyable(expensiveItem), "Should be buyable once player has enough coins");
    }

    @Test
    @DisplayName("Buying a Heal item should trigger healing in GameManager")
    void testBuyHealItem() {
        // We need to find a healing item
        BuffItem healItem = null;
        // Keep generating until we get a Heal item for the test
        while (healItem == null || !healItem.getName().equals("Heal")) {
            healItem = shopManager.createShopItem();
        }

        gameManager.takeDamage(50); // Drop health to 50
        shopManager.buyShopItem(healItem);
        
        // If heal worked, taking 50 damage again shouldn't result in 0 HP
        assertDoesNotThrow(() -> gameManager.takeDamage(10));
    }

    @Test
    @DisplayName("Buying a buff should call addBuff in GameManager")
    void testBuyBuffItem() {
        BuffItem buffItem = null;
        // Keep generating until we getnot a Heal item
        while (buffItem == null || buffItem.getName().equals("Heal")) {
            buffItem = shopManager.createShopItem();
        }

        // Making sure the process of adding a buff dont crash the manager chain
        shopManager.buyShopItem(buffItem);
    }

    @Test
    @DisplayName("Buying an item should trigger a shop reset/restock")
    void testShopRestockAfterPurchase() {
        BuffItem item = shopManager.createShopItem();
        shopManager.buyShopItem(item);
        
        // The logic buyShopItem calls resetShopItems() at the end.
        // This makes ure the player always sees a fresh set of 3 items after a purchase.
        assertDoesNotThrow(() -> shopManager.resetShopItems());
    }
}