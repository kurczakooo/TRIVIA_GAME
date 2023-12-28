package frontend_package;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import server.Player;

import java.io.IOException;
import java.util.Objects;

public class JoinScreen {
    @FXML
    public Label playerNick;
    @FXML
    private Label playerPrize;
    private Stage primaryStage;


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void renderJoinScreen(String fxmlFile, String cssFile) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssFile)).toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.setTitle("TRIVIA GAME");
        primaryStage.setScene(scene);
        primaryStage.show();

        TriviaGameApp.joinScreen = loader.getController();
        TriviaGameApp.joinScreen.setPrimaryStage(primaryStage);
    }

    public void testowyhandler(ActionEvent actionEvent){
        try {
            ChoiceController choiceController = new ChoiceController();
            choiceController.setPrimaryStage(primaryStage);
            choiceController.renderChoiceScreen("category_choice.fxml", "Styles.css");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}


