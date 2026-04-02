package com.mapstob.group35.Gameplay;

import java.util.ArrayList;

/**
 * Manages shop functionality. Creates BuffItems and displays them.
 * When an item is bought, it passes it along and resets the shop with new items.
 * @author Shaun
 * @see BuffItem
 */
public class ShopManager {
    private GameManager gameManager;
    private ArrayList<BuffItem> shopItems;

    /**
     * Constructor. Requires a reference to Gamemanager to function as it uses coins.
     * @param gameManager
     */
    public ShopManager(GameManager gameManager) {
        this.gameManager = gameManager;
        shopItems = new ArrayList<>();
        resetShopItems();
    }

    /**
     * Creates a shop item.
     * @return Shop item
     */
    public BuffItem createShopItem(){
        return new BuffItem();
    }

    /**
     * Resets all shop items and makes new ones.
     */
    public void resetShopItems()
    {
        shopItems.clear();
        shopItems.add(createShopItem());
        shopItems.add(createShopItem());
        shopItems.add(createShopItem());
    }

    /**
     * Display code.
     */
    public void DisplayShopItem(){
        //TODO
    }

    /**
     * Checks if the item is buyable, and if it is, uses the coins to buy it. Should be followed up with buyShopItem.
     * @param shopItem
     * @return if the item is bought
     */
    public boolean Buyable(BuffItem shopItem){
        return gameManager.useCoins(shopItem.getCost());
    }

    /**
     * Buyable should precede this to check if and buy the item.
     * Does the functional part of recieving what you bought.
     * @param shopItem
     */
    public void buyShopItem(BuffItem shopItem){
        switch (shopItem.getName()) {
            case "Heal" -> {
                gameManager.heal(shopItem.getPower());
            }
            case "Coin Gain Increase", "Regeneration", "Word Time Increase", "Wave Length Decrease" -> {
                gameManager.addBuff(shopItem);
            }
        }

        resetShopItems();
    }

}
