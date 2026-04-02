package com.mapstob.group35;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameGUIManager {
    @FXML private Pane HP_CONTAINER;
    @FXML private Rectangle HP_BAR;
    @FXML private Pane TIME_CONTAINER;
    @FXML private Rectangle TIME_BAR;

    @FXML private VBox TEXT_LIST;
    @FXML private VBox SCORE_CONTAINER;
    @FXML private Text WAVE_TEXT;
    @FXML private Text COIN_TEXT;
    @FXML private TextField TEXT_FIELD;

    @FXML private Button SHOP_BUTTON;
    @FXML private Button MAIN_MENU_BUTTON;

    public void setHP_BAR(int currentHP,int maxHP) {
        //base width 200 base posx 13
        HP_BAR.setWidth(currentHP*2);
        HP_BAR.setX(13 + currentHP*2);
        HP_CONTAINER.getChildren();
    }


}
