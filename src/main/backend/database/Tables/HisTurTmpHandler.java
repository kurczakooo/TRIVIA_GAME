package database.Tables;

import database.DataBaseHandler;

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
                                           Integer IDgracza, Integer IDpytania, Integer pytania_IdKategori){

        String sqlQuestion = "INSERT INTO HisTurTmp VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

            preparedStatement.setInt(1, IDtury);
            preparedStatement.setString(2, WybranaOdpowiedz);
            preparedStatement.setInt(3, IDgracza);
            preparedStatement.setInt(4, IDpytania);
            preparedStatement.setInt(5, pytania_IdKategori);

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
    public static void setWybranaOdpowiedz( String WybranaOdpowiedz, Integer IDgracza,
                                            Integer IDpytania, Integer pytania_IdKategori){

        String sqlQuestion = "INSERT INTO HisTurTmp VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

            preparedStatement.setInt(1, getMaxId()+1);
            preparedStatement.setString(2, WybranaOdpowiedz);
            preparedStatement.setInt(3, IDgracza);
            preparedStatement.setInt(4, IDpytania);
            preparedStatement.setInt(5, pytania_IdKategori);

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

    public static Boolean doesRowExist(String WybranaOdpowiedz){
        String sqlQuestion = "SELECT COUNT(*) FROM HisTurTmp WHERE WybranaOdpowiedz = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){
            preparedStatement.setString(1, WybranaOdpowiedz);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return Boolean.FALSE;
    }

    public static void main(String[] args) {
     /*

        deleteAllRows();

        System.out.println(doesRowExist("b"));

deleteAllRows();

         setWybranaOdpowiedz("INFORMATYKA", 1, 1, 2);
        setWybranaOdpowiedz("INFORMATYKA", 1, 2, 2);
*/



    }


}
