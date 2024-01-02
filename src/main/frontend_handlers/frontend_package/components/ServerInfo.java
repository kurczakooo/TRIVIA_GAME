package frontend_package.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Objects;

public class ServerInfo extends HBox{
    @FXML
    public Label hostNick;
    @FXML
    public Label portNumber;
    @FXML
    private Button joinGameButton;

    public interface joinButtonHandler{
        void handleButtonClick(ActionEvent actionEvent);
    }

    public ServerInfo(String hostNick, int portNumber, joinButtonHandler joinButtonHandler){
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerInfo.fxml"));
        loader.setRoot(this);
        loader.setController(ServerInfo.this);

        try {
            loader.load();
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/frontend_package/Styles.css")).toExternalForm());
        initialize(hostNick, portNumber, joinButtonHandler);
    }

    @FXML
    private void initialize(String hostNick, int portNumber, joinButtonHandler clickHandler){
        //this.hostNick = new Label();
        this.setHostNick(hostNick);
        //this.portNumber = new Label();
        this.setPortNumber(portNumber);
        //this.joinGameButton = new Button();
        this.joinGameButton.setText("Dołącz");
        this.joinGameButton.setOnAction(event ->{
            clickHandler.handleButtonClick(event);
        });
    }

    public void setHostNick(String hostNick) {
        this.hostNick.setText(hostNick);
    }

    public void setPortNumber(int portNumber) {
        this.portNumber.setText(Integer.toString(portNumber));
    }
}