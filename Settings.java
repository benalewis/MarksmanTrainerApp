package com.benlewis.mmtrainerapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    EditText timerText;
    Button updateTimerButton;
    Button resetDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timerText = (EditText) findViewById(R.id.setTimerEdit);
        updateTimerButton = (Button) findViewById(R.id.setTimerButton);
        resetDataButton = (Button) findViewById(R.id.setResetButton);

        updateTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTimer();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void updateTimer() {

        try {
            MainActivity.sharedPreferences.edit().putInt("timer", Integer.parseInt(timerText.getText().toString())).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
