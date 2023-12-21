package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.io.IOException;


public class PlayController {
    boolean CzyGraczDrugiPolaczony;///////////
    @FXML
    private Label waiting;
    @FXML
    private Button PlayButton;
    @FXML
    private TextField nickbox;

    private String nick;


    public void setWaitingText() {
        if (this.CzyGraczDrugiPolaczony) {
            this.waiting.setText("Gracz 2 dołączył!");
            this.waiting.setTextFill(Paint.valueOf("green"));
            this.PlayButton.setDisable(false);
        }
        else {
            this.waiting.setText("Oczekiwanie na gracza 2...");
            //this.PlayButton.setDisable(true);
        }
    }

    @FXML
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public void onPlayButtonClick(ActionEvent actionEvent) {
        nick = nickbox.getText();
        if(nick.isEmpty()){
            nickbox.setPromptText("WPISZ NICK!!");
        }
        else{
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
}