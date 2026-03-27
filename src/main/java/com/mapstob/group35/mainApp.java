
package com.mapstob.group35;

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
public class mainApp extends Application{
    @Override // BASED ON INTELIJ JAVAFX PRESET HELLOWORLD
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainMenu.fxml")); // LOAD MAIN MENU FROM FXML
        Scene scene = new Scene(fxmlLoader.load(), 600, 400); // LOAD OUR FXML AS A SCENE
        stage.setTitle("BotSpam");
        stage.setScene(scene); // SHOW SCENE
        stage.show();
    }

}
