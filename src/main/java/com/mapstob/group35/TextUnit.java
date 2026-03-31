package com.mapstob.group35;

public class TextUnit {
    private String text;
    private int curTime;
    private int timeLimit;

    public TextUnit(String text, int timeLimit) {
        this.text = text;
        this.timeLimit = timeLimit;
        curTime = 0;
    }

    public double getTimeLimit(){return timeLimit;}
    public double getCurTime(){return curTime;}
    public void incTime(){ curTime++;}

    public String getText(){return text;}

    public float getPercentTime(){
        return (float)curTime / (float)timeLimit;
    }
    public boolean checkTime(){
        if (curTime >= timeLimit){
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
