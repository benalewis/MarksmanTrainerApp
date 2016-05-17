package com.benlewis.mmtrainerapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    EditText timerText;
    Button updateTimerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timerText = (EditText) findViewById(R.id.setTimetEdit);
        updateTimerButton = (Button) findViewById(R.id.setTimerButton);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
