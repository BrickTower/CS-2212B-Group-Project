package com.mapstob.group35;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameManager {
    private ArrayList<TextUnit> textList;

    private double wordDefaultTimeLeft;
    private int maxLife;
    private int curLife;


    //Constructor
    public GameManager() {
        textList = new ArrayList<TextUnit>();
        maxLife = 100;//TODO change
        curLife = 100;

    }

    public void heal(int life) {
        if  (life+curLife > maxLife) {curLife = maxLife;}
        else {curLife += life;}
    }
    public void takeDamage(int life) {
        if (curLife < life) {die();}
        curLife -= life;
    }
    public void die(){

    }

    public void makeText(String text, int difficulty) {
        TextUnit newTextUnit = new TextUnit(text,difficulty,wordDefaultTimeLeft);
        textList.add(newTextUnit);
    }
    public void removeText(TextUnit text){
        textList.remove(text);
    }

    public void spellingAttempt(String Text){
        int attemptMistakes = textList.getFirst().checkSpellingValidity(Text);
        if(attemptMistakes>0){

        }
        else{

        }
    }


    public void checkTime(){
        for (int index = 0; index < textList.size(); index++){
            TextUnit text = textList.get(index);
            if (text.checkTime(System.nanoTime())){
                textTimeout(text);
            }
        }
    }

    public void textTimeout(TextUnit text){
        //OTHER ACTIONS??
        removeText(text);
    }



}
