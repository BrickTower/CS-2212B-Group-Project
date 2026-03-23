package com.mapstob.group35.core;


import java.util.ArrayList;

public class ParentProfile extends User {
    private ArrayList<PlayerProfile> playersManaged;

    public ParentProfile(int userID, String userName, String password) {
        super(userID, userName, password);
        this.playersManaged = new ArrayList<PlayerProfile>();
    }

    public void addPlayer(PlayerProfile newPlayer) {
        if (this.playersManaged.contains(newPlayer)) { // GUARD AGAINST PLAYER IF ALREADY MANAGED
            throw new IllegalArgumentException("Player already exists in list.");
        } else {
            this.playersManaged.add(newPlayer); // ADD IF NOT IN LIST
        }
    }

    public void removePlayer(PlayerProfile player) {
        if (!this.playersManaged.contains(player)) { // GUARD AGAINST PLAYER IF ALREADY MANAGED
            throw new IllegalArgumentException("Player is not managed by this Parent.");
        } else {
            this.playersManaged.remove(player);
        }
    }
}
