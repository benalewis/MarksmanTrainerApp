package com.benlewis.mmtrainerapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    EditText timerText;
    Button updateTimerButton;
    Button resetDataButton;
    Button exportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timerText = (EditText) findViewById(R.id.setTimerEdit);
        updateTimerButton = (Button) findViewById(R.id.setTimerButton);
        resetDataButton = (Button) findViewById(R.id.setResetButton);
        exportButton = (Button) findViewById(R.id.setExportButton);

        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.setExportButton),
                        "Coming Soon!", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        updateTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTimer();
            }
        });

        resetDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showAlert() {
        try {
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure?")
                    .setMessage("Do you definitely want to reset all data?" +
                            " It cannot be recovered after being reset.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getApplicationContext().deleteDatabase("Scores");
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show(); }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTimer() {

        try {
            MainActivity.sharedPreferences.edit().putInt("timer", Integer.parseInt(timerText.getText().toString())).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
