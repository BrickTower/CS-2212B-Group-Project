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
        defaultWordSpawnTime = 4;
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
    public TextManager(int defaultWordSpawnTime,int wordDefaultTimeLeft,int mistakes,int wordsCompleted) {
        textList = new ArrayList<TextUnit>();
        WPMTracker = new ArrayList<Integer>();
        WPM = 0;
        curTime = 0;
        this.defaultWordSpawnTime = defaultWordSpawnTime;
        wordSpawnTime = defaultWordSpawnTime;
        this.wordDefaultTimeLeft = wordDefaultTimeLeft;
        this.mistakes = mistakes;
        this.wordsCompleted = wordsCompleted;
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

        for (int index = 0; index < WPMTracker.size(); index++){
            int wordTime = WPMTracker.get(index);
            if (wordTime+10 == curTime){
                WPMTracker.remove(WPMTracker.get(index)) ;
                index--;
            }
        }
        WPM = calculateWPM();

        if (curTime % wordSpawnTime == 0){
            makeText();
        }


        return damage_amount;
    }

    /// /// /// /// TEXT
    /**
     * Adds a new word to the text list.
     */
    public void makeText() {
        TextUnit newTextUnit = new TextUnit("AAA",wordDefaultTimeLeft);
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
     *  WPMTracker keeps track of every word thats been typed in the last 10 seconds
     *  It adds it up and then multiplies by 6 for an approximation of the current WPM
     * @return Words Per Minute (approximation)
     */
    public int calculateWPM(){
        int WordsInTheLastTenSeconds = WPMTracker.size();
        return WordsInTheLastTenSeconds * 6; //for 60 seconds
    }

    /**
     * Calculates the total mistake percentage of the run.
     * @return Mistake percentage
     */
    public float calculateMistakesPercent(){
        return (float)mistakes / (float)wordsCompleted;
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
        if (textList.size() <=0){return -1;}
        int mistakes = textList.get(0).checkSpellingValidity(input);
        if (mistakes == 0){
            wordsCompleted++;

            WPMTracker.add(curTime);

            removeText(textList.get(0));
            return 0;
        }
        else if (mistakes == -1) {//invalid
            return 0;
        }
        else{
            this.mistakes++;
            return ((int) Math.ceil(mistakes/2f));
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

    public int getWPM() {
        return WPM;
    }
    public int getWordsCompleted() {return wordsCompleted;}
    public ArrayList<TextUnit>  getTextList() {return textList;}
}
