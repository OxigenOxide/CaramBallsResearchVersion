package com.oxigenoxide.balls;

public interface GameInterface {
    void showLeaderBoards();
    long getRank();
    void submitScore(int i);
    void signOut();
    void startSignIn();
    void dialog(String s);
}
