package com.mapstob.group35;

public class TextUnit {
    private String text;
    private int difficulty;
    private double timeLimit;

    public TextUnit(String text, int difficulty, double timeLimit) {
        this.text = text;
        this.difficulty = difficulty;
        this.timeLimit = timeLimit;
    }

    public double getTimeLimit(){return timeLimit;}

   public String getText(){return text;}

    public int getDifficulty(){return difficulty;}

    public boolean checkTime(double curTime){
        if (curTime > timeLimit){
            return true;
        }
        return false;
    }
    public int checkSpellingValidity(String spellingAttempt){
        int mistakes = 0;
        for(int index = 0; index < text.length(); index++){
            if (text.charAt(index) != spellingAttempt.charAt(index)){
                mistakes++;
            }
        }
        return mistakes;
    }


}
