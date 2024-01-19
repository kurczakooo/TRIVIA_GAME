package database.Tables;

import database.DataBaseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PytaniaHandler {

    private static final Connection connection = DataBaseHandler.connect();


   public static Integer getidpytania(String tresc){
        String sqlQuery = "SELECT IDpytania FROM Pytania WHERE tresc = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){

            preparedStatement.setString(1, tresc);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                System.out.print("ID pytania po kategorii: ");
                return resultSet.getInt(1);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
       System.out.println("\nBlad! Mozliwe ze masz literowke\n");
       return null;
   }



   // public static void getIDGracza(String nick){

    //}

    //public static void insertToHisTurTmp(String odpowiedz, int IDpytania, int IDgracz){
        //insert into historiaturtmp (1, odpoweidz, IDgracza, IDpytania);
    ///}

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


    public static void setPytanie(int IDpytania,String tresc, String odpPoprawna, String odp2, String odp3, String odp4, int idKategorii) {
        String sqlInsert = "INSERT INTO Pytania (IDpytania,Tresc, OdpPoprawna, odp2, Odp3, odp4, IDkategori) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setInt(1, IDpytania);
            preparedStatement.setString(2, tresc);
            preparedStatement.setString(3, odpPoprawna);
            preparedStatement.setString(4, odp2);
            preparedStatement.setString(5, odp3);
            preparedStatement.setString(6, odp4);
            preparedStatement.setInt(7, idKategorii);

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


    // Przeciazona bez podawania ID
    public static void setPytanie(String tresc, String odpPoprawna, String odp2, String odp3, String odp4, int idKategorii) {
        String sqlInsert = "INSERT INTO Pytania (IDpytania, Tresc, OdpPoprawna, odp2, Odp3, odp4, IDkategori) VALUES ( ?,?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setInt(1, getMaxId()+1);
            preparedStatement.setString(2, tresc);
            preparedStatement.setString(3, odpPoprawna);
            preparedStatement.setString(4, odp2);
            preparedStatement.setString(5, odp3);
            preparedStatement.setString(6, odp4);
            preparedStatement.setInt(7, idKategorii);

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
      /*  String test1 = PytaniaHandler.getIdKategori(8);
        String test2 = PytaniaHandler.getOdpPoprawna(1);

        System.out.println(test1);
        System.out.println(test2);

        Integer max = PytaniaHandler.getMaxId();


          System.out.println("GetMaxId: " + getMaxId());
          setPytanie("a", "b", "c", "d", "e", 16);
          setPytanie(2137,"a", "b", "c", "d", "e", 16);
          deletePytanie(getMaxId());

*/

        System.out.println(getidpytania("Miś koala należy do rodziny ..."));
        System.out.println(getidpytania("Miś koala należy do rodziny asf..."));
    }
}
