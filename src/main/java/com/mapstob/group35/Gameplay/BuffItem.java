package com.mapstob.group35.Gameplay;

public class BuffItem {
    static private String[] possibleBuffs = {
            "Heal","Coin Gain Increase", "Wave Length Decrease","Word Time Increase","Regeneration"
    };//Ideally this would be stored in a file but oh well hardcoding it is

    private String name;
    private int cost;
    private int power;
    private int length;
    private String description;

    public BuffItem() {
        determineItem();
        setItemInfo(name);
        cost = 10 * power * length;
        setDescription(name);
    }
    public String getName() {return name;}
    public int getCost() {return cost;}
    public int getPower() {return power;}
    public int getLength() {return length;}
    public String getDescription() {return description;}

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
        switch (name) {
            case "Heal" ->{
                description = "Costs " + Integer.toString(cost) + ".\nHeals" + Integer.toString(power) + "HP immediately";
            }
            case "Coin Gain Increase" -> {
                description = "Costs " + Integer.toString(cost) + ".\n";
                description = description + "Gain +" + Integer.toString(power) + "% more coins for ";
                description = description + Integer.toString(length) + " waves.";
            }
            case "Wave Length Decrease" -> {
                description = "Costs " + Integer.toString(cost) + ".\n";
                description = description + "Lower wave length by -" + Integer.toString(power) + "% for ";
                description = description + Integer.toString(length) + " waves.";
            }
            case "Word Time Increase" -> {
                description = "Costs " + Integer.toString(cost) + ".\n";
                description = description + "Increases word countdown length by +" + Integer.toString(power) + "% for ";
                description = description + Integer.toString(length) + " waves.";
            }
        }
    }
}
