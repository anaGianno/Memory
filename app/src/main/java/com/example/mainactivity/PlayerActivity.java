package com.example.mainactivity;

import android.content.Intent;
import android.nfc.Tag;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PlayerActivity extends AppCompatActivity {
    private int avatarID;
    private String playerName;
    private int playerScore;
    private Leaderboard leaderboardInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.player), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //get the intent from the gameplay to get the player's score
        Intent prevIntent = getIntent();
        playerScore = prevIntent.getIntExtra("Score",-1);
    }

    /**
     * initialises a new player to add to the leaderboard and goes to leaderboard activity
     * @param view
     */
    public void onclickSubmit(View view){
        leaderboardInstance = leaderboardInstance.getInstance();

        //get the avatar colour the player chose
        RadioGroup rg = (RadioGroup) findViewById(R.id.rg_avatar);
        RadioButton rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        //display a toast message if the player didn't pick an avatar
        if(rg.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "No Avatar Selected", Toast.LENGTH_LONG).show();
        }
        else{
            //get the avatar colour chosen using the tag of the radio button
            Object tag = rb.getTag();
            avatarID = Integer.valueOf(tag.toString());

//            avatarID = rg.indexOfChild(findViewById(rg.getCheckedRadioButtonId()));
            avatarID = leaderboardInstance.getImageArray()[avatarID];

            //get the player name from the edittext using its id
            EditText et = (EditText) findViewById(R.id.et_playername);
            playerName = et.getText().toString();

            //display a toast message if the player didn't enter a name
            if(playerName.isEmpty()) {
                Toast.makeText(this, "No name entered", Toast.LENGTH_LONG).show();
                return;
            }
            Player player = new Player(this,playerName,avatarID,playerScore);

            leaderboardInstance.updateLeaderboard(player);

            Intent intent = new Intent(this,LeaderboardActivity.class);
            startActivity(intent);
        }
    }
}