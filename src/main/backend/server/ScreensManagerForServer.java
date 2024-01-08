package server;

import frontend_package.TriviaGameApp;
import frontend_package.WaitForPlayerTurnScreen;
import javafx.application.Platform;

import java.io.IOException;

public class ScreensManagerForServer {

    public static void setHostScreenLabels(Player guestPlayer, String hostNick, String guestNick){
        Platform.runLater(() ->{
            TriviaGameApp.guestPlayer = guestPlayer;
            TriviaGameApp.hostScreen.CzyGraczDrugiPolaczony = true;
            TriviaGameApp.hostScreen.setLabelsWithServers(hostNick, guestNick);
        });
    }

    public static void startGame(String signal){
        if(signal.equals("start")){
            Platform.runLater(() ->{
                TriviaGameApp.hostScreen.guestButton.setSelected(true);
                try {
                    TriviaGameApp.waitForPlayerTurnScreen = new WaitForPlayerTurnScreen();
                    TriviaGameApp.waitForPlayerTurnScreen.setPrimaryStage(TriviaGameApp.hostScreen.getPrimaryStage());
                    TriviaGameApp.waitForPlayerTurnScreen.playerInfo = TriviaGameApp.hostScreen.playerInfo;
                    TriviaGameApp.waitForPlayerTurnScreen.renderWaitScreen("WaitForPlayerTurnScreen.fxml", "Styles.css");
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            });
        }
        else System.out.println("pewnie jest znak nowej lini");
    }

}
