package com.gohackathon.gophers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;

import static com.gohackathon.gophers.SettingsActivity.APP_PREFERENCES;
import static com.gohackathon.gophers.SettingsActivity.APP_PREFERENCES_SPEED;

public class Gopher {
    private SharedPreferences settings;


    private ImageView imageView;
    private int id;
    private boolean isVisible;
    private View hole;
    private int maxLifeTime = 4;
    private int liveTime = 4;

    public Gopher(Activity activity, int imageViewId, boolean isVisible, View hole) {
        this.id = imageViewId;
        this.imageView = activity.findViewById(imageViewId);
        this.isVisible = isVisible;
        this.hole = hole;

        settings = activity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        switch (settings.getInt(APP_PREFERENCES_SPEED, 0)) {
            case 0:
                maxLifeTime = 7;
                break;
            case 1:
                maxLifeTime = 5;
                break;
            case 2:
                maxLifeTime = 4;
                break;
            case 3:
                maxLifeTime = 2;
                break;
        }
    }

    public int getId() {
        return id;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getWidth() {
        return imageView.getWidth();
    }

    public int getHeight() {
        return imageView.getHeight();
    }

    public View getHole() {
        return hole;
    }

    public void setHole(View hole) {
        this.hole = hole;
    }

    public int getLiveTime() {
        return liveTime;
    }

    public void decrLifeTime() {
        liveTime--;
    }

    public void resetTime() {
        liveTime = maxLifeTime;
    }
}
