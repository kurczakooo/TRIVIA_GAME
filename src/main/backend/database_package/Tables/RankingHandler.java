package database_package.Tables;


import database_package.DataBaseHandler;

import java.sql.*;

public class RankingHandler {
    private static final Connection connection = DataBaseHandler.connect();

    public static Integer getMaxId() {
        String sqlQuestion = "SELECT MAX(IDgracza) FROM Ranking";

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


    public static void addPlayerWithoutStats(String nick){
        // ID jest auto increment ale i tak musze tutaj dac
        String sqlQuery = "INSERT INTO Ranking VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){

            preparedStatement.setInt(1, getMaxId() +1);
            preparedStatement.setString(2, nick);
            preparedStatement.setDate(3, null);
            preparedStatement.setString(4, null);
            preparedStatement.setString(5, null);
            preparedStatement.setString(6, null);
            preparedStatement.setString(7, null);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\npomyslnie dodano Gracza "+ nick +" do rankingu");
            } else {
                System.out.println("\nDodanie Gracza do rankingu nie powiodło się");
            }

        }catch (SQLException e){
            System.out.println("\nOsoby juz sa w rankingu!\n");
            e.printStackTrace();
        }
    }

    public static void updatePlayer(Integer IDgracza, String Nick, Integer iloscGier,
                                    Integer iloscWygranychPodRzad, Integer NajszybszaOdp, Integer NajNagroda) {

        String sqlQuery = "UPDATE Ranking SET Nick = ?, DataOstatniejGry = datetime(),  iloscGier = ?, iloscWygranychPodRzad = ?," +
                "NajszybszaOdp = ?, NajNagroda = ? WHERE IDgracza = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, Nick);
            //preparedStatement.setDate(2, dzisiejszaSqlDate);
            preparedStatement.setInt(2, iloscGier);
            preparedStatement.setInt(3, iloscWygranychPodRzad);
            preparedStatement.setInt(4, NajszybszaOdp);
            preparedStatement.setInt(5, NajNagroda);
             preparedStatement.setInt(6, IDgracza);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\nSuccessfully updated player stats for ID: " + IDgracza);
            } else {
                System.out.println("\nPlayer with ID: " + IDgracza + " not found or update failed");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

    }


    public static Integer getidgracza(String nick){
        String sqlQuery = "SELECT IDgracza FROM Ranking WHERE nick = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){

            preparedStatement.setString(1, nick);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt(1);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("\nBlad! Mozliwe ze masz literowke\n");
        return null;
    }

    public static boolean czyGraczJuzIstnieje(String nick){
        String sqlQuery = "SELECT COUNT(*) FROM Ranking WHERE nick = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){

            preparedStatement.setString(1, nick);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                int count = resultSet.getInt(1);
                System.out.println("Znaleziono Gracza w Bazie!");
                return count > 0;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static Integer getIloscGierGracza(String nick){
        String sqlQuery = "SELECT iloscGier FROM Ranking WHERE nick = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){

            preparedStatement.setString(1, nick);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt(1);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("\nBlad! Mozliwe ze masz literowke\n");
        return null;
    }

    public static Integer getIloscWygranychGracza(String nick){
        String sqlQuery = "SELECT IloscWygranychPodRzad FROM Ranking WHERE nick = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){

            preparedStatement.setString(1, nick);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt(1);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("\nBlad! Mozliwe ze masz literowke\n");
        return null;
    }

    public static void updatePlayerGamesAmount(Integer IDgracza, String Nick, Integer iloscGier) {

        String sqlQuery = "UPDATE Ranking SET Nick = ?, DataOstatniejGry = datetime(),  iloscGier = ? WHERE IDgracza = ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, Nick);
            preparedStatement.setInt(2, iloscGier);
            preparedStatement.setInt(3, IDgracza);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\nSuccessfully updated player stats for ID: " + IDgracza);
            } else {
                System.out.println("\nPlayer with ID: " + IDgracza + " not found or update failed");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
