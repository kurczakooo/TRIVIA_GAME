package main.trivia_game;

import javafx.event.ActionEvent;
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
    private Label question_label;
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

    private String question;

    public QuestionController(){this.answers = new ArrayList<>();}
    @FXML
    public void setPrimaryStage(Stage PrimaryStage) {this.PrimaryStage = PrimaryStage;}

    public void GetDataFromDB(String question, String right_answer, String answer2, String answer3, String answer4){
        //dzwonisz do bazy danych i pobierasz pytanie i odpowiedzi, tam zawsze pierwsza odpowiedz bedzie poprawna,
        //wiec tutaj pytania jak sie pobiera to beda musialy sie losowac ,tylko do wyswietlania.
        this.question = question; //max 80 znakow
        this.answers.add(right_answer);
        this.answers.add(answer2);
        this.answers.add(answer3); //odpowiedz moze miec max 26 znakow bo inaczej sie zdeformuje przycisk !!!!!!!!!!!!!!!!!!!!!!!!!!
        this.answers.add(answer4);

        this.right_answer = this.answers.get(0);
    }

    public void RandomizeAnswers(){
        Collections.shuffle(answers);

        question_label.setText(this.question);
        button1.setText(this.answers.get(0));
        button2.setText(this.answers.get(1));
        button3.setText(this.answers.get(2));
        button4.setText(this.answers.get(3));
    }

    public void answerHandler(ActionEvent event){
        Button clicked = (Button)event.getSource();
        String selected_answer = clicked.getText();

        if(selected_answer.equals(this.right_answer))
            clicked.setStyle("-fx-background-color: #7BB6B2");
        else
            clicked.setStyle("-fx-background-color: #F77B6B");
    }

}
