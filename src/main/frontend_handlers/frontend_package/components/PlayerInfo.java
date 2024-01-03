package frontend_package.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class PlayerInfo extends VBox {
    @FXML
    public Label playerNick;
    @FXML
    public Label prize;

    public PlayerInfo(){
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerInfo.fxml"));
        loader.setRoot(this);
        loader.setController(PlayerInfo.this);

        try {
            loader.load();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/frontend_package/Styles.css")).toExternalForm());
    }

    public PlayerInfo(String nick, int prize){
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerInfo.fxml"));
        loader.setRoot(this);
        loader.setController(PlayerInfo.this);

        try {
            loader.load();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/frontend_package/Styles.css")).toExternalForm());
        initialize(nick, prize);
    }

    @FXML
    private void initialize(String nick, int prize){
        this.playerNick = new Label();
        setPlayerNick(nick);
        this.prize = new Label();
        setPrize(prize);
    }

    public void setPlayerNick(String nick){
        this.playerNick.setText(nick);
    }

    public void setPrize(int prize) {
        this.prize.setText(Integer.toString(prize) + "$");
    }
}
