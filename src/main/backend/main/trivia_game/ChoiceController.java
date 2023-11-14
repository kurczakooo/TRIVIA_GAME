package main.trivia_game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ChoiceController {

    @FXML
    private Stage primaryStage;
    @FXML
    private Label choiceText;
    public boolean IsLastQuestionRight;

    @FXML
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @FXML
    public void setChoiceText() {
        if(this.IsLastQuestionRight)
            this.choiceText.setText("Choose a category for your yourself:");
        else
            this.choiceText.setText("Choose a category for your opponent:");
    }

    public void ChoiceHandler(ActionEvent actionEvent){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("question.fxml"));
            Parent root = loader.load();

            Scene choiceScene = new Scene(root, 1200, 800);
            choiceScene.getStylesheets().add(getClass().getResource("question.css").toExternalForm());
            primaryStage.setResizable(false);
            primaryStage.setTitle("TRIVIA GAME");
            primaryStage.setScene(choiceScene);

            QuestionController questionController = loader.getController();
            questionController.setPrimaryStage(primaryStage);

            questionController.GetDataFromDB();
            questionController.RandomizeAnswers();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
