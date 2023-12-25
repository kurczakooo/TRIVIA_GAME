package frontend_package;

import database.DataBaseHandler;
import frontend_package.PlayController;
import javafx.animation.PauseTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.util.Duration;
import server.Gracz;
import server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.util.Objects;

public class TriviaGameApp extends Application {

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


            PauseTransition delay = new PauseTransition(Duration.seconds(1));  //symulacja czekania na 2 gracza
            delay.setOnFinished(e -> {
                playController.CzyGraczDrugiPolaczony = true;
                System.out.println(playController.CzyGraczDrugiPolaczony);
                playController.setWaitingText();
            });
            playController.CzyGraczDrugiPolaczony = false;
            //System.out.println(playController.CzyGraczDrugiPolaczony);
            delay.play();
            playController.setWaitingText();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);

        Thread serverThread = new Thread(server);
        serverThread.start();

        launch(args);

    }
}