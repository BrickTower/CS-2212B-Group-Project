package com.mapstob.group35.core;

public class PlayerProfile extends User {
    /** The player's current difficulty level.*/
    private int level;
    /** The current amount of coins the Player has.*/
    private int coins;
    /** A {@link Stats} representation of the current Statistics (WPM, accuracy, etc.)*/
    private Stats playerStats;

    /** Empty version of PlayerProfile Constructor. Should only be used for Jackson compatibility, according to this chatbot log from Eric's work:
     * [PUT LINK HERE]
     */
    public PlayerProfile() {super();}

    /**
     Player constructor. Creates a new Player Profile object.

     @param userName String representing the login's userName
     @param password String representing the account's password
     @see User
     @author Ethan Gregory
     */
    public PlayerProfile(String userName, String password) {
        super(userName, password); // CALL INITIAL CONSTRUCTOR & INIT. CLASS-SPECIFIC VARIABLES
        this.level = 1;
        this.coins = 0;
        this.playerStats = new Stats();
    }

    /**
     * Function to add coins to Player's profile. Used when they enter a correct answer.
     * @throws IllegalArgumentException if coins to add is either 0 or negative
     * @param amount The amount of coins to add to the Player's Coin amount
     */
    public void addCoins(int amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Coin amount not possible to add.");
        } // END OF IF STATEMENT
        this.coins += amount;
    }

    /**
     * Boolean function to determine if User can afford an Item from the Item shop. If so, buy the upgrade, removing coins from the account)
     * @param amount The amount of Coins necessary for purchasing an Item
     * @return True if Item is bought, False if not.
     */
    public boolean spendCoins(int amount) {
        if (amount > this.getCoins()) { // IF WE HAVE LESS COINS THAN AMOUNT, RETURN FALSE
            return false;
        } else { // IF WE HAVE ENOUGH, SUBTRACT FROM PROFILE AND RETURN TRUE
            this.coins -= amount;
            return true;
        } // END OF IF/ELSE
    }

    /**
     * Increases the Player's current difficulty level by 1. Used when they successfully complete a wave.
     * <br>
     * @author Ethan Gregory
     */
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

    /**
     * Returns a Stats object containing the current Stats of the Player.
     * @return the Player's current statistics, represented as a Stats object
     * @see Stats
     * @author Ethan Gregory
     */
    public Stats getPlayerStats() {
        return this.playerStats;
    }

    /**
     * Resets a player's Stats to the basic use case. Should only be used when a Parent is resetting Player stats.
     * @see Stats
     * @author Ethan Gregory
     */
    protected void resetStats() {
        this.playerStats = new Stats();
    }
}
