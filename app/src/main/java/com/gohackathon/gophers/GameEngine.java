package com.gohackathon.gophers;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameEngine {
    private Timer gameTimer;
    private Activity activity;

    private final List<Gopher> gophers;
    private final List<View> catchedGophers;
    private final List<View> freeHoles;

    private Timer gopherTimer;
    private Integer score = 0;
    private TextView scoreText;
    final Random random;

    public GameEngine(Activity activity) {
        random = new Random();
        gameTimer = new Timer();
        gopherTimer = new Timer();
        this.activity = activity;
        scoreText = activity.findViewById(R.id.playScore);

        catchedGophers = new ArrayList<>();
        gophers = new ArrayList<>();
        freeHoles = new ArrayList<>();

    }

    public void init() {
        gophers.add(new Gopher(activity, R.id.gopher1,
                true, activity.findViewById(R.id.hole1)));
        gophers.add(new Gopher(activity, R.id.gopher2,
                false, activity.findViewById(R.id.hole5)));
        gophers.add(new Gopher(activity, R.id.gopher3,
                true, activity.findViewById(R.id.hole4)));

        freeHoles.add(activity.findViewById(R.id.hole2));
        freeHoles.add(activity.findViewById(R.id.hole3));
    }

    public void run() {
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gameLoop();
                    }
                });
            }
        }, 20, 20);

        gopherTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                gopherUpdate();
            }
        }, 500, 500);
    }

    private void gopherUpdate() {

        for (Gopher gopher: gophers) {
            gopher.decrLifeTime();
            if (gopher.getLiveTime() == 0 && gopher.isVisible()) {
                score--;
            }

            if (gopher.getLiveTime() <= 0) {
                if (gopher.isVisible()) {
                    gopher.setVisible(false);
                } else {
                    boolean r = random.nextBoolean();
                    gopher.setVisible(r);
                    if (r) {
                        gopher.resetTime();
                    }
                }
            }
        }
    }

    private void gameLoop() {
        for (View gopherView: catchedGophers) {
            for (Gopher g: gophers) {
                if (gopherView.equals(g.getImageView())) {
                    g.setVisible(false);
                }
            }
        }
        catchedGophers.clear();

        for (Gopher gopher: gophers) {
            if (gopher.isVisible()) {
                gopher.getImageView().setVisibility(View.VISIBLE);
            } else {
                gopher.getImageView().setVisibility(View.INVISIBLE);
                moveGopher(gopher);
            }
        }
        scoreText.setText(score.toString());
    }

    public void onGopherClick(View gopher) {
        score++;
        catchedGophers.add(gopher);
    }

    private void moveGopher(Gopher gopher) {
        View hole = freeHoles.get(random.nextInt(freeHoles.size() - 1));
        freeHoles.add(gopher.getHole());
        gopher.setHole(hole);
        freeHoles.remove(hole);

        gopher.getImageView().setX(hole.getX());
        gopher.getImageView().setY(hole.getY() - gopher.getHeight() + (int)(hole.getHeight() * 0.8));

    }
}
