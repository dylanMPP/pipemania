public class Player {

    private String nickname;
    private double time;
    private double score;

    public Player(String nickname){
        this.nickname = nickname;
        this.time = time;
        this.score = score;
    } // constructor

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
