package com.mapstob.group35;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameManager {
    private ArrayList<TextUnit> textList;

    private double curTime;
    private double wordDefaultTimeLeft;


    //Constructor
    public GameManager() {
        textList = new ArrayList<TextUnit>();
        curTime = 0;

    }

    public void makeText(String text, int difficulty) {
        TextUnit newTextUnit = new TextUnit(text,difficulty,wordDefaultTimeLeft);
        textList.add(newTextUnit);
    }
    public void removeText(TextUnit text){
        textList.remove(text);
    }





    public void checkTime(){
        for (int index = 0; index < textList.size(); index++){
            TextUnit text = textList.get(index);
            if (text.checkTime(curTime)){
                textTimeout(text);
            }
        }
    }

    public void textTimeout(TextUnit text){
        //OTHER ACTIONS??
        removeText(text);
    }



}
