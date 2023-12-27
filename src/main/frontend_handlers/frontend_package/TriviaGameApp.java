package frontend_package;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.application.Application;
import server.*;
import java.io.IOException;
import java.util.Date;

public class TriviaGameApp extends Application {
    public Server server;
    public static Player hostPlayer;
    public Player guestPlayer;
    public static MenuScreen menuScreen;
    public HostScreen hostScreen;

    public Date matchDate;

    @Override
    public void start(Stage primaryStage) {
        test(primaryStage);
    }

    public static void test(Stage primaryStage){
        try {
            //hostPlayer = new Player("debil");
            menuScreen = new MenuScreen();
            menuScreen.setPrimaryStage(primaryStage);
            menuScreen.renderMenu("MenuScreen.fxml", "Styles.css");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}