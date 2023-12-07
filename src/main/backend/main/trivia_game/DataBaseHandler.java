package main.trivia_game;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class DataBaseHandler {

    public static int liczbaPytan = 2;
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

    public static void insertToDb(int PytanieID,String TrescPytania, String OdpowiedzPoprawna, String Odpowiedz2,String Odpowiedz3, String Odpowiedz4){
        Connection con = DataBaseHandler.connect();

        // Do wysylania zapytan w sql
        PreparedStatement ps = null;

        try{
            // Pytajniki zabezpieczaja przed sql injection
            String sql = "INSERT INTO pytania VALUES(?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);

            ps.setInt(1, liczbaPytan);
            liczbaPytan++; // Nie trzeba pamietac jakie id ma pytanie w bazie danych
            ps.setString(2, TrescPytania);
            ps.setString(3, OdpowiedzPoprawna);
            ps.setString(4, Odpowiedz2);
            ps.setString(5, Odpowiedz3);
            ps.setString(6, Odpowiedz4);
            ps.execute();
            System.out.println("Data inserted");
        }catch (SQLException e){
            System.out.println(e+"");
        }
    }
}
