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
            delay.setOnFinished(e->{
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

    public static void main(String[] args) {
        launch(args);

        //Server server = new Server();
        //Gracz gracz = new Gracz("localhost", 4999);
        //server.sendMessageToPlayers(gracz.socket);
       // try {
            //gracz.socket.connect(server.socket.getLocalSocketAddress());
            //gracz.readMessage(gracz.socket);

            //server.socket.close();
            ///gracz.socket.close();
       // }
       // catch (IOException e){
       //     e.printStackTrace();
       // }

    }
}


//Connection dbConnection = DataBaseHandler.connect();
//DataBaseHandler.insertToDb(2,"Czemu jeszcz esse?", "el primo",
//        "Ser", "Clash royale", "Xddd");
//String col1 = DataBaseHandler.readFirstColumnValue("Pytania", "IDpytania");
//  System.out.println(col1);
