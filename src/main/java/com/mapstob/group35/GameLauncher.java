package com.mapstob.group35;

import com.mapstob.group35.Gameplay.GameGUIManager;
import com.mapstob.group35.Gameplay.TextUnit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Test launcher for gameplay.
 * @author Shaun
 */
public class GameLauncher extends Application{
    static GameGUIManager GUI;

    public static void main(String[] args) {
        Application.launch(GameLauncher.class, args);

    }

    @Override // BASED ON INTELIJ JAVAFX PRESET HELLOWORLD
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gamescreen.fxml")); // LOAD MAIN MENU FROM FXML
        Scene scene = new Scene(fxmlLoader.load(), 600, 400); // LOAD OUR FXML AS A SCENE
        GUI = fxmlLoader.getController();

        stage.setTitle("BotSpam");
        stage.setResizable(false);
        stage.setScene(scene); // SHOW SCENE
        stage.show();

        test();
    }



    public void test(){
        GUI.setHP_BAR(20,100);
        GUI.setTIME_BAR(69,200);
        GUI.setCoinText(69696969);
        GUI.setWaveText(69);

        ArrayList<TextUnit> textList = new ArrayList<TextUnit>();
        textList.add(new TextUnit("wawawa",23 ));
        textList.add(new TextUnit("placeholderrrr",16 ));
        textList.add(new TextUnit("globlabobalgab",10 ));
        for(int i = 0; i < 7; i++){
            textList.get(0).incTime();
            textList.get(1).incTime();
            textList.get(2).incTime();
        }
        GUI.setWordText(textList);
        GUI.setScoreText(69,45,5995,123,59);
    }

}
