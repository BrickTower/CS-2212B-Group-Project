package com.mapstob.group35.Gameplay;

import java.util.ArrayList;


/**
 * Manages TextUnits as well as keeps track of the typing statistics.
 * Adds, removes, validates spelling as needed. Calls GameManager on mistakes or completing a word.
 *
 * @author Shaun
 * @see GameManager
 * @see TextUnit
 */
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

    /**
     * Constructor
     */
    public TextManager() {
        textList = new ArrayList<TextUnit>();
        WPMTracker = new ArrayList<Integer>();
        WPM = 0;
        curTime = 0;
        defaultWordSpawnTime = 2;
        wordSpawnTime = defaultWordSpawnTime;
        wordDefaultTimeLeft = 15;
        mistakes = 0;
        wordsCompleted = 0;
    }

    /**
     * Constructor for loading already existing playerdata
     * @param defaultWordSpawnTime
     * @param wordDefaultTimeLeft
     */
    public TextManager(int defaultWordSpawnTime,int wordDefaultTimeLeft) {
        textList = new ArrayList<TextUnit>();
        WPMTracker = new ArrayList<Integer>();
        WPM = 0;
        curTime = 0;
        this.defaultWordSpawnTime = defaultWordSpawnTime;
        wordSpawnTime = defaultWordSpawnTime;
        this.wordDefaultTimeLeft = wordDefaultTimeLeft;
        mistakes = 0;
        wordsCompleted = 0;
    }

    /**
     * Resets all data.
     * Used upon game loss.
     */
    public void reset(){
        WPMTracker.clear();
        WPM = 0;
        resetTime();
        mistakes = 0;
        wordsCompleted = 0;
        textList.clear();
    }

    /**
     * Increments time for itself and every TextUnit it carries.
     * Adds up any damage taken and returns it.
     * @return damage amount
     */
    public int incTime(){
        curTime += 1;
        int damage_amount = 0;
        for(int index = 0; index < textList.size(); index++){
            TextUnit text = textList.get(index);
            if (text.incTime()){
                damage_amount += textTimeout(text);
            }
        }
        if (curTime % wordSpawnTime == 0){
            makeText("Placeholder");
        }

        return damage_amount;
    }

    /// /// /// /// TEXT
    /**
     * Adds a new word to the text list.
     * @param text
     */
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

    /**
     * Clears text list.
     */
    public void clear(){
        textList.clear();
    }

    /**
     * Checks if spelling attempt is correct, returns damage taken.
     * If spelling attempt is correct, removes text from the list and increments words completed.
     * If incorrect, increments mistakes.
     * @param input
     * @return Damage taken
     */
    public int spellingAttempt(String input){
        int mistakes = textList.getFirst().checkSpellingValidity(input);
        if (mistakes == 0){
            wordsCompleted++;
            removeText(textList.getFirst());
            return 0;
        }
        else{
            this.mistakes++;
            return ((Integer) mistakes/2);
        }
    }

    /**
     * Sets time between word spawns.
     * @param WordTime
     */
    public void setWordSpawnTime(int WordTime){this.wordSpawnTime = WordTime;}
    private int getWordSpawnTimeLeft () {return wordSpawnTime - curTime;}

    /**
     * Resets time.
     */
    public void resetTime(){curTime = 0;}
}
