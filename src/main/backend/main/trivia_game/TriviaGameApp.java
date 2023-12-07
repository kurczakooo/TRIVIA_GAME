package main.trivia_game;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;

import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;

public class TriviaGameApp extends Application {
    public static void main(String[] args) {
        launch(args);

        // Connecting with database
        Connection dbConnection = DataBaseHandler.connect();
        //DataBaseHandler.insertToDb(2,"Czemu jeszcz esse?", "el primo",
        //        "Ser", "Clash royale", "Xddd");
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 1200, 800);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("main_menu.css")).toExternalForm());

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
