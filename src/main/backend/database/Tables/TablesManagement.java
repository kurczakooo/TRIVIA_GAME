package database.Tables;

import database.DataBaseHandler;

import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TablesManagement {


// Zwraca dwie losowe kategorie
    public static String[] twoCategoriesFromKategorie() throws SQLException {
        Connection connection = DataBaseHandler.connect();
        String []answers;

        answers = new String[2];
        String sqlQuestion = "SELECT DISTINCT NazwaKategori FROM KategoriePytan ORDER BY random() LIMIT 2";

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


// Zwraca losowe ID pytania z Tabeli Pytania, na podstawie przekazanej nazwy kategori
    public static Integer getIDpytanieByCategory(String kategoria, Integer IDGracza) throws SQLException {
        Connection connection = DataBaseHandler.connect();

        // ID kategorii na podstawie nazwy kategori
        Integer IDKategori = KategoriePytanHandler.getIDkategori(kategoria);

        int i = 0;
        int drawPytanieID;
        // Losujemy randomowe ID pytania dopoki nie wylosujemy tego ktorego nie ma w HisTurTmp
            do{
                // Wybierz losowe pytanie z Pytania
                String sqlQuery = "SELECT IDpytania FROM Pytania  WHERE IDkategori = " +IDKategori + " ORDER BY random() LIMIT 1";

                try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

                    ResultSet resultSet = preparedStatement.executeQuery();

                    // Odczytanie wartości z pierwszej kolumny wyniku zapytania
                    drawPytanieID =  resultSet.getInt(1);

                    // jesli numer (IDPytania) jest > 0, i jesli pytania NIE ma w HisTurTmp to je zworc
                    if(drawPytanieID > 0 && !HisTurTmpHandler.isPytanieInTable(drawPytanieID)){
                        connection.close();

                        // Dodajemy wylosowane dane Pytania do HisTurTmp
                        HisTurTmpHandler. setWybranaOdpowiedz(kategoria, IDGracza, drawPytanieID, IDKategori);
                        return drawPytanieID;
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("\nObieg nr: "+ i + "\n" );
                i++;


            }while(HisTurTmpHandler.isPytanieInTable(drawPytanieID) && HisTurTmpHandler.isAnyQuestionLeftInCategory(kategoria));

            return null;
        }


    public static void main(String[] args) throws SQLException {
           // String[] test = twoCategoriesFromKategorie();

          //  System.out.println(test[0] + "\n" + test[1]);

        System.out.println(getIDpytanieByCategory("INFORMATYKA", 1));
    }
}
