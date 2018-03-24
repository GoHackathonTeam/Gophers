package com.gohackathon.gophers;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameplayActivity extends AppCompatActivity {
    private GameEngine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gameplay);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        engine = new GameEngine(this);
        engine.init();
        engine.run();
    }

    public void onGopherClick(View view) {
        engine.onGopherClick(view);
    }
}
