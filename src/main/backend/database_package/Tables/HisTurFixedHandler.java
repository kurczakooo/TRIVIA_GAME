package database_package.Tables;

import database_package.DataBaseHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HisTurFixedHandler {
    private static final Connection connection = DataBaseHandler.connect();

    public static Integer getMaxId() {
        String sqlQuestion = "SELECT MAX(IDtury) FROM HisTurFixed";

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

        String sqlQuestion = "SELECT WybranaOdpowiedz FROM HisTurFixed WHERE IDtury = ?";

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

        String sqlQuestion = "INSERT INTO HisTurFixed VALUES(?, ?, ?, ?, ?)";

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

    public static void main(String[] args) {
        System.out.println("test");
        System.out.println(getWybranaOdpowiedz(1));
        System.out.println(getMaxId());
    }

}
