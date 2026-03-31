package com.mapstob.group35;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameManager {
    private WaveManager waveManager;
    private TextManager textManager;

    private int coins;
    private int defaultCoinGain;
    private int maxLife;
    private int curLife;

    private Process process;

    //TODO move elsewhere
    private String[] easyDiffWords = {"cake", "cookie", "tea", "hope", "news", "chance", "luck", "known", "you", "soul"};
    private String[] mediumDiffWords = {"poland", "canada", "reimu", "marisa", "important", "zoot", "dryad", "nomad", "heaven", "honeybee", "sandpaper"};
    private String[] hardDiffWords = {"artificial", "manipulation", "despised", "topological", "prerequisite", "difficulty", "completion", "andesite", "granite", "drizzled"};




    //Constructor
    public GameManager() {
        maxLife = 100; //todo change
        curLife = maxLife;
        coins = 0;
        defaultCoinGain = 10;

        waveManager = new WaveManager(0,30);
        textManager = new TextManager();
    }
    //if loading data
    public GameManager(int maxLife, int curLife, int wave, int waveTime, int coins, int defaultCoinGain) {
        this.maxLife = maxLife;
        this.curLife = curLife;
        this.coins = coins;
        this.defaultCoinGain = defaultCoinGain;

        this.waveManager = new WaveManager(wave,waveTime);
        this.textManager = new TextManager();
    }
    /// /// /// /// PROCESS
    public void process() throws InterruptedException {
        while(true){
            Thread.sleep(1000);
            incTime();
        }

    }


    /// /// /// /// INPUT
    public void spellingAttempt(String input){ //replace string input with actual input
        int spellingAttempt = textManager.spellingAttempt(input);
        if (spellingAttempt == 0){
            spellingCorrect();
        }
        else{
            takeDamage(spellingAttempt);
        }
    }

    /// /// /// /// COINS
    public void gainCoins(int amount){coins += amount;}
    public int getCoins() {return coins;}
    public boolean useCoins(int amount){
        if (coins > amount){
            coins -= amount;
            return true;
        }
        return false;
    }
    /// /// /// /// HEALTH
    public void takeDamage(int amount){
        if (amount > curLife) {curLife = 0; die();}
        else{ curLife -= amount; }
    }
    public void heal(int amount){
        if (amount + curLife > maxLife) {curLife = maxLife;}
        else {curLife += amount; }
    }

    /// /// /// /// FINISH
    public void spellingCorrect(){
        //TODO other additions?
        gainCoins(defaultCoinGain);
    }

    /// /// /// /// TIME
    public void incTime(){
        waveManager.incTime();
        int damage;
        damage = textManager.incTime();

        if (damage >0){takeDamage(damage);}
    }




    /// /// /// /// SAVE INFO
    private void saveInfo(){
        //TODO save information
    }
    private void die(){
        //TODO show death screen, delete current run stats
    }


}
