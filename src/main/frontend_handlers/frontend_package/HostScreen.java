package frontend_package;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import server.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Objects;

public class HostScreen extends TriviaGameApp{
    public boolean isGuestConnected;
    @FXML
    public Label hostPlayerLabel;
    @FXML
    public Label guestPlayerLabel;
    @FXML
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setLabels() {
        if (isGuestConnected) {
            //guestPlayerLabel.setText(guestPlayer.nickname);
        }
        else {
            hostPlayerLabel.setText(TriviaGameApp.hostPlayer.nickname);
            guestPlayerLabel.setText("Oczekiwanie na gracza 2...");
        }
        System.out.println(TriviaGameApp.hostPlayer.nickname);
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

        hostScreen = loader.getController();
        hostScreen.setPrimaryStage(primaryStage);

        hostScreen.isGuestConnected = false;
        hostScreen.setLabels();

        /*PauseTransition delay = new PauseTransition(Duration.seconds(1));  //symulacja czekania na 2 gracza
        delay.setOnFinished(e -> {
            playController.CzyGraczDrugiPolaczony = true;
            System.out.println(playController.CzyGraczDrugiPolaczony);
            playController.setWaitingText();
        });
        playController.CzyGraczDrugiPolaczony = false;
        System.out.println(playController.CzyGraczDrugiPolaczony);
        delay.play();
        playController.setWaitingText();*/
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        server = new Server(serverSocket);
        Thread thread = new Thread(server);
        thread.start();

        primaryStage.setOnCloseRequest(e -> server.closeServer()); //jak zamkniesz okno to server sie zamyka i watek razem z nim bo nie ma co robic juz
    }

}
