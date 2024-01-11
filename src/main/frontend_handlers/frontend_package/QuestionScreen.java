package frontend_package;

import database.DataBaseHandler;
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
    private Stage primaryStage;
    public ArrayList<String> answers;
    private String right_answer;
    private String question;
    private Instant start;
    private Instant answer;
    private Duration answerTime;
    private Timer timer;

    public QuestionScreen(){this.answers = new ArrayList<>();}
    @FXML
    public void setPrimaryStage(Stage PrimaryStage) {this.primaryStage = PrimaryStage;}

    public void renderQuestionScreen(String fxmlFile, String cssFile, boolean forHost, String chosenCategory){
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

            TriviaGameApp.questionScreen.fetchQuestionDataFromDB();

            if(forHost)
                TriviaGameApp.questionScreen.setPlayerInfoHost();
            else TriviaGameApp.questionScreen.setPlayerInfoGuest();

            TriviaGameApp.questionScreen.start = Instant.now();
            TriviaGameApp.questionScreen.manageCounter();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void fetchQuestionDataFromDB(){
            //test bazy daynch
            try {
                Connection connection = DataBaseHandler.connect();
                Statement statement = connection.createStatement();

                String query = "SELECT * FROM Pytania ORDER BY RANDOM() LIMIT 1;";
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    int pytanieID = resultSet.getInt("IDpytania");
                    String trescPytania = resultSet.getString("tresc");
                    String odpowiedzPoprawna = resultSet.getString("odpPoprawna");
                    String odpowiedz2 = resultSet.getString("odp2");
                    String odpowiedz3 = resultSet.getString("odp3");
                    String odpowiedz4 = resultSet.getString("odp4");

                    TriviaGameApp.questionScreen.MapDataFromDB(trescPytania, odpowiedzPoprawna, odpowiedz2, odpowiedz3, odpowiedz4);
                }
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            TriviaGameApp.questionScreen.RandomizeAnswers();
    }

    public void MapDataFromDB(String question, String right_answer, String answer2, String answer3, String answer4){
        //dzwonisz do bazy danych i pobierasz pytanie i odpowiedzi, tam zawsze pierwsza odpowiedz bedzie poprawna,
        //wiec tutaj pytania jak sie pobiera to beda musialy sie losowac ,tylko do wyswietlania.
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
            TriviaGameApp.questionScreen.assignPrize(false);
            TriviaGameApp.questionScreen.timer.cancel();
            adjustVisualsWhenAnswered("#7BB6B2", "Poprawna odpowiedź!");

            PauseTransition pauseTransition = new PauseTransition(javafx.util.Duration.seconds(1.0));
            pauseTransition.setOnFinished(e -> processTheAnswer(true, "TRESC PYTANIA", "TRESC ODPOWIEDZI"));
            pauseTransition.play();
        } else {
            clicked.setStyle("-fx-background-color: #F77B6B");
            TriviaGameApp.questionScreen.timer.cancel();
            adjustVisualsWhenAnswered("#F77B6B", "Zła odpowiedź!");

            PauseTransition pauseTransition = new PauseTransition(javafx.util.Duration.seconds(1.0));
            pauseTransition.setOnFinished(e -> processTheAnswer(false, "TRESC PYTANIA", "TRESC ODPOWIEDZI"));
            pauseTransition.play();
        }
    }

    private void processTheAnswer(boolean isRight, String questionContent, String answerContent){
        //wywolac metode ktora pobierze id pytania na podstawie tresci
        //wywowal metode ktora doda wiersz do HisTurTmp
        if(isRight){
            try {
                TriviaGameApp.guestPlayer.bufferedWriter.write("guestChoosinCategoryForHost\n");
                TriviaGameApp.guestPlayer.bufferedWriter.flush();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else {
            try {
                TriviaGameApp.guestPlayer.bufferedWriter.write("hostTurn\n");
                TriviaGameApp.guestPlayer.bufferedWriter.flush();

                TriviaGameApp.waitForPlayerTurnScreen = new WaitForPlayerTurnScreen();
                TriviaGameApp.waitForPlayerTurnScreen.setPrimaryStage(TriviaGameApp.questionScreen.primaryStage);
                TriviaGameApp.waitForPlayerTurnScreen.renderWaitScreen("WaitForPlayerTurnScreen.fxml", "Styles.css", false);
                TriviaGameApp.waitForPlayerTurnScreen.setWaitTextAsWaitForYourTurn();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void adjustVisualsWhenAnswered(String color, String text){
        TriviaGameApp.questionScreen.counter.setStyle("-fx-text-fill: " + color);
        TriviaGameApp.questionScreen.counter.setText(text);
        TriviaGameApp.questionScreen.button1.setDisable(true);
        TriviaGameApp.questionScreen.button2.setDisable(true);
        TriviaGameApp.questionScreen.button3.setDisable(true);
        TriviaGameApp.questionScreen.button4.setDisable(true);
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
                if(time == 0){
                    TriviaGameApp.questionScreen.timer.cancel();
                    Platform.runLater(() -> {
                        adjustVisualsWhenAnswered("#F77B6B", "Koniec czasu!");
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
