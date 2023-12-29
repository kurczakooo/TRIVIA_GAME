package server;
//https://www.youtube.com/watch?v=gLfuZrrfKes

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable{

    public ServerSocket serverSocket;
    public Player hostPlayer;
    public Player guestPlayer;

    public Server(ServerSocket serverSocket, Player hostPlayer) {
        this.serverSocket = serverSocket;
        this.hostPlayer = hostPlayer;
    }

    public void waitForGuest(){
        System.out.println("Serwer stworzony, czeka na graczy");
        try {
            while (hostPlayer == null && guestPlayer == null){
                Socket socket = serverSocket.accept();
                System.out.println("nowy gracz polaczony");


            }

        }
        catch (IOException e){
            closeServer();
        }
    }

    @Override
    public void run(){
        waitForGuest();
    }

    public void closeServer(){
        try {
            if (serverSocket != null) {
                serverSocket.close();
                System.out.println("Zamknieto server");
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Blad w zamykaniu servera");
        }
    }

    public static boolean isPortAvailable(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
