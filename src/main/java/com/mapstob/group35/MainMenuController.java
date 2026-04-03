package com.mapstob.group35;

import com.mapstob.group35.core.ParentProfile;
import com.mapstob.group35.core.PlayerProfile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class MainMenuController {
    @FXML
    private Button logIn;
    private Button quit;
    private Button leaderboard;
    private Button tutorial;
    @FXML
    private VBox leaderboardBox;


    public void initialize() {
        updateLeaderboard();
    }

    private void updateLeaderboard() {
        List<Object> users = DataManager.getAllUsersSortedByWaves();

        leaderboardBox.getChildren().remove(2, leaderboardBox.getChildren().size());

        int rank = 1;

        for (Object u : users) {
            String name;
            int wave;

            if (u instanceof PlayerProfile p) {
                name = p.getUserName();
                wave = p.getPlayerStats().getPeakWaves();
            } else {
                ParentProfile p = (ParentProfile) u;
                name = p.getUserName();
                wave = p.getPlayerStats().getPeakWaves();
            }

            HBox row = new HBox();
            row.setSpacing(10);

            Text nameText = new Text(rank + ". " + name);
            nameText.setFill(Color.WHITE);
            nameText.setFont(Font.font("Consolas", 12));

            Text waveText = new Text(String.valueOf(wave));
            waveText.setFill(Color.WHITE);
            waveText.setFont(Font.font("Consolas", 12));

            row.getChildren().addAll(nameText, waveText);
            leaderboardBox.getChildren().add(row);

            rank++;
            if (rank > 10) break;
        }
    }


    @FXML
    private void onLogInClick() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login_Page.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) logIn.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
