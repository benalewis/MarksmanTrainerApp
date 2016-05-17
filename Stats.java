package com.benlewis.mmtrainerapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class Stats extends AppCompatActivity {

    TextView overallText;
    TextView last7Text;
    TextView bestText;
    TextView recentText;

    Button reset;
    Button upload;

    int last7Score;
    int bestScore;
    int recentScore;

    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        overallText = (TextView) findViewById(R.id.statsOverallResults);

        int overallScore = 0;
        int overallPoints = 0;
        int overallQuestions = 0;

        last7Score = 0;
        bestScore = 0;
        recentScore = 0;

        try {
            c = MainActivity.myDatabase.rawQuery("SELECT * FROM scores", null);

            int totalIndex = c.getColumnIndex("total");
            int pointsIndex = c.getColumnIndex("points");
            int questionsIndex = c.getColumnIndex("questions");
            int idIndex = c.getColumnIndex("id");

            if (c.moveToFirst()) {

              do {
                  //Log.i("id", String.valueOf(c.getInt(idIndex)));
                  Log.i("total", c.getString(totalIndex));
                  overallScore += c.getInt(totalIndex);

                  Log.i("points", String.valueOf(c.getInt(pointsIndex)));
                  overallPoints += c.getInt(pointsIndex);

                  Log.i("questions", String.valueOf(c.getInt(questionsIndex)));
                  overallQuestions += c.getInt(questionsIndex);

                  c.moveToNext();

                } while(c.moveToNext());
            }

            updateScores(overallText, overallScore, overallPoints, overallQuestions);

        } catch (Exception e) {
            e.printStackTrace();
        }



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void updateScores (TextView view, int score, int points, int questions) {
        view.setText(String.valueOf(score) + "%" + " Totals: " +
            points + "/" + questions);
    }

}
