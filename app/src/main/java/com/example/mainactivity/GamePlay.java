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
import java.util.Collections;

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

    /**
     * set context to value passed in and guesses text to view found by id
     * @param c
     */
    public GamePlay(Context c){
        context = c;
        textviewGuesses = ((Activity) context).findViewById(R.id.tv_num_guesses);
    }

    /**
     * create and add all cards to the list of cards and shuffle. display those cards and display the guesses text
     */
    public void setupGame(){
        String cardBack = "img_card_back";
        int DECK_SIZE = 12;

        Card card;
        //add all cards to the list of cards
        for(int i = 0; i < DECK_SIZE; i++){
            card = new Card(i,cardTypes.get(i),cardBack,"img_card_front_"+cardTypes.get(i));
            cards.add(card);
        }
        //shuffle the cards
        java.util.Collections.shuffle(cards);

        setupImageviewsAndOnclicks();
        displayCards();
        updateGuessesTextview();
    }

    /**
     * get image view for all cards by id and set them. set click event for all cards
     */
    public void setupImageviewsAndOnclicks(){
        Card card;
        ImageView iv;
        for(int i = 0; i < cards.size(); i++) {
            card = getCardByCardNum(i);

            iv = ((Activity)context).findViewById(context.getResources().getIdentifier("iv_card_" + card.getCardNum(), "id", context.getPackageName()));
            card.setImageviewCard(iv);

            iv.setOnClickListener(v -> {
                onclickCard(v);
            });
        }
    }

    /**
     * set image of cards using getCardFront() method of the card and set the card as face up
     * @param card
     */
    public void displayCardFaceUp(Card card){
        int imageID = context.getResources().getIdentifier(card.getCardFront(),"drawable",context.getPackageName());
        Drawable drawable = context.getResources().getDrawable(imageID);
        card.getImageviewCard().setImageDrawable(drawable);

        card.setFaceUp(true);
    }

    /**
     * set image of cards using getCardBack() method of the card and set the card as face down
     * @param card
     */
    public void displayCardFaceDown(Card card){
        int imageID = context.getResources().getIdentifier(card.getCardBack(),"drawable",context.getPackageName());
        Drawable drawable = context.getResources().getDrawable(imageID);
        card.getImageviewCard().setImageDrawable(drawable);

        card.setFaceUp(false);
    }

    /**
     * display all cards, displaying them as face up or down using card.isFaceUp()
     */
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

    /**
     * flips the given card by displaying it face down or up using card.isFaceUp()
     * @param card
     */
    public void flipCard(Card card){
        boolean isFaceUp = card.isFaceUp();

        if(isFaceUp){
            displayCardFaceDown(card);
        }
        else{
            displayCardFaceUp(card);
        }
    }

    /**
     * flip the given card; not allowing more than two cards to be flipped in succession
     * @param view
     */
    public void onclickCard(View view){
        Card card;
        //check all cards to see which one was clicked
        for(int i = 0; i < cards.size();i++){
            card = getCardByCardNum(i);

            //check if player is clicking their first card
            if(view == card.getImageviewCard() && isFirstGuess && cardFirst == null){
                //don't flip the card if it has been clicked already
                if(card.isFaceUp()){
                    return;
                }
                cardFirst = card;
                flipCard(cardFirst);
                isFirstGuess = false;
            }
            //check if player is clicking their second card
            else if(view == card.getImageviewCard() && !isFirstGuess && cardSecond == null){
                //don't flip the card if it has been clicked already
                if(card.isFaceUp()){
                    return;
                }
                cardSecond = card;
                flipCard(cardSecond);
                //two cards have been selected; increment the amount of guesses the player has made and check if they match
                totalGuesses++;
                checkCardMatch();
                isFirstGuess = true;
            }
        }
    }

    /**
     * check if the two selected cards match; ending the game if all have been matched
     */
    public void checkCardMatch(){
        final Handler handler = new Handler();
            //cards don't match; let the player see the cards before they are flipped back
            if(cardFirst.getCardType() != cardSecond.getCardType()){
                updateGuessesTextview();
                handler.postDelayed(() -> {
                    // Do something after 1s = 1000ms
                    flipCard(cardFirst);
                    flipCard(cardSecond);
                    //reset player's first and second chosen cards
                    cardFirst = null;
                    cardSecond = null;
                }, 1000);
            }
            //cards match; increment how many player has got correct
            else{
                totalCorrect++;
                updateGuessesTextview();
                //end game if all cards are matched
                if(totalCorrect == MAX_MATCHES){
                    gameOver();
                }
                //reset player's first and second chosen cards
                cardFirst = null;
                cardSecond = null;
            }
    }

    /**
     * go to the player activity class when all cards have been matched
     */
    public void gameOver(){
        Toast.makeText(context, "GAME OVER", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context,PlayerActivity.class);
        intent.putExtra("Score",totalGuesses);
        context.startActivity(intent);
    }

    /**
     * return the card based on card number given
     * @param cardNum
     * @return
     */
    public Card getCardByCardNum(int cardNum){
        return cards.get(cardNum);
    }

    /**
     * change the text do display the current amount of guesses of the player
     */
    public void updateGuessesTextview(){
        textviewGuesses.setText("Guesses: " + totalGuesses);
    }
}

