package model;

public class Scoreboard {

    private Player root;
    private String msg;

    public Scoreboard() {
        this.root = root;
    }

    public boolean addPlayer(Player player) {
        return addPlayer(player, root);
    } // add Node

    private boolean addPlayer(Player player, Player current) {

        if (root == null) {
            this.root = player;
            return true;
        } else {

            if(player.getNickname().equalsIgnoreCase(current.getNickname())){
                current.setScore(current.getScore()+ player.getScore());
                return true;
            }

            if(player.getScore() > current.getScore() ){

                if (current.getRight()==null){
                    current.setRight(player);
                    return true;
                } else {
                    return addPlayer(player, current.getRight());
                } // ifelse -> add at right if it is null, if it isnt then recursion comes
            } // if right

            else {

                if (current.getLeft()==null){
                    current.setLeft(player);
                    return true;
                } else {
                    return addPlayer(player, current.getLeft());
                } // ifelse -> add at left if it is null, if it isnt then recursion comes

            } // ifelse right or left
        }
    }

    public void printScore() {
        msg = "";
        printScore(root);
    }

    public void printScore(Player current) {

        if (current == null) {
            return;
        }

        printScore(current.getRight());
        msg += "\n"+(" | ") + "NICKNAME: " + current.getNickname() + "\t\t | SCORE: " + current.getScore();
        printScore(current.getLeft());
    }

    public Player getRoot() {
        return root;
    }

    public void setRoot(Player root) {
        this.root = root;
    }

    public String getMsg(){
        return this.msg;
    }
}

