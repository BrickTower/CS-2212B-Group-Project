package com.mapstob.group35;

import com.mapstob.group35.Gameplay.GameGUIManager;
import com.mapstob.group35.Gameplay.GameManager;
import com.mapstob.group35.Gameplay.ShopManager;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

/**
 * Utility class used to easily handle switching scenes, calling specific necessary tricks for setting up the corresponding scene.
 * <p></p>
 * The original Scene of the application must be set to BaseScene before any work is done, as it is key to changing the scenes around.
 *
 * @version 1
 * @author Shaun Zhang
 * @author Tamaki Konan
 */
public class SceneManager {
    static Scene BaseScene;

    static public mainController loadMainMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/com/mapstob/group35/mainMenu.fxml"));
        BaseScene.setRoot(fxmlLoader.load());
        mainController controller = fxmlLoader.getController();
        return controller;
    }

    static public GameGUIManager loadGame(GameManager gameManager) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/com/mapstob/group35/gamescreen.fxml"));
        BaseScene.setRoot(fxmlLoader.load());
        GameGUIManager GUI = fxmlLoader.getController();
        GUI.setGameManager(gameManager);
        gameManager.initGUI(GUI);
        return GUI;
    }
    static public ShopManager loadShop(GameManager gameManager) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/com/mapstob/group35/shopScreen.fxml"));
        BaseScene.setRoot(fxmlLoader.load());

        ShopManager shop = fxmlLoader.getController();
        gameManager.setShopManager(shop);
        shop.setGameManager(gameManager);
        shop.initShop();
        return shop;
    }

    //TODO Below!!!
    static public void loadStats() throws IOException{
        try {
            FXMLLoader load = new FXMLLoader(SceneManager.class.getResource("/com/mapstob/group35/stats.fxml"));
            Parent root = load.load();
            BaseScene.setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static public tutorialController loadTutorial() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/com/mapstob/group35/tutorialScreen.fxml"));
        BaseScene.setRoot(fxmlLoader.load());

        tutorialController controller = fxmlLoader.getController();
        return controller;
    }
    static public parentalController loadParentalControls() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/com/mapstob/group35/parentalScreen.fxml"));
        BaseScene.setRoot(fxmlLoader.load());

        parentalController setup = fxmlLoader.getController();
        setup.getParentsPlayers(); // POPULATE LIST WITH MAIN OPTIONS
        return setup;
    }
    static public void closeProgram() {
        Platform.exit(); // CLOSE GAME
    }

}
