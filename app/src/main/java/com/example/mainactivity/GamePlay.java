package com.example.mainactivity;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class GamePlay {
    private int MAX_MATCHES = 6;
    private int totalGuesses = 0;
    private int totalCorrect = 0;
    private boolean isFirstGuess = true;
    private Card cardFirst;
    private Card cardSecond;
    private ArrayList<String> cardTypes = new ArrayList<String>(Arrays.asList(
            "Coder","Coder","Artist","Artist","Astronaut","Astronaut",
            "Doctor","Doctor","Scientist","Scientist","Welder","Welder"
    ));
    private ArrayList<Card> cards = new ArrayList<Card>();
    private TextView textviewGuesses;
    private Context context;


//    c?
    public GamePlay(Context c){
        context = c;
    }

    public void setupGame(){
        String cardBack = "img_card_back.png";

        ArrayList<String> cardFrontTypes = new ArrayList<String>(Arrays.asList(
                "img_card_front_coder.png","img_card_front_coder.png",
                "img_card_front_artist.png","img_card_front_artist.png",
                "img_card_front_astronaut.png","img_card_front_astronaut.png",
                "img_card_front_doctor.png","img_card_front_doctor.png",
                "img_card_front_scientist.png","img_card_front_scientist.png",
                "img_card_front_welder.png","img_card_front_welder.png"));

        Card card;
        String[] frontArray = getResources().getStringArray(R.array.string_array_front);
        for(int i = 0; i < cards.size(); i++){
            card = new Card(i,cardTypes.get(i),cardBack,cardFrontTypes.get(i));
            cards.add(card);
        }
        setupImageviewsAndOnclicks();
        displayCards();
        updateGuessesTextview();
    }

    public void setupImageviewsAndOnclicks(){
        Card card;
        for(int i = 0; i < cards.size(); i++){
            card = cards.get(i);

            card.setImageviewCard();
        }
    }

    public void displayCardFaceUp(Card card){
        card.setImageviewCard();
        card.setFaceUp(true);
        displayCards();
    }

    public void displayCardFaceDown(Card card){
        card.setFaceUp(false);
        displayCards();
    }

    public void displayCards(){
        Card card;
        for(int i = 0; i < cards.size();i++){
            card = cards.get(i);
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
//        totalGuesses++;
    }

    public void checkCardMatch(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 1s = 1000ms
            }
        }, 1000);
    }

    public void gameOver(){

    }

    public Card getCardByCardNum(int cardNum){
        return cards.get(cardNum);
    }

    public void updateGuessesTextview(){
        TextView textviewGuesses = (TextView)findViewByID(R.id.tv_num_guesses);
        textviewGuesses.setText("Guesses: " + totalGuesses);
    }
}

