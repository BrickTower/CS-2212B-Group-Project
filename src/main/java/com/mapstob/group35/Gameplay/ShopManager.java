package com.mapstob.group35.Gameplay;

import java.util.ArrayList;

public class ShopManager {
    private GameManager gameManager;
    private ArrayList<BuffItem> shopItems;

    public ShopManager(GameManager gameManager) {
        this.gameManager = gameManager;
        shopItems = new ArrayList<>();
        resetShopItems();
    }

    public BuffItem createShopItem(){
        return new BuffItem();
    }

    public void resetShopItems()
    {
        shopItems.clear();
        shopItems.add(createShopItem());
        shopItems.add(createShopItem());
        shopItems.add(createShopItem());
    }

    public void DisplayShopItem(){
        //TODO
    }


    public boolean Buyable(BuffItem shopItem){
        return gameManager.useCoins(shopItem.getCost());
    }

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
