package com.mapstob.group35;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameManager {
    private int wave;
    private double waveTime;

    private ArrayList<TextUnit> textList;
    private int maxLife;
    private int curLife;

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
        waveTime = 30;
    }

    public void takeDamage(int amount){
        if (amount > curLife) {die();}
        else{ curLife -= amount; }
    }
    public void heal(int amount){
        if (amount + curLife > maxLife) {curLife = maxLife;}
        else {curLife += amount; }
    }
    private void die(){
        //TODO show death screen
    }


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

        }
    }
    public void spellingCorrect(){

    }



    public void checkTime(){
        for (int index = 0; index < textList.size(); index++){
            TextUnit text = textList.get(index);
            if (text.checkTime(System.nanoTime())){
                textTimeout(text);
            }
        }
    }

    public void textTimeout(TextUnit text) {
        takeDamage(text.getDifficulty() * 2);
        removeText(text);
    }
    public void incWave(){wave++;}
    public int getWave(){return wave;}
    public void setWaveTime(int waveTime){this.waveTime = waveTime;}
    public double getWaveTimeLeft () {return waveTime;}



}
