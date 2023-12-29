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