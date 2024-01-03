package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Serializable;
import java.net.Socket;

public class Player implements Serializable {
    public int portNumber;
    public Socket socket;
    public BufferedReader bufferedReader;
    public BufferedWriter bufferedWriter;
    public String nickname;
    public int FastestAnswer;
    public int Prize;

    public Player(){
        System.out.println("stowrzona gracza " + nickname);
    }

    public Player(String nickname, int portNumber) {
        System.out.println("stowrzona gracza " + nickname);
        this.nickname = nickname;
        this.portNumber = portNumber;
    }

    public Player(String nickname) {
        System.out.println("stowrzona gracza " + nickname);
        this.nickname = nickname;
    }

    public Player(Socket socket, String nickname){
        this.socket = socket;
        this.nickname = nickname;
    }

    public void setNickname(String nickname) {
        System.out.println("zmieniono nick gracza na " + nickname);
        this.nickname = nickname;
    }

    public void setSocket(Socket socket) {
        System.out.println("ustawiono polaczenie gracza");
        this.socket = socket;
    }

    public void setPrize(int prize){
        this.Prize = prize;
    }
}
