package frontend_package;

import javafx.stage.Stage;
import javafx.application.Application;
import server.*;
import java.io.IOException;
import java.util.Date;

//java --module-path "C:\Users\damia\Downloads\openjfx-21.0.2_windows-x64_bin-sdk\javafx-sdk-21.0.2\lib" --add-modules javafx.controls,javafx.fxml -jar Trivia_Game.jar

public class TriviaGameApp extends Application {
    public static Server server;
    public static Player hostPlayer;
    public static Player guestPlayer;
    public static MenuScreen menuScreen;
    public static HostScreen hostScreen;
    public static JoinScreen joinScreen;
    public static WaitForPlayerTurnScreen waitForPlayerTurnScreen;
    public static CategoryChoiceScreen categoryChoiceScreen;
    public static QuestionScreen questionScreen;
    public static EndingScreen endingScreen;

    public Date matchDate;

    @Override
    public void start(Stage primaryStage) {
        setMenuScreen(primaryStage);
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public static void setMenuScreen(Stage primaryStage){
        try {
            menuScreen = new MenuScreen();
            menuScreen.setPrimaryStage(primaryStage);
            menuScreen.renderMenu("MenuScreen.fxml", "Styles.css");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}