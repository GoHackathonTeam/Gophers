package com.gohackathon.gophers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {
    private TextView lastScore;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        lastScore = findViewById(R.id.gameOverScore);
        lastScore.setText(settings.getString("lastScore", "0"));
    }

    @Override
    public void onBackPressed() {
        //Do nothing
    }

    public void onMenuClick(View view) {
        Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
