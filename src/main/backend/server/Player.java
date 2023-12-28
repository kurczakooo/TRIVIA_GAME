package server;

import java.net.Socket;

public class Player {
    public String nickname;
    public int FastestAnswer;
    public int Prize;

    private Socket socket;

    public Player(){
        System.out.println("stowrzona gracza " + nickname);
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

    public void setPrize(int prize){
        this.Prize = prize;
    }
}
