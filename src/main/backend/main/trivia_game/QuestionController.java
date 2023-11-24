package main.trivia_game;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;

public class QuestionController {

    @FXML
    private Stage PrimaryStage;
    @FXML
    private Label question;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    public ArrayList<String> answers;
    private String right_answer;

    public QuestionController(){this.answers = new ArrayList<>();}
    @FXML
    public void setPrimaryStage(Stage PrimaryStage) {this.PrimaryStage = PrimaryStage;}

    public void GetDataFromDB(){
        //dzwonisz do bazy danych i pobierasz pytanie i odpowiedzi, tam zawsze pierwsza odpowiedz bedzie poprawna,
        //wiec tutaj pytania jak sie pobiera to beda musialy sie losowac ,tylko do wyswietlania.
        this.question.setText("Kim jest Baxton?Kim jest Baxton?Kim jest Baxton?Kim jest Baxton?Kim jest Baxton?"); //max 80 znakow
        this.answers.add("Youtuberem");
        this.answers.add("Pilkarzem");
        this.answers.add("Ktos taki nie istnieje"); //odpowiedz moze miec max 26 znakow bo inaczej sie zdeformuje przycisk !!!!!!!!!!!!!!!!!!!!!!!!!!
        this.answers.add("Nie smieszne");

        this.right_answer = this.answers.get(0);

        //System.out.println(question);
        //System.out.println(answers);
    }

    public void RandomizeAnswers(){
        Collections.shuffle(answers);

        button1.setText(this.answers.get(0));
        button2.setText(this.answers.get(1));
        button3.setText(this.answers.get(2));
        button4.setText(this.answers.get(3));
    }

}
