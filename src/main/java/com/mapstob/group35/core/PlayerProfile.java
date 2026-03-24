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

    /**
     * Resets Player's difficulty level back to 1. Used when a run ends to reset back to normal
     * <br>
     * @author Ethan Gregory
     */
    public void resetLevel() {
        this.level = 1;
    }
    // SIMPLE GETTERS

    /**
     * Returns an Integer representation of the Player's current difficulty level
     * @return the Player's current difficulty level, represented as a Integer
     * @see Integer
     * @author Ethan Gregory
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Returns an Integer representation of the amount of coins the Player currently has.
     * @return the Player's current amount of coins, represented as a Integer
     * @see Integer
     * @author Ethan Gregory
     */
    public int getCoins() {
        return this.coins;
    }
}
