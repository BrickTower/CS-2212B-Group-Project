package com.mapstob.group35.core;

public class PlayerProfile extends User {
    private int level;
    private int coins;

    public PlayerProfile(int userID, String userName, String password) {
        super(userID, userName, password); // CALL INITIAL CONSTRUCTOR & INIT. CLASS-SPECIFIC VARIABLES
        this.level = 1;
        this.coins = 0;
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public boolean spendCoins(int amount) {
        if (amount > this.coins) { // IF WE HAVE LESS COINS THAN AMOUNT, RETURN FALSE
            return false;
        } else { // IF WE HAVE ENOUGH, SUBTRACT FROM PROFILE AND RETURN TRUE
            this.coins -= amount;
            return true;
        } // END OF IF/ELSE
    }

    public void levelUp() {
        this.level++; // INCREASE LEVEL
    }

    // SIMPLE GETTERS

    public int getLevel() {
        return this.level;
    }

    public int getCoins() {
        return this.coins;
    }
}
