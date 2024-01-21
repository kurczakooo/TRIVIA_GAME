package frontend_package;

import frontend_package.components.PlayerInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Instant;

public class EndingScreen {
    @FXML
    private PlayerInfo playerInfo;
    @FXML
    private Label verdictLabel;
    @FXML
    private Label yourNick;
    @FXML
    private Label yourPrize;
    @FXML
    private Label opponentNick;
    @FXML
    private Label opponentPrize;
    @FXML
    private Button exitButton;

    private boolean isHost;
    @FXML
    private Stage primaryStage;

    @FXML
    public void setPrimaryStage(Stage PrimaryStage) {this.primaryStage = PrimaryStage;}

    public void renderEndingScreen(String fxmlFile, String cssFile, boolean isHost){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Scene choiceScene = new Scene(root, 1200, 800);
            choiceScene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
            primaryStage.setResizable(false);
            primaryStage.setTitle("TRIVIA GAME");
            primaryStage.setScene(choiceScene);
            primaryStage.show();

            TriviaGameApp.endingScreen = loader.getController();
            TriviaGameApp.endingScreen.setPrimaryStage(primaryStage);

            TriviaGameApp.endingScreen.isHost = isHost;

            if(isHost) {
                TriviaGameApp.endingScreen.setPlayerInfoHost();
            }
            else TriviaGameApp.endingScreen.setPlayerInfoGuest();

            TriviaGameApp.endingScreen.setPodiumLabels(isHost);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setPodiumLabels(boolean isHost) throws Exception{
        if(TriviaGameApp.hostPlayer.Prize > TriviaGameApp.guestPlayer.Prize){
            if(isHost) {
                verdictLabel.setText("WYGRAŁEŚ!");
                TriviaGameApp.endingScreen.setLabelsIfYouAreHost();
            }
            else{
                verdictLabel.setText("PRZEGRAŁEŚ!");
                TriviaGameApp.endingScreen.seLabelsIfYouAreGuest();
            }
        }
        else if(TriviaGameApp.hostPlayer.Prize < TriviaGameApp.guestPlayer.Prize){
            if(isHost) {
                verdictLabel.setText("PRZEGRAŁEŚ!");
                TriviaGameApp.endingScreen.setLabelsIfYouAreHost();
            }
            else{
                verdictLabel.setText("WYGRAŁEŚ!");
                TriviaGameApp.endingScreen.seLabelsIfYouAreGuest();
            }
        }
        else throw new Exception("REMIS JAK ??????? XDDDDDDDDDDD");
    }

    public void onExitGameButtonClick(ActionEvent actionEvent){
        if (isHost){
            TriviaGameApp.hostPlayer.disconnectPlayer();
            TriviaGameApp.server.closeServer();
        }
        else TriviaGameApp.guestPlayer.disconnectPlayer();

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    private void setLabelsIfYouAreHost(){
        yourNick.setText(TriviaGameApp.hostPlayer.nickname);
        yourPrize.setText(Integer.toString(TriviaGameApp.hostPlayer.Prize) + "$");

        opponentNick.setText(TriviaGameApp.guestPlayer.nickname);
        opponentPrize.setText(Integer.toString(TriviaGameApp.guestPlayer.Prize) + "$");
    }

    private void seLabelsIfYouAreGuest(){
        yourNick.setText(TriviaGameApp.guestPlayer.nickname);
        yourPrize.setText(Integer.toString(TriviaGameApp.guestPlayer.Prize) + "$");

        opponentNick.setText(TriviaGameApp.hostPlayer.nickname);
        opponentPrize.setText(Integer.toString(TriviaGameApp.hostPlayer.Prize) + "$");
    }


    public void setPlayerInfoHost(){
        TriviaGameApp.endingScreen.playerInfo.setPlayerNick(TriviaGameApp.hostPlayer.nickname);
        TriviaGameApp.endingScreen.playerInfo.setPrize(TriviaGameApp.hostPlayer.Prize);
    }

    public void setPlayerInfoGuest(){
        TriviaGameApp.endingScreen.playerInfo.setPlayerNick(TriviaGameApp.guestPlayer.nickname);
        TriviaGameApp.endingScreen.playerInfo.setPrize(TriviaGameApp.guestPlayer.Prize);
    }
}
