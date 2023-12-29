package frontend_package;

import database.DataBaseHandler;
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
    public static MenuScreen menuScreen;
    public static HostScreen hostScreen;
    public static JoinScreen joinScreen;

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

    public static void setServerOnPort()throws IOException{
        int portNumber = 5000;
        while(portNumber <= 6000){
            if(Server.isPortAvailable(portNumber)) {
                createServer(portNumber);
                break;
            }
            else
                portNumber++;

            if (portNumber == 6000)
                System.out.println("wszystkie serwery zajete");
        }
    }

    public static void createServer(int port) throws IOException{
        ServerSocket serverSocket = new ServerSocket(port);
        server = new Server(serverSocket, hostPlayer);
        System.out.println(serverSocket.getLocalPort());
        Thread thread = new Thread(server);
        thread.start();
    }

}