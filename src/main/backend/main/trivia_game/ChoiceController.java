package main.trivia_game;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
}
