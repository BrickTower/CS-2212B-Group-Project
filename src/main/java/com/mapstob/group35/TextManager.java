package com.mapstob.group35;

import java.util.ArrayList;

public class TextManager {

    private ArrayList<TextUnit> textList;

    private int curTime;
    private int wordSpawnTime;
    private int defaultWordSpawnTime;
    private int wordDefaultTimeLeft;


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
        return damage_amount;
    }

    /// /// /// /// TEXT
    public void makeText(String text, int difficulty) {
        TextUnit newTextUnit = new TextUnit(text,difficulty,wordDefaultTimeLeft);
        textList.add(newTextUnit);
    }
    public void removeText(TextUnit text){
        textList.remove(text);
    }
    public int textTimeout(TextUnit text) {
        removeText(text);
        return text.getText().length() * 2;
    }

    public int spellingAttempt(String input){
        int mistakes = textList.getFirst().checkSpellingValidity(input);
        if (mistakes == 0){
            removeText(textList.getFirst());
            return 0;
        }
        else{
            return ((Integer) mistakes/2);
        }
    }

    public void setWordSpawnTime(int WordTime){this.wordSpawnTime = WordTime;}
    public void incWordSpawnTime(){}
    public int getWordSpawnTimeLeft () {return wordSpawnTime - curTime;}

}
