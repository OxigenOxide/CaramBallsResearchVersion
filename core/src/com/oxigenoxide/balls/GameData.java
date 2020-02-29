package com.oxigenoxide.balls;

public class GameData {
    public int highscore=0;
    public boolean[] unlocks=new boolean[9];
    public boolean musicOn = false;
    public int selectedBall=0;
    public int orbs=0;
    public int testerID=0;
    public UserData userData;
    public GameData(){
        unlocks[0]=true;
    }
}
