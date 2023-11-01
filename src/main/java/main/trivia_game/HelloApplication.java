package main.trivia_game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

///BAWILEM SIE TROCHE TYLKO SCENE BUILDER, CHCE STWORZYC PROSTE MENU

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main_menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);

        scene.getStylesheets().add(getClass().getResource("main_menu.css").toExternalForm());

        stage.setTitle("TRIVIA GAME");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}