package frontend_package;

import database.Tables.RankingHandler;
import frontend_package.components.PlayerInfo;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.ScreensManagerForServer;

import java.io.IOException;
import java.util.Objects;

public class HostScreen {
    public boolean CzyGraczDrugiPolaczony;
    @FXML
    public PlayerInfo playerInfo;
    @FXML
    private Label hostPlayerLabel;
    @FXML
    private Label guestPlayerLabel;
    @FXML
    public ToggleButton hostButton;
    @FXML
    public ToggleButton guestButton;
    @FXML
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setLabels() {
        this.hostPlayerLabel.setText(TriviaGameApp.hostPlayer.nickname);
        if (TriviaGameApp.hostScreen.CzyGraczDrugiPolaczony) {
            TriviaGameApp.hostScreen.guestPlayerLabel.setText(TriviaGameApp.guestPlayer.nickname);
            TriviaGameApp.hostScreen.guestPlayerLabel.setTextFill(Paint.valueOf("green"));
        }
        else {
            TriviaGameApp.hostScreen.guestPlayerLabel.setText("Oczekiwanie na gracza 2...");
        }
    }

    public void setLabelsWithServers(String hostname, String guestname){
        TriviaGameApp.hostScreen.hostPlayerLabel.setText(hostname);
        TriviaGameApp.hostScreen.guestPlayerLabel.setText(guestname);
        TriviaGameApp.hostScreen.guestPlayerLabel.setTextFill(Paint.valueOf("green"));
        TriviaGameApp.hostScreen.hostPlayerLabel.setTextFill(Paint.valueOf("green"));

    }

    public void renderHostScreen(String fxmlFile, String cssFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssFile)).toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.setTitle("TRIVIA GAME");
        primaryStage.setScene(scene);
        primaryStage.show();

        TriviaGameApp.hostScreen = loader.getController();
        TriviaGameApp.hostScreen.setPrimaryStage(primaryStage);

        //TriviaGameApp.hostScreen.CzyGraczDrugiPolaczony = false;
        TriviaGameApp.hostScreen.setLabels();
        TriviaGameApp.hostScreen.hostButton.setSelected(true);
        TriviaGameApp.hostScreen.hostButton.setDisable(true);

       // primaryStage.setOnCloseRequest(e-> TriviaGameApp.server.closeServer());

        TriviaGameApp.hostScreen.setPlayerInfoHost();
        TriviaGameApp.hostScreen.guestButton.setDisable(true);

        RankingHandler.addPlayerWithoutStats(TriviaGameApp.hostPlayer.nickname);
    }

    public void setPlayerInfoHost(){
        TriviaGameApp.hostScreen.playerInfo.setPlayerNick(TriviaGameApp.hostPlayer.nickname);
        TriviaGameApp.hostScreen.playerInfo.setPrize(TriviaGameApp.hostPlayer.Prize);
    }

    public void setPlayerInfoGuest(){
        TriviaGameApp.hostScreen.playerInfo.setPlayerNick(TriviaGameApp.guestPlayer.nickname);
        TriviaGameApp.hostScreen.playerInfo.setPrize(TriviaGameApp.guestPlayer.Prize);
    }

    public void guestButtonSelect(ActionEvent actionEvent){
        if(TriviaGameApp.hostScreen.guestButton.isSelected()){
            TriviaGameApp.hostScreen.guestButton.setDisable(true);
            TriviaGameApp.hostScreen.startGameSignal();
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1.0));
            pauseTransition.setOnFinished(e -> TriviaGameApp.hostScreen.setCategoryChoiceScreen());
            pauseTransition.play();
        }

    }

    public void startGameSignal(){
        try {
            TriviaGameApp.guestPlayer.bufferedWriter.write("start\n");
            TriviaGameApp.guestPlayer.bufferedWriter.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setCategoryChoiceScreen(){
        try {
            TriviaGameApp.categoryChoiceScreen = new CategoryChoiceScreen();
            TriviaGameApp.categoryChoiceScreen.setPrimaryStage(TriviaGameApp.hostScreen.getPrimaryStage());
            TriviaGameApp.categoryChoiceScreen.renderChoiceScreen("CategoryChoiceScreen.fxml", "Styles.css", false, true);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
