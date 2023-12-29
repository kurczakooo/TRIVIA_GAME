package frontend_package;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.Objects;

public class ServerInfo extends HBox{
    @FXML
    private Label hostNick;
    @FXML
    private Label portNumber;
    @FXML
    private Button joinGameButton;

    public ServerInfo(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerInfo.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        /*try {
            Parent root = loader.load();
            Scene scene = new Scene(root, 1200, 200);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Styles.css")).toExternalForm());
            initialize();
        }
        catch (IOException e){
            e.printStackTrace();
        }*/
    }

    @FXML
    private void initialize(){
        this.setHostNick("damian");
        this.setPortNumber(5000);
        this.joinGameButton.setText("Dołącz");
    }

    public void setHostNick(String hostNick) {
        this.hostNick.setText(hostNick);
    }

    public void setPortNumber(int portNumber) {
        this.portNumber.setText(Integer.toString(portNumber));
    }
}