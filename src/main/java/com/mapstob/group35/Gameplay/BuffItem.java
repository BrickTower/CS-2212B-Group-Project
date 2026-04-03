package com.mapstob.group35.Gameplay;


/**
 * Instantiates itself with a random buff type, power, and duration.
 * Both WaveManager and ShopManager use this information for either display or use.
 *
 * @author Shaun
 * @see ShopManager
 * @see WaveManager
 */

public class BuffItem {
    static private String[] possibleBuffs = {
            "Heal","Coin Gain Increase", "Wave Length Decrease","Word Time Increase","Regeneration"
    };//Ideally this would be stored in a file but oh well hardcoding it is

    private String name;
    private int cost;
    private int power;
    private int length;
    private String description;

    /**
     * Constructor
     * Automatically chooses its own buff type and power/duration.
     */
    public BuffItem() {
        determineItem();
        setItemInfo(name);
        cost = 10 * power * length;
        setDescription(name);
    }

    /**
     * Get name of buff. When checking buff type, it will check the name.
     * @return name
     */
    public String getName() {return name;}

    /**
     * Get cost of item.
     * @return cost
     */
    public int getCost() {return cost;}

    /**
     * Get power of item.
     * @return power
     */
    public int getPower() {return power;}

    /**
     * Get duration of item. Instant use items, like heal, will have a value of one.
     * @return duration
     */
    public int getLength() {return length;}

    /**
     * Get description of item. Only used in shop for display purposes.
     * @return description
     */
    public String getDescription() {return description;}

    /**
     * Decrements length. Should only be done by WaveManager.
     * @return If item is used up
     */
    public boolean decLength(){
        length --;
        return length <= 0;
    }


    private void determineItem(){
        int randomItemIndex = (int)(Math.random() * possibleBuffs.length);
        name = possibleBuffs[randomItemIndex];
    }

    private void setItemInfo(String name){
        switch (name) {
            case "Heal" -> {
                power = (int)(5 + Math.random() * 11) * 2;
                length = 1;
            }
            case "Coin Gain Increase", "Word Time Increase", "Wave Length Decrease" -> {
                power = (int)(5+ Math.random() * 21);// 5-20 rand
                length = (int)(1 + Math.random() * 10);
            }
            case "Regeneration" -> {
                power = (int)(1 + Math.random() * 6);
                length = (int)(1 + Math.random() * 10);
            }
        }
    }


    private void setDescription(String name){
        description = "";
        switch (name) {
            case "Heal" ->{
                description = "Heals " + Integer.toString(power) + " HP immediately";
            }
            case "Coin Gain Increase" -> {
                description = description + "Gain +" + Integer.toString(power) + "% more coins for ";
                description = description + Integer.toString(length) + " waves.";
            }
            case "Wave Length Decrease" -> {
                description = description + "Lower wave length by -" + Integer.toString(power) + "% for ";
                description = description + Integer.toString(length) + " waves.";
            }
            case "Word Time Increase" -> {
                description = description + "Increases word countdown length by +" + Integer.toString(power) + "% for ";
                description = description + Integer.toString(length) + " waves.";
            }
            case "Regeneration" -> {
                description = description + "Regenerates " + Integer.toString(power)+ "HP after every turn for ";
                description = description + Integer.toString(length) + " waves.";
            }
        }
    }
}
