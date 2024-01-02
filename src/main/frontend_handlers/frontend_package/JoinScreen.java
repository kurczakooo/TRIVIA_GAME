package frontend_package;

import frontend_package.components.ServerInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    public VBox containerForServersList;
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
        TriviaGameApp.joinScreen.generateServersInfo(TriviaGameApp.guestPlayer.socket);
        //System.out.println(TriviaGameApp.joinScreen.containerForServersList.getChildren());
    }

    public void generateServersInfo(Socket guestSocket) throws IOException{
        for(int portNumber=5000 ; portNumber<=5020 ; portNumber++){
            if(!Server.isPortAvailable(portNumber)){
                Socket tmpSocket = new Socket("localhost", portNumber);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(tmpSocket.getInputStream()));
                String buffer = bufferedReader.readLine();
                String[] msg = buffer.split("`");
                //System.out.println(buffer);
                ServerInfo serverInfo = new ServerInfo(msg[0], Integer.parseInt(msg[1]), actionEvent -> joinButtonHandler(actionEvent));
                TriviaGameApp.joinScreen.containerForServersList.getChildren().add(serverInfo);
                tmpSocket.close();
           }
           //else System.out.println("nie ma servera na tym porcie");
        }
        //System.out.println(JoinScreen.containerForServersList.getChildren());
    }

    private void joinButtonHandler(ActionEvent actionEvent){
        if(actionEvent.getSource() instanceof Button){
            Button clickedButton = (Button) actionEvent.getSource();
            ServerInfo serverInfo = (ServerInfo) clickedButton.getParent();

            try{
                Socket socket = new Socket("localhost", Integer.parseInt(serverInfo.portNumber.getText()));
                TriviaGameApp.guestPlayer.setSocket(socket);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void refresh(ActionEvent actionEvent){
    //KIEDYS WROCIC DO TEGO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        try {
            TriviaGameApp.joinScreen.renderJoinScreen("JoinScreen.fxml", "Styles.css");
        }
        catch (IOException e){
            e.printStackTrace();
        }
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


