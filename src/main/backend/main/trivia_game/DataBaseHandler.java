package main.trivia_game;
import java.sql.*;
import java.util.ArrayList;

public class DataBaseHandler {

    // Wywalic to i dodac cos ze zlicza ile jest aktulnie rows w tabeli, na podstawie tego dodaje
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

    // Wstawianie do bazy danych
    //Object relational mapping - hibernate !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  CRUD zalatwiamy jeszcze roznymi metodami dla kazdej tabeli
    //robimy klase
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


    /*public static ArrayList<String> readAllData(String tableName){
        Connection con = DataBaseHandler.connect();
        PreparedStatement ps = null;

        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM " + tableName;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){
                String
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        } finally {
            try{
                rs.close();
                ps.close();
                con.close();
            }catch (SQLException e)
                System.out.println(e.toString);
        }


    }
        */
        /*public static String readSpecificDataRow(String tableName){
            Connection con  = DataBaseHandler.connect();
            PreparedStatement ps = null;
            ResultSet rs = null;
            try{
                String sql = "SELECT NazwKategori from KategoriePytan where IDkategori =?";
                ps = con.prepareSatement(sql);
                ps.setString(1, "1");
                rs = ps.executeQuery();

                String nazwaKategori = rs.getString(1);
                Systen.out.printl(nazwaKategori);

            }catch(SQLException e){
                System.out.println(e.toString());
            }finally {
                // Zamykamy polaczenia
                try {
                    ps.close();
                    rs.close();
                    con.close();
                } catch (SQLException e) {
                    System.out.println(e.toString());
                }

            }

            return  "";
        }*/


    /* Pobieramy pierwsza kolumne z tabeli
     public static String readFirstColumnValue(String tableName, String firstColumnName) {
        Connection con  = DataBaseHandler.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String firstColumnValue = null;

        try {
            String sql = "SELECT * FROM " + tableName + " WHERE " +firstColumnName+ " = ?";
            ps = con.prepareStatement(sql);
            // ps.setString(1, "1");
            rs = ps.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            if (rs.next()) {
                // Jeśli są wyniki, pobierz wartość pierwszej kolumny
                firstColumnValue = rs.getString(firstColumnName);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            // Zamykamy połączenia
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }

        return firstColumnValue;
    }*/
}
