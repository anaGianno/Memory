package com.example.mainactivity;

import static android.app.PendingIntent.getActivity;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class GamePlay {
    private int MAX_MATCHES = 6;
    private int totalGuesses = 0;
    private int totalCorrect = 0;
    private boolean isFirstGuess = true;
    private Card cardFirst = null;
    private Card cardSecond = null;
    private ArrayList<String> cardTypes = new ArrayList<String>(Arrays.asList(
            "coder","coder","artist","artist","astronaut","astronaut",
            "doctor","doctor","scientist","scientist","welder","welder"
    ));
    private ArrayList<Card> cards = new ArrayList<Card>();
    private Context context;
    private TextView textviewGuesses;


//    c?
    public GamePlay(Context c){
        context = c;
        textviewGuesses = ((Activity) context).findViewById(R.id.tv_num_guesses);

    }

    public void setupGame(){
        String cardBack = "img_card_back";
        int DECK_SIZE = 12;

        Card card;
        for(int i = 0; i < DECK_SIZE; i++){
            card = new Card(i,cardTypes.get(i),cardBack,"img_card_front_"+cardTypes.get(i));
            cards.add(card);
        }
//        java.util.Collections.shuffle(cards);

        setupImageviewsAndOnclicks();
        displayCards();
        updateGuessesTextview();
    }

    public void setupImageviewsAndOnclicks(){
        Card card;
        ImageView iv;
        for(int i = 0; i < cards.size(); i++) {
            card = getCardByCardNum(i);

            iv = ((Activity)context).findViewById(context.getResources().getIdentifier("iv_card_" + i, "id", context.getPackageName()));
            card.setImageviewCard(iv);

//            iv.setOnClickListener(new View.OnClickListener(){
//                public void onClick(View v){
//
//                }
//            });

            iv.setOnClickListener(v -> {
                onclickCard(v);
            });
        }
    }

    public void displayCardFaceUp(Card card){
        int imageID = context.getResources().getIdentifier(card.getCardFront(),"drawable",context.getPackageName());
        Drawable drawable = context.getResources().getDrawable(imageID);
        card.getImageviewCard().setImageDrawable(drawable);

        card.setFaceUp(true);
    }

    public void displayCardFaceDown(Card card){
        int imageID = context.getResources().getIdentifier(card.getCardBack(),"drawable",context.getPackageName());
        Drawable drawable = context.getResources().getDrawable(imageID);
        card.getImageviewCard().setImageDrawable(drawable);

        card.setFaceUp(false);
    }

    public void displayCards(){
        Card card;
        for(int i = 0; i < cards.size();i++){
            card = getCardByCardNum(i);
            if(card.isFaceUp()){
                displayCardFaceUp(card);
            }
            else{
                displayCardFaceDown(card);
            }
        }
    }

    public void flipCard(Card card){
        boolean isFaceUp = card.isFaceUp();

        if(isFaceUp){
            displayCardFaceDown(card);
        }
        else{
            displayCardFaceUp(card);
        }
    }

    public void onclickCard(View view){
        Card card;
        for(int i = 0; i < cards.size();i++){
            card = getCardByCardNum(i);

            if(view == card.getImageviewCard() && cardFirst == null){
                if(card.isFaceUp()){
                    return;
                }
                cardFirst = card;
                flipCard(cardFirst);
            }
            else if(view == card.getImageviewCard() && cardSecond == null){
                if(card.isFaceUp()){
                    return;
                }
                cardSecond = card;
                flipCard(cardSecond);
                totalGuesses++;
                checkCardMatch();
            }
        }
    }

    public void checkCardMatch(){
        final Handler handler = new Handler();

            if(cardFirst.getCardType() != cardSecond.getCardType()){
                updateGuessesTextview();
                handler.postDelayed(() -> {
                    // Do something after 1s = 1000ms
                    flipCard(cardFirst);
                    flipCard(cardSecond);
                    cardFirst = null;
                    cardSecond = null;
                }, 1000);
            }
            else{
                totalCorrect++;
                updateGuessesTextview();
                if(totalCorrect == MAX_MATCHES){
                    gameOver();
                }
                cardFirst = null;
                cardSecond = null;
            }
    }

    public void gameOver(){
        Toast.makeText(context, "GAME OVER", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context,PlayerActivity.class);
        intent.putExtra("Score",totalGuesses);
        context.startActivity(intent);
    }

    public Card getCardByCardNum(int cardNum){
        return cards.get(cardNum);
    }

    public void updateGuessesTextview(){
        textviewGuesses.setText("Guesses: " + totalGuesses);
    }
}

