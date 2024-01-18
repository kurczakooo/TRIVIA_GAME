package frontend_package;

import frontend_package.components.PlayerInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Instant;

public class EndingScreen {
    @FXML
    private PlayerInfo playerInfo;
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

            if(isHost) {
                TriviaGameApp.endingScreen.setPlayerInfoHost();
            }
            else TriviaGameApp.endingScreen.setPlayerInfoGuest();

        } catch (IOException e){
            e.printStackTrace();
        }
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
