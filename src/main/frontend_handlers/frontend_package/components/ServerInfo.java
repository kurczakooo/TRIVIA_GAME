package frontend_package.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

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
        initialize("debil", 6969);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerInfo.fxml"));
        loader.setRoot(this);
        loader.setController(ServerInfo.this);

        try {
            loader.load();
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/frontend_package/Styles.css")).toExternalForm());
    }

    public ServerInfo(String hostNick, int portNumber){
        initialize(hostNick, portNumber);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerInfo.fxml"));
        loader.setRoot(this);
        loader.setController(ServerInfo.this);

        try {
            loader.load();
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/frontend_package/Styles.css")).toExternalForm());
    }

    @FXML
    private void initialize(String hostNick, int portNumber){
        this.hostNick = new Label();
        this.setHostNick(hostNick);
        this.portNumber = new Label();
        this.setPortNumber(portNumber);
        this.joinGameButton = new Button();
        this.joinGameButton.setText("Dołącz");
    }

    public void setHostNick(String hostNick) {
        this.hostNick.setText(hostNick);
    }

    public void setPortNumber(int portNumber) {
        this.portNumber.setText(Integer.toString(portNumber));
    }
}