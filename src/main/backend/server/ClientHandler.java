package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public ClientHandler(Socket socket){
        try {
            this.socket = socket;
            this.bufferedWriter =  new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = bufferedReader.readLine();
            clientHandlers.add(this);
            informServer(username + "dolaczyl");
        }
        catch (IOException e){
            closeHandler(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String message;

        while (socket.isConnected()) {
            try {
                message = bufferedReader.readLine();
                informServer(message);
            } catch (IOException e) {
                closeHandler(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void informServer(String message){
        for(ClientHandler handler : clientHandlers){
            try {
                if(!handler.username.equals(this.username)){
                    handler.bufferedWriter.write(message);
                    handler.bufferedWriter.newLine();
                    handler.bufferedWriter.flush();
                }
            }
            catch (IOException e){
                closeHandler(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public  void removeHandler(){
        clientHandlers.remove(this);
        informServer(username + "LEFT");
    }

    public void closeHandler(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeHandler();
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

}
