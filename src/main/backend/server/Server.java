package server;
//https://www.youtube.com/watch?v=gLfuZrrfKes

import frontend_package.TriviaGameApp;
import javafx.application.Platform;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable{

    public ServerSocket serverSocket;
    public BufferedReader bufferedReader;
    public BufferedWriter bufferedWriter;
    public BufferedWriter bufferedWriterForHost;
    public BufferedReader bufferedReaderForHost;
    public Player hostPlayer;
    public Player guestPlayer;
    public int playerCount;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.playerCount = 0;
    }
    public void setHostPlayer(Player hostPlayer) {
        this.hostPlayer = hostPlayer;
    }
    public void setGuestPlayer(Player guestPlayer) {
        this.guestPlayer = guestPlayer;
    }

    private void setBuffers(Socket playerSocket, boolean forHost) {
        if(forHost){
            try {
                this.bufferedWriterForHost = new BufferedWriter(new OutputStreamWriter(playerSocket.getOutputStream()));
                this.bufferedReaderForHost = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(playerSocket.getOutputStream()));
                this.bufferedReader = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void waitForGuest(){
        System.out.println("Serwer stworzony, czeka na graczy");
        try {
            while (playerCount != 3){
                Socket playerSocket = serverSocket.accept();
                System.out.println("nowy gracz polaczony");
                playerCount++;
                setBuffers(playerSocket, false);
                if(playerCount == 1)
                    setBuffers(playerSocket, true);
                if(playerCount == 2)
                    shareInfo();
                if(playerCount == 3){
                    String guestnick = this.bufferedReader.readLine();
                    this.guestPlayer = new Player(guestnick, serverSocket.getLocalPort());
                    ScreensManagerForServer.setHostScreenLabels(this.guestPlayer, this.hostPlayer.nickname, this.guestPlayer.nickname);
                    try{
                        ScreensManagerForServer.startGame(bufferedReader.readLine());
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
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

    public void shareInfo() {
        try {
            String message = hostPlayer.nickname + "`" +
                    serverSocket.getLocalPort() + "\n";
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
        while(portNumber <= 5020){
            if(Server.isPortAvailable(portNumber)) {
                createServer(portNumber);
                break;
            }
            else
                portNumber++;

            if (portNumber == 5020)
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
