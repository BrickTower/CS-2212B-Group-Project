package com.mapstob.group35.Gameplay;


/**
 * Single word to spell, managed by TextManager. Tracks how long it's been here. Contains a method for checking spelling validity.
 *
 * @author Shaun
 * @see TextManager
 */
public class TextUnit {
    private String text;
    private int curTime;
    private int timeLimit;

    /**
     * Constructor.
     * @param text
     * @param timeLimit
     */
    public TextUnit(String text, int timeLimit) {
        this.text = text;
        this.timeLimit = timeLimit;
        curTime = 0;
    }

    /**
     * Gets time limit.
     * @return time limit
     */
    public double getTimeLimit(){return timeLimit;}

    /**
     * Gets time passed.
     * @return time passed
     */
    public double getCurTime(){return curTime;}

    /**
     * Increments time. Returns true if time limit reached.
     * @return if time limit is reached
     */
    public boolean incTime(){
        curTime++;
        return curTime >= timeLimit;
    }

    /**
     * Gets text.
     * @return text
     */
    public String getText(){return text;}

    /**
     * Gets percentage of time passed.
     * @return percent of time passed out of the time limit
     */
    public float getPercentTime(){
        return (float)curTime / (float)timeLimit;
    }

    /**
     * Basic check of how many mistakes were made. Will return 0 if it is accurate.
     * <br>
     * Goes through and checks how many different letters there are.
     * If the object is misspelled by moving something forwards or backwards, it counts as a lot of mistakes.
     * "Spelling" as "Seplling" will result in 7 mistakes, despite it only being a letter swap. Too bad!
     *
     * @param spellingAttempt
     * @return amount of mistakes made
     */
    public int checkSpellingValidity(String spellingAttempt){
        int mistakes = 0;

        String smaller_text = spellingAttempt;
        if (spellingAttempt.length() > text.length()){
            smaller_text = text;
        }
        if (spellingAttempt.length() != text.length()){
            mistakes += Math.abs(spellingAttempt.length() - text.length());
        }

        for(int index = 0; index < smaller_text.length(); index++){
            if (text.charAt(index) != spellingAttempt.charAt(index)){
                mistakes++;
            }
        }
        System.out.println("mistakes: " + mistakes);
        return mistakes;
    }


}
