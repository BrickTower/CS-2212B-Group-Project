package com.mapstob.group35.Gameplay;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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

    @FXML private Text COINS_TEXT;

    //Better way to do this? yes, probably. Will i? no.
    @FXML private Text NAME_1;
    @FXML private Text COST_1;
    @FXML private Text DESC_1;

    @FXML private Text NAME_2;
    @FXML private Text COST_2;
    @FXML private Text DESC_2;

    @FXML private Text NAME_3;
    @FXML private Text COST_3;
    @FXML private Text DESC_3;

    /**
     * Constructor.
     */
    public ShopManager() {
        shopItems = new ArrayList<>();
        resetShopItems();
    }
    public void setGameManager(GameManager gameManager) {this.gameManager = gameManager;}

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
        DisplayShopItems();
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
        DisplayCoins();

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


    /**
     * Display shop items in GUI.
     */
    public void DisplayShopItems(){
        BuffItem item = shopItems.get(0);
        NAME_1.setText(item.getName());
        COST_1.setText( "COST: " +  Integer.toString(item.getCost()) + " COINS");
        DESC_1.setText(item.getDescription());

        item = shopItems.get(1);
        NAME_2.setText(item.getName());
        COST_2.setText( "COST: " +  Integer.toString(item.getCost()) + " COINS");
        DESC_2.setText(item.getDescription());

        item = shopItems.get(2);
        NAME_3.setText(item.getName());
        COST_3.setText( "COST: " +  Integer.toString(item.getCost()) + " COINS");
        DESC_3.setText(item.getDescription());

    }
    /**
     * Displays coin amount in GUI.
     */
    public void DisplayCoins(){
        COINS_TEXT.setText("COINS: "+ Integer.toString(gameManager.getCoins()));
    }

}
