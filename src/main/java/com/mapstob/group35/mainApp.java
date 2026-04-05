
package com.mapstob.group35;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Interface for opening BotSpam. This should open directly to the base menu.
 * <br><br>
 * Implementation is a slightly modified InteliJ JavaFX Hello World Preset, created when initializing the project.
 * @author InteliJ, Ethan Gregory
 *
 */
public class mainApp extends Application{
    @Override // BASED ON INTELIJ JAVAFX PRESET HELLOWORLD
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("baseMenu.fxml")); // LOAD MAIN MENU FROM FXML
        Scene scene = new Scene(fxmlLoader.load(), 600, 400); // LOAD OUR FXML AS A SCENE
        SceneManager.BaseScene = scene; // LOAD SCENE INTO BASE SCENE
        stage.setTitle("BotSpam");
        stage.setResizable(false);
        stage.setScene(scene); // SHOW SCENE
        stage.show();
    }

}
