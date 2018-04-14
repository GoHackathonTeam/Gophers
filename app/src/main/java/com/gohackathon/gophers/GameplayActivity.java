package com.gohackathon.gophers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameplayActivity extends AppCompatActivity {
    private final int GOPHERS_UPDATE_PERIOD = 500;
    private GameLogic game;
    private SharedPreferences settings;
    private Timer timer;
    private Vibrator vibrator;
    private boolean vibrate;

    private TextView scoreText;
    private View life1;
    private View life2;
    private View life3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gameplay);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //Init
        scoreText = findViewById(R.id.playScore);
        life1 = findViewById(R.id.life1);
        life2 = findViewById(R.id.life2);
        life3 = findViewById(R.id.life3);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean difficultyHard = settings.getBoolean("difficulty", false);
        vibrate = settings.getBoolean("vibrate", true);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        game = new GameLogic(difficultyHard, getGophers(),
                getInitialFreeTopHoles(), getInitialFreeBottomHoles());
        game.setGophersHoles(getInitialGopherHolesIds());

        //Game update
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        game.gopherUpdate();
                        draw();
                    }
                });

            }
        }, GOPHERS_UPDATE_PERIOD, GOPHERS_UPDATE_PERIOD);
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        super.onBackPressed();
    }

    public void onGopherClick(View view) {
        game.onGopherClick(view.getId());
        draw();
    }

    private void update() {
        if (game.getLives() < 1) {
            timer.cancel();
            settings.edit().putString("lastScore", game.getScore()).apply();
            Intent intent = new Intent(GameplayActivity.this, GameOverActivity.class);
            startActivity(intent);
        }

        if (vibrate && game.isGopherMissed()) {
            vibrator.vibrate(300);
        }
    }

    private void draw() {
        scoreText.setText(game.getScore());
        int lives = game.getLives();
        if (lives < 3) {
            life3.setVisibility(View.INVISIBLE);
            if (lives < 2) {
                life2.setVisibility(View.INVISIBLE);
                if (lives < 1) {
                    life1.setVisibility(View.INVISIBLE);
                }
            }
        }

        for (Gopher gopher: game.getGophers()) {
            View gopherView = findViewById(gopher.getId());
            View hole = findViewById(gopher.getHole());
            if (gopher.isAlive()) {
                gopherView.setX(hole.getX());
                gopherView.setY(hole.getY() - gopherView.getHeight() + (int)(hole.getHeight() * 0.8));
                gopherView.setVisibility(View.VISIBLE);
            } else {
                gopherView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private List<Pair<Integer, Integer>> getGophers() {
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        result.add(new Pair<>(R.id.gopher1, 1));
        result.add(new Pair<>(R.id.gopher2, 1));
        result.add(new Pair<>(R.id.gopher3, 2));
        return result;
    }

    private List<Integer> getInitialFreeTopHoles() {
        List<Integer> result = new ArrayList<>();
        result.add(R.id.hole2);
        return result;
    }

    private List<Integer> getInitialFreeBottomHoles() {
        List<Integer> result = new ArrayList<>();
        result.add(R.id.hole5);
        return result;
    }

    private List<Integer> getInitialGopherHolesIds() {
        List<Integer> result = new ArrayList<>();
        result.add(R.id.hole1);
        result.add(R.id.hole3);
        result.add(R.id.hole4);
        return result;
    }

}
