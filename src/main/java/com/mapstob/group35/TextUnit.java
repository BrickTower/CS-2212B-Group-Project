package com.mapstob.group35;

public class TextUnit {
    private String text;
    private String inputtedText;
    private int difficulty;
    private double timeLimit;

    public TextUnit(String text, int difficulty, double timeLimit) {
        this.text = text;
        this.difficulty = difficulty;
        this.timeLimit = timeLimit;
    }

    public double getTimeLimit(){return timeLimit;}

    public void setInputtedText(String inputtedText){this.inputtedText = inputtedText;}
    public String getInputtedText(){return inputtedText;}
    public String getText(){return text;}

    public int getDifficulty(){return difficulty;}

    public boolean checkTime(double curTime){
        if (curTime > timeLimit){
            return true;
        }
        return false;
    }


}
