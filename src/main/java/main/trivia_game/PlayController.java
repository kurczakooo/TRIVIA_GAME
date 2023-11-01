package main.trivia_game;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlayController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onPlayButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}