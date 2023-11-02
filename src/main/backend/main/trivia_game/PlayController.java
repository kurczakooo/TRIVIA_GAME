package main.trivia_game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class PlayController {

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