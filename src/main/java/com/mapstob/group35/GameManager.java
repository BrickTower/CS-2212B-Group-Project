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
    private boolean died;

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
        died = false;
    }
    //if loading data
    public GameManager(int maxLife, int curLife, int wave, int waveTime, int coins, int defaultCoinGain) {
        this.maxLife = maxLife;
        this.curLife = curLife;
        this.coins = coins;
        this.defaultCoinGain = defaultCoinGain;

        this.waveManager = new WaveManager(wave,waveTime);
        this.textManager = new TextManager();
        died = false;
    }
    /// /// /// /// PROCESS
    public void process() throws InterruptedException {
        while(!incTime() && !died){
            Thread.sleep(1000);
        }
        if (!died){
            textManager.clear();
            saveInfo();
        }

    }


    /// /// /// /// INPUT
    public void spellingAttempt(String input){ //TODO replace string input with actual input
        int spellingAttempt = textManager.spellingAttempt(input);
        if (spellingAttempt == 0){
            spellingCorrect();
        }
        else{
            takeDamage(spellingAttempt);
        }
    }

    public void startWave() throws InterruptedException {
        waveManager.startWave();
        textManager.reset();
        for(int x = 0;x<5;x++) {
            textManager.makeText("Placeholder");
        }
        process();
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
    public boolean incTime(){
        int damage;
        damage = textManager.incTime();

        if (damage >0){takeDamage(damage);}

        return waveManager.incTime();
    }




    /// /// /// /// MODIFY INFO
    private void saveInfo(){
        //TODO save information
    }
    private void deleteInfo(){
        //TODO
    }
    private void die(){
        died = true;
        deleteInfo();
        //TODO show death screen, delete current run stats
    }


}
