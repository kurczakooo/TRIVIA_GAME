package frontend_package;

import javafx.stage.Stage;
import javafx.application.Application;

import java.io.IOException;

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
        //ServerSocket serverSocket = new ServerSocket(1234);
        //Server server = new Server(serverSocket);

        //Thread serverThread = new Thread(server);
        //serverThread.start();

        launch(args);
    }
}