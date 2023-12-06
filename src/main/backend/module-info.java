module main.trivia_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens main.trivia_game to javafx.fxml;
    exports main.trivia_game;
}