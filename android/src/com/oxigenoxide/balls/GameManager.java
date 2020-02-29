package com.oxigenoxide.balls;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.games.Player;

public class GameManager implements GameInterface{
    AndroidLauncher androidLauncher;
    Player player;
    long rank=420;
    Handler handler;
    AlertDialog alertDialog;
    public GameManager(AndroidLauncher androidLauncher){
        this.androidLauncher = androidLauncher;
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                alertDialog = new AlertDialog.Builder(GameManager.this.androidLauncher, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                        .setTitle("Results")
                        .setMessage("Different versions of the game are listed here.\nThe green on the bars indicates how much the version has been played by the players.\n\nScroll through the list and select the version you want to play.\n\nHave fun!")
                        .setPositiveButton("close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();
                alertDialog.show();
            }
        };
    }

    @Override
    public void showLeaderBoards() {
        androidLauncher.showLeaderBoards();
    }

    @Override
    public long getRank() {
        return rank;
    }
    @Override
    public void submitScore(int i) {
        androidLauncher.submitScore(i);
    }

    @Override
    public void signOut() {
        androidLauncher.signOut();
    }

    public void startSignIn() {
        androidLauncher.startSignInIntent();
    }

    public void dialog(String s) {
        handler.sendEmptyMessage(0);
    }
}
