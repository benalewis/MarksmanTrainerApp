package com.benlewis.mmtrainerapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    ArrayList<String> champions = new ArrayList<String>();
    int locationOfCorrect;

    TextView champTextView;
    TextView resultTextView;
    TextView scoreTextView;
    TextView timerTextView;

    int score;
    int noQuestions;

    Button a;
    Button b;
    Button c;
    Button d;
    Button playAgainButton;

    public int getChamp (String champ) {
        return MainActivity.this.getResources().getIdentifier
                (champ, "drawable", MainActivity.this.getPackageName());
    }

    public void fillChampions() {
        champions.add("Ashe"); champions.add("Caitlyn"); champions.add("Corki");
        champions.add("Draven"); champions.add("Ezreal"); champions.add("Jhin");
        champions.add("Jinx"); champions.add("Kalista"); champions.add("KogMaw");
        champions.add("Lucian"); champions.add("MissFortune"); champions.add("Quinn");
        champions.add("Sivir"); champions.add("Tristana"); champions.add("Twitch");
        champions.add("Urgot"); champions.add("Varus"); champions.add("Vayne");
    }

    public void playAgain(View view) {

        score = 0;
        noQuestions = 0;

        a.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        champTextView.setVisibility(View.VISIBLE);

        timerTextView.setText("30s");
        scoreTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);

        generateQuestion();

        new CountDownTimer(5050, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000) + "s");
            }

            @Override
            public void onFinish() {

                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("Your score: " +
                        Integer.toString(score) + "/" + Integer.toString(noQuestions));

                a.setVisibility(View.INVISIBLE);
                b.setVisibility(View.INVISIBLE);
                c.setVisibility(View.INVISIBLE);
                d.setVisibility(View.INVISIBLE);
                champTextView.setVisibility(View.INVISIBLE);
            }
        }.start();

    }

    public void generateQuestion() {

        answers.clear();

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

        champTextView.setText(champions.get(correctAnswer));
        //a.setText(champions.get(answers.get(0)));
        a.setBackgroundResource(getChamp(champions.get(answers.get(0)).toLowerCase()));
        b.setBackgroundResource(getChamp(champions.get(answers.get(1)).toLowerCase()));
        c.setBackgroundResource(getChamp(champions.get(answers.get(2)).toLowerCase()));
        d.setBackgroundResource(getChamp(champions.get(answers.get(3)).toLowerCase()));

    }

    public void chooseAnswer(View view) {

        if (view.getTag().toString().equals(Integer.toString(locationOfCorrect))) {
            score++;
            resultTextView.setText("Correct!");
        } else {
            resultTextView.setText("Wrong!");
        }

        noQuestions++;
        scoreTextView.setText(Integer.toString(score)+ "/" + Integer.toString(noQuestions));
        generateQuestion();
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
        timerTextView = (TextView) findViewById(R.id.timerTextView);

        a = (Button) findViewById(R.id.button);
        b = (Button) findViewById(R.id.button1);
        c = (Button) findViewById(R.id.button2);
        d = (Button) findViewById(R.id.button3);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);

        fillChampions();

        generateQuestion();

        playAgain(findViewById(R.id.playAgainButton));
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
        if (id == R.id.settings) {

            Intent i = new Intent(getApplicationContext(), Settings.class);
            startActivity(i);

            return true;
        }

        if (id == R.id.stats) {

            Intent i = new Intent(getApplicationContext(), Stats.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
