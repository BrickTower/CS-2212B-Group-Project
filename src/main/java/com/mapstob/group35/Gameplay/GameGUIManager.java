package com.mapstob.group35.Gameplay;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class GameGUIManager {
    @FXML private Pane HP_CONTAINER;
    @FXML private Rectangle HP_BAR;
    @FXML private Pane TIME_CONTAINER;
    @FXML private Rectangle TIME_BAR;

    @FXML private VBox SCORE_CONTAINER;
    @FXML private Text WPM_TEXT;
    @FXML private Text MISTAKES_TEXT;
    @FXML private Text WORDS_TEXT;
    @FXML private Text HIGH_WPM_TEXT;
    @FXML private Text HIGH_WAVE_TEXT;


    @FXML private VBox TEXT_LIST;
    @FXML private Text WAVE_TEXT;
    @FXML private Text COINS_TEXT;
    @FXML private TextField TEXT_FIELD;

    @FXML private Button SHOP_BUTTON;
    @FXML private Button MAIN_MENU_BUTTON;

    public void setHP_BAR(int currentHP,int maxHP) {
        //base width 200 base posx 13
        HP_BAR.setWidth(currentHP*2);
        //HP_BAR.setX(13);
        for (Node child : HP_CONTAINER.getChildren()){
            if (child instanceof Text){
                ((Text) child).setText(Integer.toString(currentHP) + " / " + Integer.toString(maxHP) + " HP");
            }
        }
    }
    public void setTIME_BAR(int currentTime,int maxTime) {
        //base width 200 base posx 13
        float percentage = (float)currentTime/(float)maxTime;
        TIME_BAR.setWidth(percentage*200);
        TIME_BAR.setX(13 + (200-percentage*200));
        for (Node child : TIME_CONTAINER.getChildren()){
            if (child instanceof Text){
                ((Text) child).setText("Time left: "+ Integer.toString(currentTime));
            }
        }
    }

    public void setWaveText(int waveNumber){
        WAVE_TEXT.setText("WAVE: "+Integer.toString(waveNumber));
    }

    public void setCoinText(int coinNumber){
        COINS_TEXT.setText("COINS: "+Integer.toString(coinNumber));
    }

    public void setScoreText(int WPM, int mistakes_percent, int session_words,int highscoreWPM, int highscoreWaves){
        WPM_TEXT.setText("WPM: "+Integer.toString(WPM));
        MISTAKES_TEXT.setText("MISTAKES: "+Integer.toString(mistakes_percent) + "%");
        WORDS_TEXT.setText("WORDS THIS SESSION: "+Integer.toString(session_words));
        HIGH_WPM_TEXT.setText("HIGHEST WPM: "+Integer.toString(highscoreWPM));
        HIGH_WAVE_TEXT.setText("HIGHEST WAVE: "+Integer.toString(highscoreWaves));
    }

    //I hate this!!
    public void setWordText(ArrayList<TextUnit> textList){
        int size = textList.size();
        if  (size > 7){size = 7;}
                                    //WHY DO I HAVE TO CAST IT IF ITS ALrEADY EXPLICITLY THE eXACT TYPE AUHSGDSHAIGAWF
        List<TextUnit> shownlist =  textList.subList(0,size);
        ObservableList<Node> children = TEXT_LIST.getChildren();
        for (int i = 0; i < 8; i++){
            //if list size less than 7 hide the rest
            if (i >= size){
                children.get(7-i).setDisable(true);
                children.get(7-i).setVisible(false);
            }
            //Awful, i know
            else {
                Group g = (Group) (((HBox) (children.get(7-i)))).getChildren().getFirst();
                TextUnit t = shownlist.get(i);
                setTextUnit(g, t);

                children.get(7-i).setDisable(false);
                children.get(7-i).setVisible(true);
            }
        }

    }

    private void setTextUnit(Group node, TextUnit textUnit){
        for (Node n : node.getChildren()){
            if (n instanceof Text){
                ((Text) n).setText(textUnit.getText());
            }
            if (n instanceof Rectangle){
                ((Rectangle) n).setWidth ((1- textUnit.getPercentTime()) * 429);
            }

        }
    }
}
