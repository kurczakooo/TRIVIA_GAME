module main.trivia_game {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.trivia_game to javafx.fxml;
    exports main.trivia_game;
}