package main.trivia_game;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class PlayController {
    boolean CzyGraczDrugiPolaczony;///////////
    @FXML
    private Label waiting;
    @FXML
    private Button PlayButton;


    public void setWaitingText() {
        if (this.CzyGraczDrugiPolaczony) {
            this.waiting.setText("Gracz 2 dołączył \uD83D\uDC4D");
            this.PlayButton.setDisable(false);
        }
        else {
            this.waiting.setText("Oczekiwanie na gracza 2...");
            this.PlayButton.setDisable(true);
        }
    }

    @FXML
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public void onPlayButtonClick(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("category_choice.fxml"));
            Parent root = loader.load();

            Scene choiceScene = new Scene(root, 1200, 800);
            choiceScene.getStylesheets().add(getClass().getResource("category_choice.css").toExternalForm());
            primaryStage.setResizable(false);
            primaryStage.setTitle("TRIVIA GAME");
            primaryStage.setScene(choiceScene);

            ChoiceController choiceController = loader.getController();
            choiceController.setPrimaryStage(primaryStage);

            choiceController.IsLastQuestionRight = true; ///ustawiamy zeby pierwszy wybor nalezal dla samego siebie
            choiceController.setChoiceText();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}