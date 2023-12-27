package frontend_package;

import javafx.stage.Stage;
import javafx.application.Application;
import server.*;
import java.io.IOException;
import java.util.Date;

public class TriviaGameApp extends Application {


    @Override
    public void start(Stage primaryStage) {
        try {
            MenuScreen menu = new MenuScreen();
            menu.setPrimaryStage(primaryStage);
            menu.renderMenu("MenuScreen.fxml", "Styles.css");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}