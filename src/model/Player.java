package model;

import java.util.ArrayList;

public class Player {

    private String nickname;
    public ArrayList<String> names;
    private double score;
    private Player left;
    private Player right;

    public Player(String nickname, double score) {
        this.nickname = nickname;
        this.score = score;
        names = new ArrayList<String>();
    }// constructor

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ArrayList<String> getNames() {
        return this.names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    public double getScore() {
        return this.score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Player getLeft() {
        return this.left;
    }

    public void setLeft(Player left) {
        this.left = left;
    }

    public Player getRight() {
        return this.right;
    }

    public void setRight(Player right) {
        this.right = right;
    }

    public boolean add(String name) {
        return names.add(name);
    }

    public boolean empty() {
        return names.isEmpty();
    }

    public int print(int count) {

        for (int i = 0; i < names.size(); i++) {
            System.out.println((count) + ". " + names.get(i) + "      " + this.score);
            count++;
        }
        return count;
    }
}
