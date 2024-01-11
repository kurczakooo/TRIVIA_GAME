package server;

import frontend_package.CategoryChoiceScreen;
import frontend_package.TriviaGameApp;
import frontend_package.WaitForPlayerTurnScreen;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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
            pauseTransition.setOnFinished(e -> setWaitScreen());
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
                TriviaGameApp.waitForPlayerTurnScreen.setWaitTextAsWaitForYourTurn();

                waitForEndTurnSignal();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    private static void waitForEndTurnSignal(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    String msg = TriviaGameApp.server.bufferedReader.readLine();
                    if(msg.equals("hostTurn"))
                        hostTurn();
                    else if(msg.equals("guestChoosinCategoryForHost")){
                        Platform.runLater(() -> {
                            TriviaGameApp.waitForPlayerTurnScreen.setWaitTextAsOpponentChoosinCateogry();
                        });
                    }
                    else throw new RuntimeException();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer.schedule(timerTask, 0, 500);
    }

    private static void hostTurn(){
        Platform.runLater(() -> {
            try {
                TriviaGameApp.categoryChoiceScreen = new CategoryChoiceScreen();
                TriviaGameApp.categoryChoiceScreen.setPrimaryStage(TriviaGameApp.waitForPlayerTurnScreen.primaryStage);
                TriviaGameApp.categoryChoiceScreen.renderChoiceScreen("CategoryChoiceScreen.fxml", "Styles.css", true, true);
                TriviaGameApp.categoryChoiceScreen.setPlayerInfoHost();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
    }
}
