package frontend_package;

import frontend_package.components.ServerInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import server.Player;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class JoinScreen {
    @FXML
    public Label playerNick;
    @FXML
    private Label playerPrize;
    @FXML
    ArrayList<ServerInfo> serversList;
    private Stage primaryStage;

    public void setPlayerNick(){
        this.playerNick.setText(TriviaGameApp.guestPlayer.nickname);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void renderJoinScreen(String fxmlFile, String cssFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssFile)).toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.setTitle("TRIVIA GAME");
        primaryStage.setScene(scene);
        primaryStage.show();

        TriviaGameApp.joinScreen = loader.getController();
        TriviaGameApp.joinScreen.setPrimaryStage(primaryStage);

        TriviaGameApp.joinScreen.setPlayerNick();
    }

    public void generateServersInfo(Socket guestSocket) throws IOException{
        /*for(int portNumber=5000 ; portNumber<=6000 ; portNumber++){
            if(Server.isPortAvailable(portNumber)){
                continue;
            }
            if(!Server.isPortAvailable(portNumber)){
                try {
                    Socket tmpSocket = new Socket("localhost", portNumber);

                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }*/
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(guestSocket.getInputStream()));
        String buffer = bufferedReader.readLine();
        System.out.println(buffer);
        //socket.close();
    }

    public void testowyhandler(ActionEvent actionEvent){
        try {
            ChoiceController choiceController = new ChoiceController();
            choiceController.setPrimaryStage(primaryStage);
            choiceController.renderChoiceScreen("category_choice.fxml", "Styles.css");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}


