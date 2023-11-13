package main.trivia_game;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;

public class QuestionController {

    @FXML
    private Stage PrimaryStage;
    @FXML
    private Label question;
    public ArrayList<String> answers;

    public QuestionController(){this.answers = new ArrayList<>();}
    @FXML
    public void setPrimaryStage(Stage PrimaryStage) {this.PrimaryStage = PrimaryStage;}

    public void GetDataFromDB(){
        //dzwonisz do bazy danych i pobierasz pytanie i odpowiedzi, tam zawsze 1 odpowiedz bedzie poprawna,
        //wiec tutaj pytania jak sie pobiera to beda musialy sie losowac ,tylko do wyswietlania.
        this.question.setText("Kim jest Baxton?");
        this.answers.add("Youtuberem");
        this.answers.add("Pilkarzem");
        this.answers.add("Ktos taki nie istnieje");
        this.answers.add("Nie smieszne");

        System.out.println(question);
        System.out.println(answers);
    }



}
