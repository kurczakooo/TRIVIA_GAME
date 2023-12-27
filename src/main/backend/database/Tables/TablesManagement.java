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

        Set<String> alreadyChosenNazwaKategori = new HashSet<String>();
        String []answers;
        int counter = 0;
        final int maxTries = 20;

        //  ̶w̶y̶b̶i̶e̶r̶z̶ ̶d̶w̶i̶e̶ ̶r̶a̶n̶d̶o̶m̶ ̶k̶a̶t̶e̶g̶o̶r̶i̶e̶
        //  ̶J̶e̶ś̶l̶i̶ ̶k̶t̶ó̶r̶a̶ś̶ ̶z̶ ̶n̶i̶c̶h̶ ̶i̶s̶t̶n̶i̶e̶j̶e̶ ̶w̶ ̶t̶a̶b̶e̶l̶i̶ ̶H̶i̶s̶t̶o̶r̶i̶a̶T̶u̶r̶T̶m̶p̶ ̶t̶o̶ ̶w̶y̶l̶o̶s̶u̶j̶ ̶j̶e̶s̶z̶c̶z̶e̶ ̶r̶a̶z̶

    do {
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

            // Checks if any of the items in array of String exist in HisTurTmp table, if it does then break and choose
            // next random 2
            for(String ans : answers){
                if(HisTurTmpHandler.doesRowExist(ans)){
                    answers = null;
                    counter ++;
                    System.out.println("Fetched, exist in HisTurTmp... retrying. Iteration nr: " + counter);
                    break;
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error in twoCategoriesFromKategorie (fetching 2 random from KategoriePytan)");
            connection.close();
        }

    }while(answers == null);

        if(counter == maxTries){
            System.out.println("Reached max tries");
            return null;
        }
         return answers;
    }


    // Nowa metoda losuje pytanie z Pytania po
    public static String drawQuestion(int IDkategori){
        // Po id wybierz losową treść
        // Wrzuc do tmp do pola WybranaOdpowiedz pole tresc
        //

        return null;
    }
    // Czy IF count(*) from HisTurTmp where pytania_IdKategori = 5 then wylosuj jeszcze raz kategorie
    // If nie ma 5 to:
    // Wylosowane 2 kategorie
    // Gracz wybiera jedną z dwóch wylosowanych kategorii
    // Napisac metode losuje pytanie na podstawie IDkategorii którą ma w argumencie losuje jedno pytanie
    // Jesli wybrane IDpytania jest w IDpytania Histurtmp to losuj do momentu gdzie wybierze jakies IDpytania którego
    // nie ma w histur tmp
    // Ta nowa metoda zwraca IDpytania
    // Damian wywołuje tą metode losuje pytanie bierze to ID i wywoluje sb getodp1, odp2.. etc na podstawie zwroconego ID
    // Daje id tego pytania do histurtmp do pola IDpytania
    // Gracz klika odpowiedź, tresc do stringa damian daje
    // na podstawie Stringa damiana w argumencie,  wsadz tego stringa do kolumny wybrana odpowiedz w histurtmp
    // damian przekaze tez idpytania i wsadz je do pytania_IdKategori
    // daje all tylko idtury niech sie samo zwieksza
    public static void main(String[] args) throws SQLException {

        String[]test = twoCategoriesFromKategorie();

        System.out.println(test[0] + "\n" + test[1]);

    }
}
