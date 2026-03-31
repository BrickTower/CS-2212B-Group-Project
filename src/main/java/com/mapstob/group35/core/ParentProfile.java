package com.mapstob.group35.core;


import java.util.LinkedList; // IMPORT ARRAYLIST FOR PLAYER LIST

// FIX FOR JSON AUTO DETECT FROM GITHUB COPILOT: https://github.com/copilot/share/401d100a-4104-8407-9903-280fe41b405e. FOLLOWING CODE IS HUMAN WRITTEN
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ParentProfile extends PlayerProfile { // Parents should be able to play
    /** A list of all Players currently managed by this Parent*/
    private LinkedList<PlayerProfile> playersManaged;

    /** Empty version of ParentProfile Constructor. Should only be used for Jackson compatibility, according to this chatbot log from Eric's work:
     * [PUT LINK HERE]
     */
    public ParentProfile() {super();}

    public ParentProfile(String userName, String password) {
        super(userName, password);
        this.playersManaged = new LinkedList<PlayerProfile>();
    }

    /**
     * Adds a new Player to the list of Players managed by the calling Parent.
     * This Player must be a {@link PlayerProfile} only. Parents can not be managed by other Parents.
     * @throws IllegalArgumentException if the Player is not managed by the Parent calling the function, or if said Player is a Parent themselves.
     * @param newPlayer A valid Player who is managed by the calling Parent
     */
    public void addPlayer(PlayerProfile newPlayer) {
        if ((newPlayer instanceof ParentProfile)) {
            throw new IllegalArgumentException("Parents cannot be managed by other Parents.");
        }
        else if (this.playersManaged.contains(newPlayer)) { // GUARD AGAINST PLAYER IF ALREADY MANAGED, AND IF PLAYER IS PARENT
            throw new IllegalArgumentException("Player already exists in list.");
        } else {
            this.playersManaged.add(newPlayer); // ADD IF NOT IN LIST
        }
    }

    /**
     * Removes a Player from the list of Players managed by the Parent
     * @throws IllegalArgumentException if the Player is not already managed by the Parent calling the function
     * @param player A valid Player who is managed by the calling Parent
     */
    public void removePlayer(PlayerProfile player) {
        if (!this.playersManaged.contains(player)) { // GUARD AGAINST PLAYER IF ALREADY MANAGED, AND IS NOT PARENT AS WELL
            throw new IllegalArgumentException("Player is not managed by this Parent.");
        } else {
            this.playersManaged.remove(player);
        }
    }

    /**
     * Retrieves the Stats associated with a Player managed by the Parent
     * @throws IllegalArgumentException if the Player is not managed by the Parent calling the function
     * @param player A valid Player who is managed by the calling Parent
     */
    public Stats getPlayerStats(PlayerProfile player) {
        if (!this.playersManaged.contains(player)) { // CHECK TO SEE IF PLAYER IS MANAGED BY PARENT. IF NOT, THROW AN ERROR
            throw new IllegalArgumentException("Player is not managed by this Parent.");
        } else { // OTHERWISE, GET STATS
            return player.getPlayerStats();
        }
    }

    /**
     * Resets the Stats of a Player who the Parent is managing.
     * @throws IllegalArgumentException if the Player is not managed by the Parent calling the function
     * @param player A valid Player who is managed by the calling Parent
     */
    public void resetPlayerStats(PlayerProfile player) {
        if (!this.playersManaged.contains(player)) { // CHECK TO SEE IF PLAYER IS MANAGED BY PARENT. IF NOT, THROW AN ERROR
            throw new IllegalArgumentException("Player is not managed by this Parent.");
        } else { // OTHERWISE, RESET STATS
            player.resetStats();
        }
    }
}
