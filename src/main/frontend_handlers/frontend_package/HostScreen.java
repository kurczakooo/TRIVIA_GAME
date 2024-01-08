package frontend_package;

import frontend_package.components.PlayerInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

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
        if (this.CzyGraczDrugiPolaczony) {
            this.guestPlayerLabel.setText(TriviaGameApp.guestPlayer.nickname);
            this.guestPlayerLabel.setTextFill(Paint.valueOf("green"));
        }
        else {
            this.guestPlayerLabel.setText("Oczekiwanie na gracza 2...");
        }
    }

    public void setLabelsWithServers(String hostname, String guestname){
        this.hostPlayerLabel.setText(hostname);
        this.guestPlayerLabel.setText(guestname);
        this.guestPlayerLabel.setTextFill(Paint.valueOf("green"));
        this.hostPlayerLabel.setTextFill(Paint.valueOf("green"));

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
}
