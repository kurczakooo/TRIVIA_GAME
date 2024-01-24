package database_package.Tables;

import database_package.DataBaseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HisTurTmpHandler {
    private static final Connection connection = DataBaseHandler.connect();

    public static Integer getMaxId() {
        String sqlQuestion = "SELECT MAX(IDtury) FROM HisTurTmp";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){       // numer kolumny
                return resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getWybranaOdpowiedz(int IDtury){

        String sqlQuestion = "SELECT WybranaOdpowiedz FROM HisTurTmp WHERE IDtury = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

            preparedStatement.setInt(1, IDtury);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("WybranaOdpowiedz");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Wystapil blad!");
        return null;
    }


    public static void setWybranaOdpowiedz(Integer IDtury, String WybranaOdpowiedz,
                                           Integer IDgracza, Integer IDpytania){

        String sqlQuestion = "INSERT INTO HisTurTmp VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

            preparedStatement.setInt(1, IDtury);
            preparedStatement.setString(2, WybranaOdpowiedz);
            preparedStatement.setInt(3, IDgracza);
            preparedStatement.setInt(4, IDpytania);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Wiersz dodane pomyślnie!");
                return;
            } else {
                System.out.println("Błąd podczas dodawania wiersza.");
                return;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Wystapil blad!");
    }


    // Przeciazona metoda, auto IDtury
    public static void setWybranaOdpowiedz(String WybranaOdpowiedz, Integer IDgracza,
                                            Integer IDpytania){

        String sqlQuestion = "INSERT INTO HisTurTmp VALUES(?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

            preparedStatement.setInt(1, getMaxId()+1);
            preparedStatement.setString(2, WybranaOdpowiedz);
            preparedStatement.setInt(3, IDgracza);
            preparedStatement.setInt(4, IDpytania);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Wiersz dodane pomyślnie!");
                return;
            } else {
                System.out.println("Błąd podczas dodawania wiersza.");
                return;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Wystapil blad!");
    }

// Znowu przeciazona xD, po samym id tury mozna dodac all
    public static void setWybranaOdpowiedz(int IDtury){

        String sqlQuestion = "INSERT INTO HisTurTmp VALUES(?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

            preparedStatement.setInt(1, getMaxId()+1);
            preparedStatement.setString(2, getWybranaOdpowiedz(IDtury));
            preparedStatement.setInt(3, getIDgracza(IDtury));
            preparedStatement.setInt(4, getIDpytania(IDtury));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Wiersz dodane pomyślnie!");
                return;
            } else {
                System.out.println("Błąd podczas dodawania wiersza.");
                return;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Wystapil blad!");
    }
    public static Integer getIDgracza(int IDtury){

        String sqlQuestion = "SELECT IDgracza FROM HisTurTmp WHERE IDtury = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

            preparedStatement.setInt(1, IDtury);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("IDgracza");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Wystapil blad!");
        return null;
    }


    public static Integer getIDpytania(int IDtury){

        String sqlQuestion = "SELECT IDpytania FROM HisTurTmp WHERE IDtury = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

            preparedStatement.setInt(1, IDtury);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("IDpytania");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Wystapil blad!");
        return null;
    }

    public static void deleteRow(int IDtury) {
        String sqlDelete = "DELETE FROM HisTurTmp WHERE IDtury = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {
            preparedStatement.setInt(1, IDtury);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Wiersz usunięte pomyślnie!");
            } else {
                System.out.println("Nie znaleziono wiersza o podanym ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllRows() {
        for(int i = 0; i<= getMaxId(); i++){
            deleteRow(i);
        }
    }


    // Zwraca true jesli pytanie o przekazanym ID istnieje, i false jesli nie w HisTurTmp
    public static Boolean isPytanieInTable(Integer IDPytania) {
        String sqlQuery = "SELECT COUNT(*) from HisTurTmp WHERE IDpytania = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){

            preparedStatement.setInt(1,IDPytania);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                    return resultSet.getInt(1) > 0;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // Jedna kategoria ma 5 pytan
    public static Boolean isAnyQuestionLeftInCategory(String kategoria){

        // ID kategorii po jej nazwie
        Integer IDKategori = KategoriePytanHandler.getIDkategori(kategoria);

        // Zlicza ile jest wierszy w HisTurTmp z Kategoria ktora przekazalismy
        String sqlQuery = "SELECT count(Pytania.IDkategori)\n" +
                "FROM HisTurTmp\n" +
                "join Pytania on HisTurTmp.IDpytania = Pytania.IDpytania\n" +
                "WHERE Pytania.IDkategori = " + IDKategori + ";";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                int count = resultSet.getInt(1);

                if(count == 5){
                    System.out.println("\nThere is no questions left in this category\n");
                    return false;
                }
            }

        }catch(SQLException e){
            throw new RuntimeException();
        }
        return true;
    }

    public static void main(String[] args) {
     /*

        deleteAllRows();

        System.out.println(doesRowExist("b"));

deleteAllRows();

         setWybranaOdpowiedz("INFORMATYKA", 1, 1, 2);
        setWybranaOdpowiedz("INFORMATYKA", 1, 2, 2);
*/

        // setWybranaOdpowiedz("INFORMATYKA", 1, 1, 2);

       // System.out.println( isPytanieInTable(1));

       // System.out.println(isAnyQuestionLeftInCategory("HISTORIA"));
       // System.out.println(isAnyQuestionLeftInCategory("INFORMATYKA"));
    }



}
