package frontend_package;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class MenuScreen {
    @FXML
    private TextField nickbox;
    private String nick;
    @FXML
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void renderMenu(String fxmlFile, String cssFile) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssFile)).toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.setTitle("TRIVIA GAME");
        primaryStage.setScene(scene);
        primaryStage.show();

        MenuScreen menuScreen = loader.getController();
        menuScreen.setPrimaryStage(primaryStage);
    }

    public void onHostButtonClick(ActionEvent actionEvent) {
        nick = nickbox.getText();
        if(nick.isEmpty()){
            nickbox.setPromptText("WPISZ NICK!!");
        }
        else{
            try{
                HostScreen hostScreen = new HostScreen();
                hostScreen.setPrimaryStage(primaryStage);
                hostScreen.renderHostScreen("HostScreen.fxml", "Styles.css");
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void onJoinButtonClick(ActionEvent actionEvent){
        nick = nickbox.getText();
        if(nick.isEmpty()){
            nickbox.setPromptText("WPISZ NICK!!");
        }
        else{
            try{
                JoinScreen joinScreen = new JoinScreen();
                joinScreen.setPrimaryStage(primaryStage);
                joinScreen.renderJoinScreen("JoinScreen.fxml", "Styles.css");
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}