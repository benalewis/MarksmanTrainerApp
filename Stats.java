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

        double overallScore = 0;
        double overallPoints = 0;
        double overallQuestions = 0;
        double count = 0;

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

                  overallScore += c.getDouble(totalIndex);
                  overallPoints += c.getDouble(pointsIndex);
                  overallQuestions += c.getDouble(questionsIndex);
                  count += c.getCount();

                  c.moveToNext();

                } while(c.moveToNext());
            }
            Log.i("score", String.valueOf(overallScore));
            Log.i("points", String.valueOf((overallPoints)));
            Log.i("questions", String.valueOf((overallQuestions)));
            Log.i("count", String.valueOf(count));

            double averageScore = (overallScore / count) * 10;
            double averagePoints = (overallPoints / count) * 10;
            double averageQuestions = (overallQuestions / count) * 10;

            updateScores(overallText, Math.round(averageScore), averagePoints, averageQuestions);

        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void updateScores (TextView view, double score, double points, double questions) {
        view.setText(String.valueOf(score) + "%" + " Totals: " +
                Integer.valueOf((int) points).toString()
                + "/" +
                Integer.valueOf((int) questions).toString());
    }

}
