package com.example.mainactivity;


import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class Player {
    private String playerName;
    private Drawable playerAvatar;
    private int playerScore;

    public Player(Context context, String name, int avatarID, int score){
        this.playerName = name;
        this.playerScore = score;
//        playerAvatar = ContextCompat.getDrawable(context,avatarID);
        this.playerAvatar = ContextCompat.getDrawable(context,avatarID);
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
