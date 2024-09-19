package com.example.mainactivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class Card {
    private int cardNum;
    private String cardType;
    private String cardBack;
    private String cardFront;
    private ImageView imageviewCard;
    private boolean isFaceUp;

    public Card(int cNum, String cType, String cBack, String cFront){
        cardNum = cNum;
        cardType = cType;
        cardBack = cBack;
        cardFront = cFront;
        isFaceUp = false;
    }

    public int getCardNum(){
        return cardNum;
    }

    public String getCardBack(){return cardBack;}

    public String getCardFront(){ return cardFront; }

    public boolean isFaceUp(){
            return isFaceUp;
        }

    public String getCardType(){
        return cardType;
    }

    public void setFaceUp(boolean faceUp){ isFaceUp = faceUp; }

    public ImageView getImageviewCard(){
        return imageviewCard;
    }

    public void setImageviewCard(ImageView ivCard){
        imageviewCard = ivCard;
    }
}
