package com.mapstob.group35.core;


import java.util.LinkedList; // IMPORT ARRAYLIST FOR PLAYER LIST
import java.util.List;

// FIX FOR JSON AUTO DETECT FROM GITHUB COPILOT: https://github.com/copilot/share/401d100a-4104-8407-9903-280fe41b405e. CODE FOLLOWING THIS ANNOTATION IS HUMAN WRITTEN
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mapstob.group35.DataManager;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ParentProfile extends PlayerProfile { // Parents should be able to play
    /** A list of all Players currently managed by this Parent, listed by UserName*/
    private LinkedList<PlayerProfile> playersManaged;

    /** Empty version of ParentProfile Constructor. Should only be used for Jackson compatibility.
     * @author Ethan Gregory
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
     * @author Ethan Gregory
     */
    public void addPlayer(PlayerProfile newPlayer) {
        if ((newPlayer instanceof ParentProfile)) {
            throw new IllegalArgumentException("Parents cannot be managed by other Parents.");
        }
        else if (this.playersManaged.contains(newPlayer)) { // GUARD AGAINST PLAYER IF ALREADY MANAGED, AND IF PLAYER IS PARENT
            throw new IllegalArgumentException("Player already exists in list.");
        } else {
            this.playersManaged.add(newPlayer); // ADD IF NOT IN LIST
            this.updateSave();
        }
    }

    /**
     * Retrieves the Stats associated with a Player managed by the Parent
     * @throws IllegalArgumentException if the Player is not managed by the Parent calling the function
     * @param player A valid Player who is managed by the calling Parent
     * @author Ethan Gregory
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
     * @author Ethan Gregory
     */
    public void resetPlayerStats(PlayerProfile player) {
        if (!this.playersManaged.contains(player)) { // CHECK TO SEE IF PLAYER IS MANAGED BY PARENT. IF NOT, THROW AN ERROR
            throw new IllegalArgumentException("Player is not managed by this Parent.");
        } else { // OTHERWISE, RESET STATS
            player.resetStats();
        }
    }

    /**
     * Returns the usernames of each Player that this Parent manages, in the form of a {@link LinkedList}.
     * @return All Usernames of every {@link PlayerProfile} added to the list.
     * @author Ethan Gregory
     */
    public LinkedList<PlayerProfile> getPlayersManaged() {
        return this.playersManaged;
    }

    /**
     *  Updates the JSON file (parents.json) with any major changes (when coins are added or removed, Stats are modified, etc.)
     *  Only called within its class, and slightly modified to accomodate for different way of accessing
     *
     * @author Ethan Gregory
     */
    private void updateSave() {
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
    }
}
