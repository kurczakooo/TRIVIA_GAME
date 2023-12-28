package database.Tables;

import database.DataBaseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;


public class TablesManagement {


// Wywalić do while
// Niech zwraca mape typu <Int, String> idpytania, trescPytania
    public static String[] twoCategoriesFromKategorie() throws SQLException {
        Connection connection = DataBaseHandler.connect();
        String []answers;

        answers = new String[2];
        String sqlQuestion = "SELECT NazwaKategori FROM KategoriePytan ORDER BY random() LIMIT 2";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

            ResultSet resultSet = preparedStatement.executeQuery();

            // Inserts random NazwaKategori into array of String
            int i = 0;
            while(resultSet.next()){
                answers[i] = resultSet.getString("NazwaKategori");
                answers[i] = answers[i].trim(); // Deletes spaces form values
                i ++;
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error in twoCategoriesFromKategorie (fetching 2 random from KategoriePytan)");
            connection.close();
        }
         return answers;
    }


    // Nowa metoda losuje pytanie z Pytania po
    public static String drawQuestion(int IDkategori){
        // Po IDkategorii wybierz losową treść
        // Wrzuc do tmp do pola WybranaOdpowiedz pole tresc

        // Random tresc from Pytania na podstawie IDkategori
        String sqlQuestion = "SELECT FROM WHERE IDpytania = IDkategorii";

        return null;
    }
    // Czy IF count(*) from HisTurTmp where pytania_IdKategori = 5 then wylosuj jeszcze raz kategorie
    // If nie ma 5 to:
    // Wylosowane 2 kategorie,  DONE
    // Gracz wybiera jedną z dwóch wylosowanych kategorii

    // Napisac metode która losuje pytanie na
    // podstawie IDkategorii którą ma w argumencie losuje jedno pytanie


    // Jesli wybrane IDpytania jest w IDpytania Histurtmp to losuj do
    // momentu gdzie wybierze jakies IDpytania którego nie ma w histur tmp
    // Ta nowa metoda zwraca IDpytania

    // Damian wywołuje tą metode losuje pytanie bierze to ID i wywoluje sb getodp1, odp2.. etc na podstawie zwroconego ID
    // Daje id tego pytania do histurtmp do pola IDpytania
    // Gracz klika odpowiedź, tresc do stringa damian daje
    // na podstawie Stringa damiana w argumencie,  wsadz tego stringa do kolumny wybrana odpowiedz w histurtmp
    // damian przekaze tez idpytania i wsadz je do pytania_IdKategori
    // daje all tylko idtury niech sie samo zwieksza
    public static void main(String[] args) throws SQLException {
            String[] test = twoCategoriesFromKategorie();

            System.out.println(test[0] + "\n" + test[1]);


    }
}
