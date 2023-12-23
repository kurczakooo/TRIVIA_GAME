package server;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;

    private List<Gracz> players = new ArrayList<>();

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
//https://www.youtube.com/watch?v=gLfuZrrfKes              = 5:02
    public void setServer(){
        try {
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("nowy gracz polaczony");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessageToPlayers(Socket playerSocket){
        try {
            String message = "Example message";
            PrintWriter output = new PrintWriter(playerSocket.getOutputStream(), true);
            output.println(message);
            System.out.println("Wyslano wiadomosc do graczy");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
