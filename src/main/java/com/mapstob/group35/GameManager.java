package com.mapstob.group35;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameManager {
    private int wave;
    private double waveTime;
    private double waveInitTime;
    private int defaultWaveTime;
    private int coins;

    private ArrayList<TextUnit> textList;
    private int maxLife;
    private int curLife;

    //TODO move elsewhere
    private String[] easyDiffWords = {"cake", "cookie", "tea", "hope", "news", "chance", "luck", "known", "you", "soul"};
    private String[] mediumDiffWords = {"poland", "canada", "reimu", "marisa", "important", "zoot", "dryad", "nomad", "heaven", "honeybee", "sandpaper"};
    private String[] hardDiffWords = {"artificial", "manipulation", "despised", "topological", "prerequisite", "difficulty", "completion", "andesite", "granite", "drizzled"};

    private double wordDefaultTimeLeft;


    //Constructor
    public GameManager() {
        textList = new ArrayList<TextUnit>();
        maxLife = 100; //todo change
        curLife = maxLife;
        wave = 0;
        defaultWaveTime = 30;
        coins = 0;
    }
    //if loading data
    public GameManager(int maxLife, int curLife, int wave, int waveTime, int coins) {
        textList = new ArrayList<TextUnit>();
        this.maxLife = maxLife;
        this.curLife = curLife;
        this.wave = wave;
        this.waveTime = waveTime;
        this.coins = coins;
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


    /// /// /// /// TEXT
    public void makeText(String text, int difficulty) {
        TextUnit newTextUnit = new TextUnit(text,difficulty,wordDefaultTimeLeft);
        textList.add(newTextUnit);
    }
    public void removeText(TextUnit text){
        textList.remove(text);
    }

    public void spellingAttempt(String input){
        int mistakes = textList.getFirst().checkSpellingValidity(input);
        if (mistakes == 0){
            removeText(textList.getFirst());
        }
        else{
            takeDamage((Integer) mistakes/2);
        }
    }

    public void spellingCorrect(int difficulty){
        //TODO other additions?
        gainCoins(difficulty);
    }


    /// /// /// /// TIME
    public void checkTime(){
        for (int index = 0; index < textList.size(); index++){
            TextUnit text = textList.get(index);
            if (text.checkTime(System.nanoTime())){
                textTimeout(text);
            }
        }
    }

    //use for time left bar
    public double getPercentTextTimeLeft(TextUnit text){
        double totaltime = text.getTimeLimit() - text.getInitTime();
        double curtime = System.nanoTime() - text.getInitTime();
        return curtime / totaltime;
    }
    public void textTimeout(TextUnit text) {
        takeDamage(text.getText().length() * 2);
        removeText(text);
    }

    /// /// /// /// WAVE
    public void incWave(){wave++;}
    public int getWave(){return wave;}
    public void setWaveTime(double waveTime){this.waveTime = waveTime;}
    public double getWaveTimeLeft () {return waveTime - System.nanoTime();}

    public void waveOver(){
        textList.clear();
        incWave();
        saveInfo();
    }
    public void startWave(){
        waveInitTime =  System.nanoTime();
        setWaveTime(waveInitTime + defaultWaveTime * 1000000000);
    }

    // for the timer icon
    public double waveCompletionPercent(){
        return (waveTime - System.nanoTime()) /(waveTime - waveInitTime);
    }

    /// /// /// /// SAVE INFO
    private void saveInfo(){
        //TODO save information
    }
    private void die(){
        //TODO show death screen, delete current run stats
    }

}
