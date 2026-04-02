package com.mapstob.group35.Gameplay;

import java.util.ArrayList;

public class TextManager {

    private ArrayList<TextUnit> textList;

    private int curTime;
    private int wordSpawnTime;
    private int defaultWordSpawnTime;

    private int wordDefaultTimeLeft;

    private int mistakes;
    private int wordsCompleted;
    private int WPM;
    private ArrayList<Integer> WPMTracker;

    public TextManager() {
        this.textList = new ArrayList<TextUnit>();
        WPMTracker = new ArrayList<Integer>();
        WPM = 0;
        curTime = 0;
        defaultWordSpawnTime = 2;
        wordSpawnTime = defaultWordSpawnTime;
        wordDefaultTimeLeft = 15;
        mistakes = 0;
        wordsCompleted = 0;
    }
    public TextManager(int defaultWordSpawnTime,int wordDefaultTimeLeft) {
        this.textList = new ArrayList<TextUnit>();
        WPMTracker = new ArrayList<Integer>();
        WPM = 0;
        curTime = 0;
        this.defaultWordSpawnTime = defaultWordSpawnTime;
        wordSpawnTime = defaultWordSpawnTime;
        this.wordDefaultTimeLeft = wordDefaultTimeLeft;
        mistakes = 0;
        wordsCompleted = 0;
    }

    public void reset(){
        WPMTracker.clear();
        WPM = 0;
        resetTime();
        mistakes = 0;
        wordsCompleted = 0;
        textList.clear();
    }

    public int incTime(){
        curTime += 1;
        int damage_amount = 0;
        for(int index = 0; index < textList.size(); index++){
            TextUnit text = textList.get(index);
            text.incTime();
            if (text.checkTime()){
                damage_amount += textTimeout(text);
            }
        }
        if (curTime % wordSpawnTime == 0){
            makeText("Placeholder");
        }

        return damage_amount;
    }

    /// /// /// /// TEXT
    public void makeText(String text) {
        TextUnit newTextUnit = new TextUnit(text,wordDefaultTimeLeft);
        textList.add(newTextUnit);
    }
    private void removeText(TextUnit text){
        textList.remove(text);
    }
    private int textTimeout(TextUnit text) {
        removeText(text);
        return text.getText().length() * 2;
    }
    public void clear(){
        textList.clear();
    }

    public int spellingAttempt(String input){
        int mistakes = textList.get(0).checkSpellingValidity(input);
        if (mistakes == 0){
            wordsCompleted++;
            removeText(textList.get(0));
            return 0;
        }
        else{
            this.mistakes++;
            return ((Integer) mistakes/2);
        }
    }

    public void setWordSpawnTime(int WordTime){this.wordSpawnTime = WordTime;}
    private int getWordSpawnTimeLeft () {return wordSpawnTime - curTime;}
    public void resetTime(){curTime = 0;}
}
