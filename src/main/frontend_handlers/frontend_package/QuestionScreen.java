package frontend_package;

import database.DataBaseHandler;
import database.Tables.HisTurTmpHandler;
import database.Tables.KategoriePytanHandler;
import database.Tables.PytaniaHandler;
import database.Tables.TablesManagement;
import frontend_package.components.PlayerInfo;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import server.ScreensManagerForServer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class QuestionScreen {
    @FXML
    public PlayerInfo playerInfo;
    @FXML
    private Label question_label;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Label counter;
    @FXML
    public Stage primaryStage;
    public ArrayList<String> answers;
    private String right_answer;
    private String question;
    private Instant start;
    private Instant answer;
    private Duration answerTime;
    private Timer timer;
    private boolean isHost;

    public QuestionScreen(){this.answers = new ArrayList<>();}
    @FXML
    public void setPrimaryStage(Stage PrimaryStage) {this.primaryStage = PrimaryStage;}

    public void renderQuestionScreen(String fxmlFile, String cssFile, boolean isHost, String chosenCategory){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Scene choiceScene = new Scene(root, 1200, 800);
            choiceScene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
            primaryStage.setResizable(false);
            primaryStage.setTitle("TRIVIA GAME");
            primaryStage.setScene(choiceScene);
            primaryStage.show();

            TriviaGameApp.questionScreen = loader.getController();
            TriviaGameApp.questionScreen.setPrimaryStage(primaryStage);

            TriviaGameApp.questionScreen.fetchQuestionDataFromDB(chosenCategory);

            if(isHost) {
                TriviaGameApp.questionScreen.setPlayerInfoHost();
               TriviaGameApp.questionScreen.isHost = true;
            }
            else TriviaGameApp.questionScreen.setPlayerInfoGuest();

            TriviaGameApp.questionScreen.start = Instant.now();
            TriviaGameApp.questionScreen.manageCounter();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void fetchQuestionDataFromDB(String choosenCategory){
        try {
            int idPytania = TablesManagement.getIDpytanieByCategory(choosenCategory, 0);

            String tresc = PytaniaHandler.getTresc(idPytania);
            String odpPoprawna = PytaniaHandler.getOdpPoprawna(idPytania);
            String odp2 = PytaniaHandler.getOdp2(idPytania);
            String odp3 = PytaniaHandler.getOdp3(idPytania);
            String odp4 = PytaniaHandler.getOdp4(idPytania);

            TriviaGameApp.questionScreen.MapDataFromDB(tresc, odpPoprawna, odp2, odp3, odp4);

            TriviaGameApp.questionScreen.RandomizeAnswers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void MapDataFromDB(String question, String right_answer, String answer2, String answer3, String answer4){
        TriviaGameApp.questionScreen.question = question; //max 80 znakow
        TriviaGameApp.questionScreen.answers.add(right_answer);
        TriviaGameApp.questionScreen.answers.add(answer2);
        TriviaGameApp.questionScreen.answers.add(answer3); //odpowiedz moze miec max 26 znakow bo inaczej sie zdeformuje przycisk !!!!!!!!!!!!!!!!!!!!!!!!!!
        TriviaGameApp.questionScreen.answers.add(answer4);

        TriviaGameApp.questionScreen.right_answer = TriviaGameApp.questionScreen.answers.get(0);
    }

    public void RandomizeAnswers(){
        Collections.shuffle(answers);

        question_label.setText(TriviaGameApp.questionScreen.question);
        button1.setText(TriviaGameApp.questionScreen.answers.get(0));
        button2.setText(TriviaGameApp.questionScreen.answers.get(1));
        button3.setText(TriviaGameApp.questionScreen.answers.get(2));
        button4.setText(TriviaGameApp.questionScreen.answers.get(3));
    }

    public void answerHandler(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        String selected_answer = clicked.getText();
        TriviaGameApp.questionScreen.answer = Instant.now();
        TriviaGameApp.questionScreen.answerTime = Duration.between(TriviaGameApp.questionScreen.start, TriviaGameApp.questionScreen.answer);

        if (selected_answer.equals(TriviaGameApp.questionScreen.right_answer)) {
            clicked.setStyle("-fx-background-color: #7BB6B2");
            TriviaGameApp.questionScreen.assignPrize(TriviaGameApp.questionScreen.isHost);
            TriviaGameApp.questionScreen.timer.cancel();
            adjustVisualsWhenAnswered("#7BB6B2", "Poprawna odpowiedź!");

            PauseTransition pauseTransition = new PauseTransition(javafx.util.Duration.seconds(1.0));
            pauseTransition.setOnFinished(e -> processTheAnswer(true, TriviaGameApp.questionScreen.question, selected_answer));
            pauseTransition.play();
        } else {
            clicked.setStyle("-fx-background-color: #F77B6B");
            TriviaGameApp.questionScreen.timer.cancel();
            adjustVisualsWhenAnswered("#F77B6B", "Zła odpowiedź!");

            PauseTransition pauseTransition = new PauseTransition(javafx.util.Duration.seconds(1.0));
            pauseTransition.setOnFinished(e -> processTheAnswer(false, TriviaGameApp.questionScreen.question, selected_answer));
            pauseTransition.play();
        }
    }

    private void colorRightAnswer(){
        if(TriviaGameApp.questionScreen.button1.getText().equals(TriviaGameApp.questionScreen.right_answer))
            TriviaGameApp.questionScreen.button1.setStyle("-fx-background-color: #7BB6B2");
        if(TriviaGameApp.questionScreen.button2.getText().equals(TriviaGameApp.questionScreen.right_answer))
            TriviaGameApp.questionScreen.button2.setStyle("-fx-background-color: #7BB6B2");
        if(TriviaGameApp.questionScreen.button3.getText().equals(TriviaGameApp.questionScreen.right_answer))
            TriviaGameApp.questionScreen.button3.setStyle("-fx-background-color: #7BB6B2");
        if(TriviaGameApp.questionScreen.button4.getText().equals(TriviaGameApp.questionScreen.right_answer))
            TriviaGameApp.questionScreen.button4.setStyle("-fx-background-color: #7BB6B2");
    }

    private void processTheAnswer(boolean isRight, String questionContent, String answerContent){
        //wywolac metode ktora pobierze id pytania na podstawie tresci
        //wywowal metode ktora doda wiersz do HisTurTmp
        //HisTurTmpHandler.setWybranaOdpowiedz();
        if(TriviaGameApp.questionScreen.isHost){
            try {
                if(ScreensManagerForServer.roundNumber == 10){
                    TriviaGameApp.hostPlayer.bufferedWriter.write("EndOfGame\n");
                    TriviaGameApp.hostPlayer.bufferedWriter.flush();
                }
                TriviaGameApp.hostPlayer.bufferedWriter.write("guestTurn\n");
                TriviaGameApp.hostPlayer.bufferedWriter.flush();
                ScreensManagerForServer.setWaitScreen();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                TriviaGameApp.guestPlayer.bufferedWriter.write("hostTurn\n");
                TriviaGameApp.guestPlayer.bufferedWriter.flush();

                TriviaGameApp.waitForPlayerTurnScreen = new WaitForPlayerTurnScreen();
                TriviaGameApp.waitForPlayerTurnScreen.setPrimaryStage(TriviaGameApp.questionScreen.primaryStage);
                TriviaGameApp.waitForPlayerTurnScreen.renderWaitScreen("WaitForPlayerTurnScreen.fxml", "Styles.css", false);
                TriviaGameApp.waitForPlayerTurnScreen.setWaitTextAsWaitForYourTurn();
                TriviaGameApp.questionScreen.waitForHostEndSignal();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void waitForHostEndSignal(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Czekam na wiadomosc od servera");
                try {
                    String msg = TriviaGameApp.guestPlayer.bufferedReader.readLine();
                    if(msg.equals("guestTurn")){
                        timer.cancel();
                        Platform.runLater(() -> {
                            try {
                                TriviaGameApp.categoryChoiceScreen = new CategoryChoiceScreen();
                                TriviaGameApp.categoryChoiceScreen.setPrimaryStage(TriviaGameApp.waitForPlayerTurnScreen.primaryStage);
                                TriviaGameApp.categoryChoiceScreen.renderChoiceScreen("CategoryChoiceScreen.fxml", "Styles.css", false, true);
                                TriviaGameApp.categoryChoiceScreen.setPlayerInfoGuest();
                            }
                            catch (IOException e){
                                e.printStackTrace();
                            }
                        });
                    }
                    else if(msg.equals("guestTurnLast")){
                        timer.cancel();
                       Platform.runLater(() -> {
                           TriviaGameApp.questionScreen.waitForHostEndSignal();
                        });
                        System.out.println("\nTOBYLAOSTATNIARUNDACHUJU\n");
                    }
                    else if(msg.equals("EndOfGame")){
                        timer.cancel();
                        Platform.runLater(() -> {
                            System.out.println("\nKONIECGRY GOSC SCREEN\n" + TriviaGameApp.guestPlayer.Prize);
                            TriviaGameApp.endingScreen = new EndingScreen();
                            TriviaGameApp.endingScreen.setPrimaryStage(TriviaGameApp.questionScreen.primaryStage);
                            TriviaGameApp.endingScreen.renderEndingScreen("EndingScreen.fxml", "Styles.css", false);
                        });
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

    private void adjustVisualsWhenAnswered(String color, String text){
        TriviaGameApp.questionScreen.counter.setStyle("-fx-text-fill: " + color);
        TriviaGameApp.questionScreen.counter.setText(text);
        TriviaGameApp.questionScreen.button1.setDisable(true);
        TriviaGameApp.questionScreen.button2.setDisable(true);
        TriviaGameApp.questionScreen.button3.setDisable(true);
        TriviaGameApp.questionScreen.button4.setDisable(true);
        TriviaGameApp.questionScreen.colorRightAnswer();
    }

    private int calculatePrize(float answerTime){
        float firststep = 1 - ((answerTime / 30000) /2);
        return (int) (firststep * 1000);
    }

    private void assignPrize(boolean isHost){
        int prize = calculatePrize((float) TriviaGameApp.questionScreen.answerTime.toMillis());

        if (isHost)
            TriviaGameApp.hostPlayer.Prize += prize;
        else
            TriviaGameApp.guestPlayer.Prize += prize;

        TriviaGameApp.questionScreen.playerInfo.prize.setText(prize + "$");
    }

    private void manageCounter(){
        TriviaGameApp.questionScreen.timer = new Timer();
        TimerTask countdown = new TimerTask() {
            int time = 30;
            @Override
            public void run() {
                time -= 1;
                if(time == 5){
                    Platform.runLater(() -> {
                        TriviaGameApp.questionScreen.counter.setStyle("-fx-text-fill: #F77B6B");
                        TriviaGameApp.questionScreen.counter.setText(Integer.toString(time));
                    });
                }
                else if(time == 0){
                    TriviaGameApp.questionScreen.timer.cancel();
                    Platform.runLater(() -> {
                        adjustVisualsWhenAnswered("#F77B6B", "Koniec czasu!");
                        PauseTransition pauseTransition = new PauseTransition(javafx.util.Duration.seconds(1.0));
                        pauseTransition.setOnFinished(e -> TriviaGameApp.questionScreen.processTheAnswer(false, "", ""));
                        pauseTransition.play();
                    });
                }
                else {
                    Platform.runLater(() -> {
                        TriviaGameApp.questionScreen.counter.setText(Integer.toString(time));
                    });
                }
            }
        };
        TriviaGameApp.questionScreen.timer.schedule(countdown, 0, 1000);
    }

    public void setPlayerInfoHost(){
        TriviaGameApp.questionScreen.playerInfo.setPlayerNick(TriviaGameApp.hostPlayer.nickname);
        TriviaGameApp.questionScreen.playerInfo.setPrize(TriviaGameApp.hostPlayer.Prize);
    }

    public void setPlayerInfoGuest(){
        TriviaGameApp.questionScreen.playerInfo.setPlayerNick(TriviaGameApp.guestPlayer.nickname);
        TriviaGameApp.questionScreen.playerInfo.setPrize(TriviaGameApp.guestPlayer.Prize);
    }

}
