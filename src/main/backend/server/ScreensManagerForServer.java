package server;

import frontend_package.CategoryChoiceScreen;
import frontend_package.TriviaGameApp;
import frontend_package.WaitForPlayerTurnScreen;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

import java.io.IOException;

public class ScreensManagerForServer {

    public static void setHostScreenLabels(Player guestPlayer, String hostNick, String guestNick){
        Platform.runLater(() ->{
            TriviaGameApp.guestPlayer = guestPlayer;
            TriviaGameApp.hostScreen.CzyGraczDrugiPolaczony = true;
            TriviaGameApp.hostScreen.setLabelsWithServers(hostNick, guestNick);
        });
    }

    public static void startGame(String signal) throws Exception{
        if(signal.equals("start")){
            TriviaGameApp.hostScreen.guestButton.setSelected(true);
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1.0));
            pauseTransition.setOnFinished(e -> ScreensManagerForServer.setWaitScreen());
            pauseTransition.play();
        }
        else throw new Exception("Blad w rozpoczeciu gry");
    }

    public static void setWaitScreen(){
        Platform.runLater(() ->{
            try {
                TriviaGameApp.waitForPlayerTurnScreen = new WaitForPlayerTurnScreen();
                TriviaGameApp.waitForPlayerTurnScreen.setPrimaryStage(TriviaGameApp.hostScreen.getPrimaryStage());
                TriviaGameApp.waitForPlayerTurnScreen.renderWaitScreen("WaitForPlayerTurnScreen.fxml", "Styles.css", true);
                String msg = TriviaGameApp.server.bufferedReader.readLine();
                if(msg.equals("hostTurn"))
                    ScreensManagerForServer.hostTurn();
                else if(msg.equals("guestChoosinCategoryForHost")){
                    System.out.println("gosc wybiera kategorie dla hosta");
                }
                else throw new RuntimeException();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    private static void hostTurn(){
        try {
            TriviaGameApp.categoryChoiceScreen = new CategoryChoiceScreen();
            TriviaGameApp.categoryChoiceScreen.setPrimaryStage(TriviaGameApp.waitForPlayerTurnScreen.primaryStage);
            TriviaGameApp.categoryChoiceScreen.renderChoiceScreen("CategoryChoiceScreen.fxml", "Styles.css", true, true);
            TriviaGameApp.categoryChoiceScreen.setPlayerInfoHost();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
