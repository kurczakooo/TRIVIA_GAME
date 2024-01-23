package server;

import database_package.Tables.HisTurTmpHandler;
import database_package.Tables.TablesManagement;
import frontend_package.CategoryChoiceScreen;
import frontend_package.EndingScreen;
import frontend_package.TriviaGameApp;
import frontend_package.WaitForPlayerTurnScreen;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ScreensManagerForServer {

    public static int roundNumber;

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
                ScreensManagerForServer.roundNumber++;

                waitForEndTurnSignalFromGuest();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    private static void waitForEndTurnSignalFromGuest(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    String msg = TriviaGameApp.server.bufferedReader.readLine();
                    if(msg.equals("hostTurn")) {
                        timer.cancel();
                        hostTurn();
                    }
                    else{
                        timer.cancel();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer.schedule(timerTask, 0, 500);
    }

    private static void waitForEndTurnSignalFromHost(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    String msgFromHost = TriviaGameApp.server.bufferedReaderForHost.readLine();
                    if(msgFromHost.equals("guestTurn")){
                        timer.cancel();
                        try {
                            TriviaGameApp.server.bufferedWriter.write("guestTurn\n");
                            TriviaGameApp.server.bufferedWriter.flush();
                            System.out.println("wyslano wiadomosc do goscia");
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    else if(msgFromHost.equals("EndOfGame")){
                        timer.cancel();
                        try {
                            TriviaGameApp.server.bufferedWriter.write("EndOfGame;" + TriviaGameApp.hostPlayer.Prize + "\n");
                            TriviaGameApp.server.bufferedWriter.flush();
                            System.out.println("wyslano wiadomosc konczaca gre do goscia");

                            String guestPrize = TriviaGameApp.server.bufferedReader.readLine();

                            System.out.println("\nKOINIECGRRY HOST SCREEN\n" + TriviaGameApp.hostPlayer.Prize);

                            TablesManagement.fetchFromHisTurTmp();
                            HisTurTmpHandler.deleteAllRows();
                            System.out.println("STATY " + TriviaGameApp.hostPlayer.nickname + " najHajs:" + TriviaGameApp.hostPlayer.biggestWin + " najOdp:" + TriviaGameApp.hostPlayer.FastestAnswer);

                            Platform.runLater(() -> {
                                TriviaGameApp.guestPlayer.Prize = Integer.parseInt(guestPrize);
                                TriviaGameApp.endingScreen = new EndingScreen();
                                TriviaGameApp.endingScreen.setPrimaryStage(TriviaGameApp.questionScreen.primaryStage);
                                TriviaGameApp.endingScreen.renderEndingScreen("EndingScreen.fxml", "Styles.css", true);
                            });
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    else{
                        timer.cancel();
                        throw new RuntimeException();
                    }
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
                ScreensManagerForServer.roundNumber++;
                if(ScreensManagerForServer.roundNumber == 10) {
                    TriviaGameApp.server.bufferedWriter.write("guestTurnLast\n");
                    TriviaGameApp.server.bufferedWriter.flush();
                }
                ScreensManagerForServer.waitForEndTurnSignalFromHost();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
    }
}
