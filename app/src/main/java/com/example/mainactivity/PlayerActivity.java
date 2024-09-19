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

        Intent prevIntent = getIntent();
        playerScore = prevIntent.getIntExtra("Score",-1);
    }

    public void onclickSubmit(View view){
        leaderboardInstance = leaderboardInstance.getInstance();

        RadioGroup rg = (RadioGroup) findViewById(R.id.rg_avatar);
        RadioButton rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        if(rg.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "No Avatar Selected", Toast.LENGTH_LONG).show();
        }
        else{
            avatarID = rg.indexOfChild(findViewById(rg.getCheckedRadioButtonId()));
            avatarID = leaderboardInstance.getImageArray()[avatarID];

            EditText et = (EditText) findViewById(R.id.et_playername);
            playerName = et.getText().toString();

            Player player = new Player(this,playerName,avatarID,playerScore);

            leaderboardInstance.updateLeaderboard(player);

            Intent intent = new Intent(this,LeaderboardActivity.class);
            startActivity(intent);
        }
    }
}