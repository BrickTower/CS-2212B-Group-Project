package com.mapstob.group35.core;

// FIX FOR JSON AUTO DETECT FROM GITHUB COPILOT: https://github.com/copilot/share/c03d4088-4924-8c05-b150-284ec451014e. CODE THAT IS NOT ANNOTATIONS IS HUMAN WRITTEN
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mapstob.group35.DataManager;

import java.util.List;

@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE // ADD THIS
)
public class PlayerProfile {
    /** The player's current difficulty level.*/
    @JsonProperty("level")
    private int level;
    /** The current amount of coins the Player has.*/
    @JsonProperty("coins")
    private int coins;
    /** A {@link Stats} representation of the current Statistics (WPM, accuracy, etc.)*/
    @JsonProperty("stats")
    private Stats playerStats;
    ///  Profile's username (i.e. egregor4)
    @JsonProperty("userName")
    private String userName;
    ///  Profile's password
    @JsonProperty("password")
    private String password;

    /** Empty version of PlayerProfile Constructor. Should only be used for Jackson compatibility.
     */
    public PlayerProfile() {this.playerStats = new Stats();}

    /**
     Player constructor. Creates a new Player Profile object.

     @param userName String representing the login's userName
     @param password String representing the account's password

     @author Ethan Gregory
     */
    public PlayerProfile(String userName, String password) {
        this.userName = userName; // INIT. CLASS-SPECIFIC VARIABLES
        this.password = password;
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
        this.updateSave();
    }

    /**
     *  Updates the JSON file (parents.json) with any major changes (when coins are added or removed, Stats are modified, etc.)
     *  Only called within its class, and makes decision based on whether or not its a ParentProfile
     *
     * @author Ethan Gregory
     */
    protected void updateSave() {
        if (this instanceof ParentProfile){
            try {
                List<ParentProfile> parents = DataManager.loadList("parents.json", new TypeReference<List<ParentProfile>>() {
                });

                int counter = 0; // FIND INDEX IN FOR LOOP
                for (ParentProfile parent : parents) {
                    if (parent.getUserName().equals(this.getUserName())) {
                        parents.set(counter, (ParentProfile) (this));
                        break;
                    }
                    counter++;
                }
                DataManager.saveList("parents.json", parents);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                List<ParentProfile> parents = DataManager.loadList("parents.json", new TypeReference<List<ParentProfile>>() {
                });

                int counter = 0; // FIND INDEX IN FOR LOOP
                boolean foundPlayer = false;
                for (ParentProfile parent : parents) {
                    int counter2 = 0;
                    List<PlayerProfile> players = parent.getPlayersManaged();
                    for (PlayerProfile player : players) {
                        if (player.getUserName().equals(this.getUserName())) {
                            players.set(counter2, this);
                            foundPlayer = true;
                            break;
                        }
                        counter2++;
                    }
                    if (foundPlayer) { // IF PLAYER WAS FOUND, BREAK OUT OF ORIGINAL FOR LOOP
                        break;
                    }
                    counter++;
                }
                DataManager.saveList("parents.json", parents);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
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
            this.updateSave();
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
        this.updateSave();
    }

    /**
     * Resets Player's difficulty level back to 1. Used when a run ends to reset back to normal
     * <br>
     * @author Ethan Gregory
     */
    public void resetLevel() {
        this.level = 1;
        this.updateSave();
    }

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
     * Sets the amount of coins present
     * @param coins
     */
    public void setCoins(int coins) {this.coins=coins;}

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
    public void resetStats() {
        this.playerStats = new Stats();
        this.updateSave();
    }

    /**
     * Sets a player's Stats. Used to save any Stats changes to the Player.
     * @param newStats Stats object, preferably the one from the currently logged in user (currUser.getPlayerStats(), for example)
     * @see Stats
     * @author Ethan Gregory
     */
    public void setStats(Stats newStats) {
        this.playerStats = newStats;
        this.updateSave();
    }

    /**
     * Returns a String representation of the username
     * @return the Profile's userName, represented as a String
     * @see String
     * @author Ethan Gregory
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Returns a String representation of the User's password
     * @return the Profile's password, represented as a String
     * @see String
     * @author Ethan Gregory
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the Player's password to the provided String
     * @param newPassword A {@link String} representation of the password.
     * @author Ethan Gregory
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
        this.updateSave();
    }

    /**
     * Overloaded toString method for all Profiles
     * @return the Profile's username
     */
    public String toString() {
        return this.getUserName();
    }
}
