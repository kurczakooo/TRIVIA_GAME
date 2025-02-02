package database_package.Tables;

import database_package.DataBaseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class KategoriePytanHandler {

    private static Connection connection = DataBaseHandler.connect();

    public static String getNazwaKategori(int IDkategori){

        String sqlQuestion = "SELECT NazwaKategori FROM KategoriePytan WHERE IDkategori = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

            preparedStatement.setInt(1, IDkategori);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("NazwaKategori");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Wystapil blad!");
        return null;
    }


    // Zwraca ID Kategorii po jej nazwie
    public static Integer getIDkategori(String NazwaKategori){

        String sqlQuestion = "SELECT IDkategori FROM KategoriePytan WHERE NazwaKategori = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

            preparedStatement.setString(1, NazwaKategori.toUpperCase()); // All sa duzymi literami wiec why not

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("IDkategori");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Wystapil blad!");
        return null;
    }



    public static void main(String[] args) {
          System.out.println(getNazwaKategori(1));
    }
}
