package com.mapstob.group35.Gameplay;

import com.mapstob.group35.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
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

    @FXML protected void EXIT_BUTTON(){
        try {
            SceneManager.loadGame(gameManager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setGameManager(GameManager gameManager) {this.gameManager = gameManager;}
    public void initShop(){
        shopItems = new ArrayList<>();
        resetShopItems();
        DisplayCoins();
    }
    /**
     * Creates a shop item.
     * @return Shop item
     */
    private BuffItem createShopItem(){
        return new BuffItem();
    }

    /**
     * Resets all shop items and makes new ones.
     */
    private void resetShopItems()
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
    private boolean Buyable(BuffItem shopItem){
        return gameManager.useCoins(shopItem.getCost());
    }

    /**
     * Buyable should precede this to check if and buy the item.
     * Does the functional part of recieving what you bought.
     * @param shopItem
     */
    private void buyShopItem(BuffItem shopItem){
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
    private void DisplayShopItems(){
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
    private void DisplayCoins(){
        COINS_TEXT.setText("COINS: "+ Integer.toString(gameManager.getCoins()));
    }

    @FXML protected void CLICKED_1(){
        System.out.println("Attempt buy 1");
        if (Buyable( shopItems.get(0) )){
            buyShopItem(shopItems.get(0));
        }
    }
    @FXML protected void CLICKED_2(){
        System.out.println("Attempt buy 2");
        if (Buyable( shopItems.get(1) )){
            buyShopItem(shopItems.get(1));
        }
    }
    @FXML protected void CLICKED_3(){
        System.out.println("Attempt buy 3");
        if (Buyable( shopItems.get(2) )){
            buyShopItem(shopItems.get(2));
        }
    }
}
