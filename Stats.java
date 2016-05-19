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
        recentText = (TextView) findViewById(R.id.statsRecentResults);
        last7Text = (TextView) findViewById(R.id.statsLastResults);
        bestText = (TextView) findViewById(R.id.statsBestResults);

        last7Score = 0;
        bestScore = 0;
        recentScore = 0;

        calcAverage();
        calcRecent();
        calcLastX();
        calcBest();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public double getRating(double points, double questions) {

        return (points + (points - questions));
    }

    public void updateScores (TextView view, double score, double points, double questions) {

        int s = Integer.valueOf((int) score);
        int p = Integer.valueOf((int) points);
        int q = Integer.valueOf((int) questions);

        view.setText(
                String.valueOf(s) + "%" + " Totals: " + String.valueOf(p)
                        + "/" + String.valueOf(q));
    }

    public void calcBest() {

        double score = 0;
        double points = 0;
        double questions = 0;
        double rating = 0;

        try {
            c = MainActivity.myDatabase.rawQuery("SELECT * FROM scores ORDER BY points DESC LIMIT 10", null);

            int totalIndex = c.getColumnIndex("total");
            int pointsIndex = c.getColumnIndex("points");
            int questionsIndex = c.getColumnIndex("questions");

            if (c.moveToFirst()) {

                do {

                    Log.i("Rating: ", String.valueOf(getRating(c.getDouble(pointsIndex), c.getDouble(questionsIndex))));

                    if ( (getRating(c.getDouble(pointsIndex), c.getDouble(questionsIndex))) > rating) {

                        score = c.getDouble(totalIndex);
                        points = c.getDouble(pointsIndex);
                        questions = c.getDouble(questionsIndex);
                        rating = getRating(c.getDouble(pointsIndex), c.getDouble(questionsIndex));
                    }

                    c.moveToNext();

                } while (c.moveToNext());

                updateScores(bestText, score, points, questions);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calcLastX() {

        double score = 0;
        double points = 0;
        double questions = 0;
        double count = 0;

        try {
            c = MainActivity.myDatabase.rawQuery("SELECT * FROM scores ORDER BY id DESC LIMIT 7", null);

            int totalIndex = c.getColumnIndex("total");
            int pointsIndex = c.getColumnIndex("points");
            int questionsIndex = c.getColumnIndex("questions");

            if (c.moveToFirst()) {

                do {

                    score += c.getDouble(totalIndex);
                    points += c.getDouble(pointsIndex);
                    questions += c.getDouble(questionsIndex);
                    count += c.getCount();

                    c.moveToNext();

                } while (c.moveToNext());

                if (count >= 7) {

                updateScores(last7Text, Math.round(score/7),
                        Math.round(points/7),
                        Math.round(questions/7)); }
                else {
                    last7Text.setText("Need more data");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calcRecent() {

        double score;
        double points;
        double questions;

        try {
            c = MainActivity.myDatabase.rawQuery("SELECT * FROM scores ORDER BY id DESC LIMIT 1", null);

            if (c.moveToFirst()) {

                int totalIndex = c.getColumnIndex("total");
                int pointsIndex = c.getColumnIndex("points");
                int questionsIndex = c.getColumnIndex("questions");

                score = c.getDouble(totalIndex);
                points = c.getDouble(pointsIndex);
                questions = c.getDouble(questionsIndex);

                updateScores(recentText, score, points, questions);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calcAverage() {

        double overallScore = 0;
        double overallPoints = 0;
        double overallQuestions = 0;
        double count = 0;

        try {
            c = MainActivity.myDatabase.rawQuery("SELECT * FROM scores", null);

            int totalIndex = c.getColumnIndex("total");
            int pointsIndex = c.getColumnIndex("points");
            int questionsIndex = c.getColumnIndex("questions");
            int idIndex = c.getColumnIndex("id");

            if (c.moveToFirst()) {

                do {
                    overallScore += c.getDouble(totalIndex);
                    overallPoints += c.getDouble(pointsIndex);
                    overallQuestions += c.getDouble(questionsIndex);
                    count += c.getCount();

                    c.moveToNext();

                } while(c.moveToNext());
            }
            /*Log.i("score", String.valueOf(overallScore));
            Log.i("points", String.valueOf((overallPoints)));
            Log.i("questions", String.valueOf((overallQuestions)));
            Log.i("count", String.valueOf(count));*/

            double averageScore = (overallScore / count);
            double averagePoints = (overallPoints / count);
            double averageQuestions = (overallQuestions / count);

            updateScores(overallText, Math.round(averageScore),
                    Math.round(averagePoints),
                    Math.round(averageQuestions));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
