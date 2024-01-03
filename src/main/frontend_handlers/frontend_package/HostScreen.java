package frontend_package;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class HostScreen {
    public boolean CzyGraczDrugiPolaczony;
    @FXML
    private Label hostPlayerLabel;
    @FXML
    private Label guestPlayerLabel;
    @FXML
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setLabels() {
        this.hostPlayerLabel.setText(TriviaGameApp.hostPlayer.nickname);
        if (this.CzyGraczDrugiPolaczony) {
            this.guestPlayerLabel.setText(TriviaGameApp.guestPlayer.nickname);
            this.guestPlayerLabel.setTextFill(Paint.valueOf("green"));
        }
        else {
            this.guestPlayerLabel.setText("Oczekiwanie na gracza 2...");
        }
    }

    public void setLabelsWithServers(String hostname, String guestname){
        this.hostPlayerLabel.setText(hostname);
        this.guestPlayerLabel.setText(guestname);
        this.guestPlayerLabel.setTextFill(Paint.valueOf("green"));
    }

    public void renderHostScreen(String fxmlFile, String cssFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssFile)).toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.setTitle("TRIVIA GAME");
        primaryStage.setScene(scene);
        primaryStage.show();

        TriviaGameApp.hostScreen = loader.getController();
        TriviaGameApp.hostScreen.setPrimaryStage(primaryStage);

        //TriviaGameApp.hostScreen.CzyGraczDrugiPolaczony = false;
        TriviaGameApp.hostScreen.setLabels();

        primaryStage.setOnCloseRequest(e-> TriviaGameApp.server.closeServer());
    }

    public void testowyhandler(ActionEvent actionEvent){
        // Dodać:
        // Wywolac metode do losowania kategorii i zwrocona mape zapisac w polu
        // Do przycisków przypisz dwa stringi z mapy - Stringi to wartości
        // Po kliknięciu przycisku weź klucz z wybranego Stringa i wyowłaj metode losującą pytanie, dając klucz jako argument

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("category_choice.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 1200, 800);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Styles.css")).toExternalForm());

            primaryStage.setResizable(false);
            primaryStage.setTitle("TRIVIA GAME");
            primaryStage.setScene(scene);
            primaryStage.show();

            ChoiceController choiceController = loader.getController();
            choiceController.setPrimaryStage(primaryStage);

            choiceController.IsLastQuestionRight = true;
            choiceController.setChoiceText();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
