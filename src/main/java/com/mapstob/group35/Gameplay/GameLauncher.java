package com.mapstob.group35.Gameplay;

import com.mapstob.group35.GameGUIManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Interface for opening BotSpam. This should open directly to the main menu.
 * <br><br>
 * Implementation is a slightly modified InteliJ JavaFX Hello World Preset, created when initializing the project.
 * @author InteliJ, Ethan Gregory
 *
 */
public class GameLauncher extends Application{
    static GameGUIManager GUI;

    public static void main(String[] args) {
        GUI = new GameGUIManager();
        Application.launch(GameLauncher.class, args);

    }

    @Override // BASED ON INTELIJ JAVAFX PRESET HELLOWORLD
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gamescreen.fxml")); // LOAD MAIN MENU FROM FXML
        Scene scene = new Scene(fxmlLoader.load(), 600, 400); // LOAD OUR FXML AS A SCENE
        stage.setTitle("BotSpam");
        stage.setScene(scene); // SHOW SCENE
        stage.show();

    }

    public void test(){
        GUI.setHP_BAR(50,100);
    }

}
