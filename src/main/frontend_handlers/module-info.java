module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens frontend_package to javafx.fxml;
    exports frontend_package;
    exports server;
    opens server to javafx.fxml;
    exports database;
    opens database to javafx.fxml;
    exports frontend_package.components;
    opens frontend_package.components to javafx.fxml;
}

//java --module-path "C:\Users\damia\Downloads\openjfx-21.0.2_windows-x64_bin-sdk\javafx-sdk-21.0.2\lib" --add-modules javafx.controls,javafx.fxml -jar Trivia_Game.jar