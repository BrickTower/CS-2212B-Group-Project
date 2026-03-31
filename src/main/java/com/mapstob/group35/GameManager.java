package com.mapstob.group35;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameManager {
    private int wave;
    private int curTime;
    private int waveTime;
    private int defaultWaveTime;
    private int wordSpawnTime;
    private int defaultWordSpawnTime;

    private int coins;

    private ArrayList<TextUnit> textList;
    private int maxLife;
    private int curLife;

    private Process process;

    //TODO move elsewhere
    private String[] easyDiffWords = {"cake", "cookie", "tea", "hope", "news", "chance", "luck", "known", "you", "soul"};
    private String[] mediumDiffWords = {"poland", "canada", "reimu", "marisa", "important", "zoot", "dryad", "nomad", "heaven", "honeybee", "sandpaper"};
    private String[] hardDiffWords = {"artificial", "manipulation", "despised", "topological", "prerequisite", "difficulty", "completion", "andesite", "granite", "drizzled"};

    private int wordDefaultTimeLeft;


    //Constructor
    public GameManager() {
        textList = new ArrayList<TextUnit>();
        maxLife = 100; //todo change
        curLife = maxLife;
        wave = 0;
        defaultWaveTime = 30;
        wordDefaultTimeLeft = 10;
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
    /// /// /// /// PROCESS
    public void process() throws InterruptedException {
        while(true){
            Thread.sleep(1000);
            incTime();
            for(int index = 0; index < textList.size(); index++){
                TextUnit text = textList.get(index);
                text.incTime();
                if (text.checkTime()){
                    textTimeout(text);
                }
            }
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
    public void setWordSpawnTime(int WordTime){this.wordSpawnTime = WordTime;}
    public void incWordSpawnTime(){}
    public double getWordSpawnTimeLeft () {return wordSpawnTime - (curLife % wordSpawnTime);}


    /// /// /// /// TIME
    public void checkTime(){
        for (int index = 0; index < textList.size(); index++){
            TextUnit text = textList.get(index);
            if (text.checkTime()){
                textTimeout(text);
            }
        }
    }


    public void textTimeout(TextUnit text) {
        takeDamage(text.getText().length() * 2);
        removeText(text);
    }

    /// /// /// /// WAVE
    public void incWave(){wave++;}
    public int getWave(){return wave;}
    public void setWaveTime(int waveTime){this.waveTime = waveTime;}
    public double getWaveTimeLeft () {return waveTime - curTime;}


    public void waveOver(){
        textList.clear();
        incWave();
        saveInfo();
    }
    public void startWave(){
        curTime = 0;
        setWaveTime(defaultWaveTime);
    }
    public void incTime(){
        curTime++;
        if (curTime>=waveTime){
            waveOver();
        }
    }
    // for the timer icon
    public float waveCompletionPercent(){
        return (float) curTime / (float) waveTime;
    }

    /// /// /// /// SAVE INFO
    private void saveInfo(){
        //TODO save information
    }
    private void die(){
        //TODO show death screen, delete current run stats
    }


}
