package database_package;
import java.sql.*;

public class DataBaseHandler {

    public static Connection connect(){
        Connection connection = null;
        try {
            // Laduje klase "org.sqlite.JDBC"
            Class.forName("org.sqlite.JDBC");

            // Connecting to db; Dla sqlLite: "jdbc:sqlite:<sciezka do bazy danych>",
            // sciezka to URL do bazy danych, prawym na baze->properties->URL
            connection = DriverManager.getConnection("jdbc:sqlite:database");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e + "");
        }

        if(connection != null)
            System.out.println("Connected");
        else
            System.out.println("Not connected");

        return connection;
    }

  /*  public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e+ "");
        }
    }
    */


}
