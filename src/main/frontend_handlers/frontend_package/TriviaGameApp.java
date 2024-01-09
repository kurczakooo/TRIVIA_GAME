package frontend_package;

import database.DataBaseHandler;
import frontend_package.components.PlayerInfo;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.application.Application;
import server.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.util.Date;

public class TriviaGameApp extends Application {
    public static Server server;
    public static DataBaseHandler dataBaseHandler;
    public static Player hostPlayer;
    public static Player guestPlayer;
    public static PlayerInfo hostInfo;
    public static PlayerInfo guestInfo;
    public static MenuScreen menuScreen;
    public static HostScreen hostScreen;
    public static JoinScreen joinScreen;
    public static WaitForPlayerTurnScreen waitForPlayerTurnScreen;
    public static CategoryChoiceScreen categoryChoiceScreen;

    public Date matchDate;

    @Override
    public void start(Stage primaryStage) {
        setMenuScreen(primaryStage);
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public static void setMenuScreen(Stage primaryStage){
        try {
            menuScreen = new MenuScreen();
            menuScreen.setPrimaryStage(primaryStage);
            menuScreen.renderMenu("MenuScreen.fxml", "Styles.css");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}