package frontend_package;

import frontend_package.components.PlayerInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class WaitForPlayerTurnScreen {
    @FXML
    public PlayerInfo playerInfo;
    @FXML
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void renderWaitScreen(String fxmlFile, String cssFile) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssFile)).toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.setTitle("TRIVIA GAME");
        primaryStage.setScene(scene);
        primaryStage.show();

        TriviaGameApp.waitForPlayerTurnScreen = loader.getController();
        TriviaGameApp.waitForPlayerTurnScreen.setPrimaryStage(primaryStage);
        setPlayerInfoHost();
    }

    public void setPlayerInfoHost(){
        TriviaGameApp.waitForPlayerTurnScreen.playerInfo.setPlayerNick(TriviaGameApp.hostPlayer.nickname);
        TriviaGameApp.waitForPlayerTurnScreen.playerInfo.setPrize(TriviaGameApp.hostPlayer.Prize);
    }

    public void setPlayerInfoGuest(){
        TriviaGameApp.waitForPlayerTurnScreen.playerInfo.setPlayerNick(TriviaGameApp.guestPlayer.nickname);
        TriviaGameApp.waitForPlayerTurnScreen.playerInfo.setPrize(TriviaGameApp.guestPlayer.Prize);
    }
}
