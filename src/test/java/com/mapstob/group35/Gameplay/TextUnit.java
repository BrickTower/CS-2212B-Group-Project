package com.mapstob.group35.Gameplay;

public class TextUnit {
    private String text;
    private int timeLeft;

    public TextUnit(String text, int timeLeft) {
        this.text = text;
        this.timeLeft = timeLeft;
    }

    public void incTime() { this.timeLeft--; }
    public boolean checkTime() { return this.timeLeft <= 0; }
    public String getText() { return text; }
    
    // Mocking the spelling logic for the manager tests
    public int checkSpellingValidity(String input) {
        return text.equals(input) ? 0 : 1; 
    }
}