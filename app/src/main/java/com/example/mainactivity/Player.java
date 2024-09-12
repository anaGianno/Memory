package com.example.mainactivity;


import android.content.Context;
import android.graphics.drawable.Drawable;


public class Player {
    private String playerName;
    private Drawable playerAvatar;
    private int playerScore;

    public Player(Context context, String name, int avatarID, int score){
        playerName = name;
        playerScore = score;
//        playerAvatar = getResources().getDrawable(avatarID);
    }

    public String getPlayerName(){
        return playerName;
    }

    public Drawable getPlayerAvatar(){
        return playerAvatar;
    }

    public int getPlayerScore(){
        return playerScore;
    }
}
