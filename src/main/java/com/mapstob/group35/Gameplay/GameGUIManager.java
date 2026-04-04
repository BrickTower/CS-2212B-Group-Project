package com.mapstob.group35.Gameplay;

import com.mapstob.group35.SceneManager;
import com.mapstob.group35.core.PlayerProfile;
import com.mapstob.group35.core.loggedUser;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages Game UI, and loads scenes as needed.
 * @see GameManager
 * @see ShopManager
 * @author Shaun Zhang
 */
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

    @FXML private AnchorPane GAME_OVER;

    private GameManager gameManager;

    /**
     * Sets gameManager.
     * @param gameManager
     */
    public void setGameManager(GameManager gameManager) {this.gameManager = gameManager;}

    /**
     * Only called on shop button click.
     * Switches scenes, and creates a shopManager.
     * Unclickable if currently in wave.
     */
    @FXML protected void SHOP_CLICK(){
        if (gameManager.getDowntime()){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/mapstob/group35/shopScreen.fxml"));
            try {
                    SceneManager.loadShop(gameManager);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Only called on main menu click. Opens main menu.
     * Unclickable if currently in wave.
     */
    @FXML protected void MAIN_MENU_CLICK(){
        //TODO
        if (gameManager.getDowntime()){
            System.out.println("MAIN_MENU_CLICK");
            try {
                SceneManager.loadMainMenu();
            }  catch (IOException e) {throw new RuntimeException(e);}
        }
    }

    public void GameOverScreen(boolean display) {
        GAME_OVER.setVisible(display);
        GAME_OVER.setDisable(!display);
    }

    /**
     * On pressing enter, will either: <br>
     * if not in wave:
     * start wave
     * if in wave:
     * send spelling attempt to gameManager
     */
    @FXML protected void TEXT_INPUTTED(){
        System.out.println("Text Inputted");
        if(gameManager.getDowntime()){
            try {
                gameManager.startWave();
                System.out.println("Started Wave");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            gameManager.spellingAttempt(TEXT_FIELD.getText());
            TEXT_FIELD.clear();
        }
    }

    @FXML protected void START_NEW_RUN(){
        //TODO
    }
    @FXML protected void MAIN_MENU_FROM_GAME_OVER(){
        try {
            SceneManager.loadMainMenu();
        }  catch (IOException e) {throw new RuntimeException(e);}
    }
    @FXML protected void EXIT_GAME(){
        SceneManager.closeProgram();
    }

    /**
     * Display next wave start hint
     */
    public void nextWaveText(){
        TEXT_FIELD.setText("Press enter to continue to a new wave.");
    }
    public void clearTextField(){
        TEXT_FIELD.clear();
    }

    /**
     * Changes button look and behaviour depending on if valid or not
     * @param valid
     */
    public void buttonValid(boolean valid){
        if (valid){
            SHOP_BUTTON.setStyle("-fx-background-color: #7D8570 ");
            SHOP_BUTTON.setMouseTransparent(false);
            MAIN_MENU_BUTTON.setStyle("-fx-background-color: #7D8570 ");
            MAIN_MENU_BUTTON.setMouseTransparent(false);
        }
        else{
            SHOP_BUTTON.setStyle("-fx-background-color: #7C7C7C ");
            MAIN_MENU_BUTTON.setStyle("-fx-background-color: #7C7C7C ");
            SHOP_BUTTON.setMouseTransparent(true);
            MAIN_MENU_BUTTON.setMouseTransparent(true);
        }
    }

    /**
     * Sets HP bar.
     * Called on every HP change.
     * @param currentHP
     * @param maxHP
     */
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

    /**
     * Sets time left bar.
     * Called on every time change.
     * @param currentTime
     * @param maxTime
     */
    public void setTIME_BAR(int currentTime,int maxTime) {
        //base width 200 base posx 13
        float percentage;
        if (maxTime==0){ percentage = 0f; }
        else { percentage = (float) (maxTime - currentTime) / (float) maxTime; }

        TIME_BAR.setWidth(percentage*200);
        TIME_BAR.setX((200-percentage*200));
        for (Node child : TIME_CONTAINER.getChildren()){
            if (child instanceof Text){
                ((Text) child).setText("Time left: "+ Integer.toString(maxTime - currentTime));
            }
        }
    }

    /**
     * Sets waves cleared text.
     * Called on every wave change.
     * @param waveNumber
     */
    public void setWaveText(int waveNumber){
        WAVE_TEXT.setText("WAVE: "+Integer.toString(waveNumber));
    }

    /**
     * Sets coins text.
     * Called on every coin change.
     * @param coinNumber
     */
    public void setCoinText(int coinNumber){
        COINS_TEXT.setText("COINS: "+Integer.toString(coinNumber));
    }

    /**
     * Sets scores text.
     * Called on every score text.
     * @param WPM
     * @param mistakes_percent
     * @param session_words
     * @param highscoreWPM
     * @param highscoreWaves
     */
    public void setScoreText(int WPM, int mistakes_percent, int session_words,int highscoreWPM, int highscoreWaves){
        WPM_TEXT.setText("WPM: "+Integer.toString(WPM));
        MISTAKES_TEXT.setText("MISTAKES: "+Integer.toString(mistakes_percent) + "%");
        WORDS_TEXT.setText("WORDS THIS ROUND: "+Integer.toString(session_words));
        HIGH_WPM_TEXT.setText("HIGHEST WPM: "+Integer.toString(highscoreWPM));
        HIGH_WAVE_TEXT.setText("HIGHEST WAVE: "+Integer.toString(highscoreWaves));
    }

    /**
     * Displays each individual text, as well as their progress bars.
     * Only displays the first 7 in list, anything more would be off screen.
     * Awful implementation, but JavaFX for some reason doesn't let things stack in reverse order. Thank you JavaFX!!! I hate you!!!
     * But i could also just be stupid and sleep deprived. Who knows.
     * @param textList
     */
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
                Group g = (Group) (((HBox) (children.get(7-i)))).getChildren().get(0);
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
