package server;
//https://www.youtube.com/watch?v=gLfuZrrfKes

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable{

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    @Override
    public void run(){
        System.out.println("Serwer stworzony, czeka na graczy");
        try {
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("nowy gracz polaczony");
                ClientHandler handler = new ClientHandler(socket);

                Thread t1 = new Thread(handler);
                t1.start();
            }

        }
        catch (IOException e){
            closeServer();
        }
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
}
