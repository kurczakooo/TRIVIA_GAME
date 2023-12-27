package server;

public class Player {
    public String nickname;
    public int FastestAnswer;
    public int Prize;

    public Player(){
        System.out.println("stowrzona gracza " + nickname);
    }

    public Player(String nickname) {
        System.out.println("stowrzona gracza " + nickname);
        this.nickname = nickname;
    }

    public void setNickname(String nickname) {
        System.out.println("zmieniono nick gracza na " + nickname);
        this.nickname = nickname;
    }
}
