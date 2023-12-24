package server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Gracz {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Gracz(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = username;
        }
        catch (IOException e){
            closePlayer(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(){
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);

            while (socket.isConnected()){
                String message = scanner.nextLine();
                bufferedWriter.write(username + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }
        catch (IOException e){
            closePlayer(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenToServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromChat;

                while (socket.isConnected()){
                    try {
                        msgFromChat = bufferedReader.readLine();
                        System.out.println(msgFromChat);
                    } catch (IOException e) {
                        closePlayer(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closePlayer(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if (bufferedReader != null)
                bufferedReader.close();
            if (bufferedWriter != null)
                bufferedWriter.close();
            if (socket != null)
                socket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        Socket socket = new Socket("localhost", 1234);
        Gracz gracz = new Gracz(socket, "GRACZ1");
        gracz.listenToServer();
        gracz.sendMessage();
    }
}
