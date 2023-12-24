package database.Tables;

import database.DataBaseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PytaniaHandler {
    public static Connection connection = DataBaseHandler.connect();

    public static String getTresc(int idPytania){

        String sqlQuestion = "SELECT Tresc FROM Pytania WHERE IDpytania = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

            // Wykonuje sql w javie   numer pytajnika   zamiast pytajnika 4 linie wyzej,
            preparedStatement.setInt(1, idPytania);


            // Po executeQuery resultSet zawiera wartość zwróconą przez zapytanie
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("Tresc");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Wystapil blad!");
        return null;
    }


   public static String getOdpPoprawna(int idPytania){

       String sqlQuestion = "SELECT OdpPoprawna FROM Pytania WHERE IDpytania = ?";

       try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

           preparedStatement.setInt(1, idPytania);

           ResultSet resultSet = preparedStatement.executeQuery();

           if(resultSet.next()){                   // nazwa kolumny
               return resultSet.getString("odpPoprawna");
           }
       }catch(SQLException e){
           e.printStackTrace();
       }
       System.out.println("Wystapil blad!");
       return null;
   }


    public static String getOdp2(int idPytania){

        String sqlQuestion = "SELECT odp2 FROM Pytania WHERE IDpytania = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

            preparedStatement.setInt(1, idPytania);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("odp2");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Wystapil blad!");
        return null;
    }


    public static String getOdp3(int idPytania){

        String sqlQuestion = "SELECT Odp3 FROM Pytania WHERE IDpytania = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

            preparedStatement.setInt(1, idPytania);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("Odp3");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Wystapil blad!");
        return null;
    }


    public static String getOdp4(int idPytania){

        String sqlQuestion = "SELECT odp4 FROM Pytania WHERE IDpytania = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

            preparedStatement.setInt(1, idPytania);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("odp4");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Wystapil blad!");
        return null;
    }


    public static String getIdKategori(int idPytania){

        String sqlQuestion = "SELECT IDkategori FROM Pytania WHERE IDpytania = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuestion)){

            preparedStatement.setInt(1, idPytania);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("IDkategori");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Wystapil blad!");
        return null;
    }


    public static void setPytanie(String tresc, String odpPoprawna, String odp2, String odp3, String odp4, int idKategorii) {
        String sqlInsert = "INSERT INTO Pytania (Tresc, OdpPoprawna, odp2, Odp3, odp4, IDkategori) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setString(1, tresc);
            preparedStatement.setString(2, odpPoprawna);
            preparedStatement.setString(3, odp2);
            preparedStatement.setString(4, odp3);
            preparedStatement.setString(5, odp4);
            preparedStatement.setInt(6, idKategorii);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Pytanie dodane pomyślnie!");
            } else {
                System.out.println("Błąd podczas dodawania pytania.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePytanie(int idPytania) {
        String sqlDelete = "DELETE FROM Pytania WHERE IDpytania = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {
            preparedStatement.setInt(1, idPytania);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Pytanie usunięte pomyślnie!");
            } else {
                System.out.println("Nie znaleziono pytania o podanym ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Integer getMaxId() {
        String sqlQuestion = "SELECT MAX(IDpytania) FROM Pytania";

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

    public static void main(String[] args) {
        String test1 = PytaniaHandler.getIdKategori(8);
        String test2 = PytaniaHandler.getOdpPoprawna(1);

        System.out.println(test1);
        System.out.println(test2);

        Integer max = PytaniaHandler.getMaxId();
        System.out.println(max);

        // setPytanie("test", "a", "b", "c", "d", 6);

            System.out.println("Pytan jest: " + getMaxId());
            //deletePytanie(23);

        // deletePytanie(21);
    }
}
