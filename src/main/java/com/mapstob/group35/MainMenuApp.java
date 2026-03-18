
package com.mapstob.group35;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller for BotSpam's Main Menu. Controls access to logging in, accessing the Tutorial, and viewing current leaderboard scores.
 * Implementation based on the InteliJ JavaFx
 * <br>
 * @author Ethan Gregory
 *
 */
public class MainMenuApp extends Application{
    @Override // BASED ON INTELIJ JAVAFX PRESET HELLOWORLD
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainMenu.fxml")); // LOAD MAIN MENU FROM FXML
        Scene scene = new Scene(fxmlLoader.load(), 600, 400); // LOAD OUR FXML AS A SCENE
        stage.setTitle("BotSpam");
        stage.setScene(scene); // SHOW SCENE
        stage.show();
    }

}
