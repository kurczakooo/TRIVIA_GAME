package frontend_package;

import database.DataBaseHandler;
import database.Tables.TablesManagement;
import frontend_package.components.PlayerInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Statement;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


public class CategoryChoiceScreen {
    @FXML
    public PlayerInfo playerInfo;
    @FXML
    private Stage primaryStage;
    @FXML
    private Label choiceText;
    @FXML
    private Button categoryButton1;
    @FXML
    private Button categoryButton2;
    public boolean IsLastQuestionRight;
    private boolean isHost;

    @FXML
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @FXML
    public void setChoiceText() {
        if(this.IsLastQuestionRight)
            this.choiceText.setText("Wybierz kategorie pytania dla siebie:");
        else
            this.choiceText.setText("Wybierz kategorie pytania dla przeciwnika:");
    }

    public void renderChoiceScreen(String fxmlFile, String cssFile, boolean isHost, boolean wasLastQuestionRight) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssFile)).toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.setTitle("TRIVIA GAME");
        primaryStage.setScene(scene);
        primaryStage.show();

        TriviaGameApp.categoryChoiceScreen = loader.getController();
        TriviaGameApp.categoryChoiceScreen.setPrimaryStage(primaryStage);

        TriviaGameApp.categoryChoiceScreen.assignCategories();

        TriviaGameApp.categoryChoiceScreen.IsLastQuestionRight = wasLastQuestionRight;
        TriviaGameApp.categoryChoiceScreen.setChoiceText();

        if(isHost) {
            TriviaGameApp.categoryChoiceScreen.setPlayerInfoHost();
            TriviaGameApp.categoryChoiceScreen.isHost = true;
        }
        else TriviaGameApp.categoryChoiceScreen.setPlayerInfoGuest();
    }

    public void ChoiceHandler(ActionEvent actionEvent){
        Button clickedButton = (Button) actionEvent.getSource();
        String chosenCategory = clickedButton.getText();

        TriviaGameApp.questionScreen = new QuestionScreen();
        TriviaGameApp.questionScreen.setPrimaryStage(TriviaGameApp.categoryChoiceScreen.primaryStage);
        TriviaGameApp.questionScreen.renderQuestionScreen("QuestionScreen.fxml", "Styles.css",
                TriviaGameApp.categoryChoiceScreen.isHost, chosenCategory);
    }

    public void setPlayerInfoHost(){
        TriviaGameApp.categoryChoiceScreen.playerInfo.setPlayerNick(TriviaGameApp.hostPlayer.nickname);
        TriviaGameApp.categoryChoiceScreen.playerInfo.setPrize(TriviaGameApp.hostPlayer.Prize);
    }

    public void setPlayerInfoGuest(){
        TriviaGameApp.categoryChoiceScreen.playerInfo.setPlayerNick(TriviaGameApp.guestPlayer.nickname);
        TriviaGameApp.categoryChoiceScreen.playerInfo.setPrize(TriviaGameApp.guestPlayer.Prize);
    }

    private void assignCategories(){
        DataBaseHandler.connect();
        try {
            String[] categories = TablesManagement.twoCategoriesFromKategorie();
            categoryButton1.setText(categories[0]);
            categoryButton2.setText(categories[1]);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
