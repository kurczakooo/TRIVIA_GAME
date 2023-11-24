/*package main.trivia_game;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DataBaseHandler {
    private static final String DATABASE_URL = "jdbc:sqlite:trivia_database.db";
    private Connection connection;
    public static void main(String[] args) {

    }

    public DataBaseHandler() {
        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection(DATABASE_URL);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        if(connection != null)
            System.out.println("Connected");
        else
            System.out.println("Not connected");
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}*/
