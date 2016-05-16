package com.benlewis.mmtrainerapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrect;
    TextView champTextView;
    TextView resultTextView;
    TextView scoreTextView;
    int score;

    Button a;
    Button b;
    Button c;
    Button d;

    public void chooseAnswer(View view) {

        if (view.getTag().toString().equals(Integer.toString(locationOfCorrect))) {
            score++;
            scoreTextView.setText(Integer.toString(score));
            resultTextView.setText("Correct!");
        } else {
            resultTextView.setText("Wrong!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        champTextView = (TextView) findViewById(R.id.champTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);

        a = (Button) findViewById(R.id.button);
        b = (Button) findViewById(R.id.button1);
        c = (Button) findViewById(R.id.button2);
        d = (Button) findViewById(R.id.button3);

        Random random = new Random();

        int correctAnswer = random.nextInt(18);
        int incorrectAnswer;

        locationOfCorrect = random.nextInt(4);

        for (int i = 0; i < 4; i++) {

            if (i == locationOfCorrect) {

                answers.add(correctAnswer);

            } else {

                incorrectAnswer = random.nextInt(18);

                while (incorrectAnswer == correctAnswer) {

                    incorrectAnswer = random.nextInt(18);
                }

                answers.add(incorrectAnswer);
            }
        }

        champTextView.setText(String.valueOf(correctAnswer));
        a.setText(Integer.toString(answers.get(0)));
        b.setText(Integer.toString(answers.get(1)));
        c.setText(Integer.toString(answers.get(2)));
        d.setText(Integer.toString(answers.get(3)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
