package com.gohackathon.gophers;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

public class SettingsActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_SPEED = "speed";
    private SharedPreferences mSettings;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSettings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        mSeekBar = findViewById(R.id.setSpeed);

        if (mSettings.contains(APP_PREFERENCES_SPEED))
        mSeekBar.setProgress(mSettings.getInt(APP_PREFERENCES_SPEED, 0));

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putInt(APP_PREFERENCES_SPEED, i);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
