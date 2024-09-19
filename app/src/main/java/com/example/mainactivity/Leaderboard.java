package com.example.mainactivity;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Leaderboard {
    private static final int[] IMG_ARRAY = new int[]{R.drawable.img_card_front_coder,R.drawable.img_card_front_artist, R.drawable.img_card_front_astronaut,R.drawable.img_card_front_doctor,R.drawable.img_card_front_scientist};
    private ArrayList<Player> leaderboard = new ArrayList<Player>();
    private static Leaderboard leaderboardInstance = new Leaderboard();

    private Leaderboard(){

    }

    public static Leaderboard getInstance(){
        return leaderboardInstance;
    }

    public static int[] getImageArray(){
        return IMG_ARRAY;
    }

    public void updateLeaderboard(Player currentPlayer){
        if(leaderboard.size()==5){
            leaderboard.remove(4);
        }
        leaderboard.add(currentPlayer);
        leaderboard.sort(Comparator.comparing(Player::getPlayerScore));
    }

    public void displayLeaderboard(Context context){
        for(int i = 1; i<leaderboard.size()+1;i++){
            int ivAvatarID = context.getResources().getIdentifier("iv_leaderboard_avatar"+i,"id",context.getPackageName());
            ImageView ivAvatar =  ((Activity)context).findViewById(ivAvatarID);
            ivAvatar.setImageDrawable(leaderboard.get(i-1).getPlayerAvatar());

            int tvNameID = context.getResources().getIdentifier("tv_leaderboard_name"+i,"id",context.getPackageName());
            TextView tvName =  ((Activity)context).findViewById(tvNameID);
            tvName.setText(leaderboard.get(i-1).getPlayerName() + " scored:");

            int tvScoreID = context.getResources().getIdentifier("tv_leaderboard_score"+i,"id",context.getPackageName());
            TextView tvScore =  ((Activity)context).findViewById(tvScoreID);
            tvScore.setText(String.valueOf(leaderboard.get(i-1).getPlayerScore()));
        }
    }
}
