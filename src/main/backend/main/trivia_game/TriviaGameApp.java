package main.trivia_game;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;

import java.io.IOException;

public class TriviaGameApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 1200, 800);
            scene.getStylesheets().add(getClass().getResource("main_menu.css").toExternalForm());

            primaryStage.setResizable(false);
            primaryStage.setTitle("TRIVIA GAME");
            primaryStage.setScene(scene);
            primaryStage.show();

            PlayController playController = loader.getController();
            playController.setPrimaryStage(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
