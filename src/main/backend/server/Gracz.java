package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Gracz {

    public Socket socket;

    public Gracz(String adress, int port){
        try {
            socket = new Socket(adress, port);
            System.out.println("gracz polaczony z serwerem");
        }
        catch (IOException e){
         e.printStackTrace();
        }
    }

    public String readMessage(Socket socket) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return input.readLine();
    }
}
