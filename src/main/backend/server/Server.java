package server;
//https://www.youtube.com/watch?v=gLfuZrrfKes

import frontend_package.TriviaGameApp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable{

    public ServerSocket serverSocket;
    public BufferedReader bufferedReader;
    public BufferedWriter bufferedWriter;
    public Player hostPlayer;
    public Player guestPlayer;
    public int playerCount;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public void setHostPlayer(Player hostPlayer) {
        this.hostPlayer = hostPlayer;
    }
    public void setGuestPlayer(Player guestPlayer) {
        this.guestPlayer = guestPlayer;
    }

    private void setBuffers(Socket playerSocket) {
        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(playerSocket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitForGuest(){
        System.out.println("Serwer stworzony, czeka na graczy");
        try {
            while (playerCount != 2){
                Socket playerSocket = serverSocket.accept();
                System.out.println("nowy gracz polaczony");
                setBuffers(playerSocket);
                playerCount++;
            }
            if(playerCount == 2)
                shareInfo();
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

    public void shareInfo() {
        try {
            String message = "pokoj gracza: " +
                    hostPlayer.nickname + " port:" +
                    serverSocket.getLocalPort() + "\n";
            //System.out.println("Server sending message: " + message);
            bufferedWriter.write(message);
            bufferedWriter.flush();
        }
        catch (IOException e){
            e.printStackTrace();
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

    public static void setServerOnPort()throws IOException{
        int portNumber = 5000;
        while(portNumber <= 6000){
            if(Server.isPortAvailable(portNumber)) {
                createServer(portNumber);
                break;
            }
            else
                portNumber++;

            if (portNumber == 6000)
                System.out.println("wszystkie serwery zajete");
        }
    }

    public static void createServer(int port) throws IOException{
        ServerSocket serverSocket = new ServerSocket(port);
        TriviaGameApp.server = new Server(serverSocket);
        System.out.println(serverSocket.getLocalPort());
        Thread thread = new Thread(TriviaGameApp.server);
        thread.start();
    }


    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(5000);
            Server server = new Server(socket);
            Thread thread = new Thread(server);
            thread.start();

            Socket socket1 = new Socket("localhost", 5000);
            Player player = new Player(socket1, "debil");
            server.setHostPlayer(player);

            Socket socket2 = new Socket("localhost", 5000);
            Player player2 = new Player(socket2, "debil");
            server.setGuestPlayer(player2);

            server.shareInfo();

            BufferedReader reader = new BufferedReader(new InputStreamReader(player2.socket.getInputStream()));
            String info = reader.readLine();
            System.out.println(info);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
