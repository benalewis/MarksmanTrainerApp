package com.benlewis.mmtrainerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> answers = new ArrayList<Integer>();
    ArrayList<String> champions = new ArrayList<String>();

    int locationOfCorrect;
    int timer;

    TextView champTextView;
    TextView resultTextView;
    TextView scoreTextView;
    TextView timerTextView;

    double score;
    double noQuestions;
    double total;

    Button a;
    Button b;
    Button c;
    Button d;
    Button playAgainButton;
    Button stopButton;
    Button startButton;

    static SQLiteDatabase myDatabase;
    static SharedPreferences sharedPreferences;

    RelativeLayout extraLayout;
    GridLayout gridLayout;
    RelativeLayout infoLayout;

    MediaPlayer mediaPlayer;

    public void updateTimer() {
        if (sharedPreferences.getInt("timer", 0) == 0) {
            timer = 30;
        } else {
            timer = sharedPreferences.getInt("timer", 0);
        }
    }

    public void initDatabase() {

        try {
            myDatabase = this.openOrCreateDatabase("Scores", MODE_PRIVATE, null);

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS scores (total DOUBLE(3), points DOUBLE(3)," +
                    "id INTEGER PRIMARY KEY, questions DOUBLE(3))");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

        gridLayout.setVisibility(View.VISIBLE);
        a.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        champTextView.setVisibility(View.VISIBLE);
        resultTextView.setVisibility(View.VISIBLE);

        timerTextView.setText(timer + "s");
        scoreTextView.setText("0/0");
        resultTextView.setText("Good Luck!");
        playAgainButton.setVisibility(View.INVISIBLE);
        stopButton.setVisibility(View.VISIBLE);

        generateQuestion();

        final CountDownTimer countDownTimer = new CountDownTimer((timer*1000)+50, 995) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000) + "s");
            }

            @Override
            public void onFinish() {

                playFinish();

                if ( score <= 0 || noQuestions <= 0) {
                    total = 0;
                } else {
                    total = Math.round(score / (noQuestions)*100);
                }

                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("Your score: " +
                        Integer.valueOf((int) score).toString() + "/" +
                        Integer.valueOf((int) noQuestions).toString());

               /* a.setVisibility(View.INVISIBLE);
                b.setVisibility(View.INVISIBLE);
                c.setVisibility(View.INVISIBLE);
                d.setVisibility(View.INVISIBLE);
                champTextView.setVisibility(View.INVISIBLE);*/

                gridLayout.setVisibility(View.INVISIBLE);

                myDatabase.execSQL("INSERT INTO scores (total, points, questions) VALUES (" +
                        total + ", " + score + ", " + noQuestions + ")");
                stopButton.setVisibility(View.INVISIBLE);
            }
        }.start();

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                playAgainButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.INVISIBLE);
                gridLayout.setVisibility(View.INVISIBLE);
                resultTextView.setVisibility(View.INVISIBLE);
            }
        });
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
            playCorrect();
        } else {
            resultTextView.setText("Wrong!");
            playIncorrect();
        }

        noQuestions++;

        scoreTextView.setText(
                Integer.valueOf((int) score).toString() + "/" +
                        Integer.valueOf((int) noQuestions).toString());

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

        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        infoLayout = (RelativeLayout) findViewById(R.id.infoLayout);
        extraLayout = (RelativeLayout) findViewById(R.id.extraLayout);

        gridLayout.setVisibility(View.INVISIBLE);
        infoLayout.setVisibility(View.INVISIBLE);
        extraLayout.setVisibility(View.INVISIBLE);

        sharedPreferences = this.getSharedPreferences("com.benlewis.mmtrainerapp", MODE_PRIVATE);

        a = (Button) findViewById(R.id.button);
        b = (Button) findViewById(R.id.button1);
        c = (Button) findViewById(R.id.button2);
        d = (Button) findViewById(R.id.button3);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        stopButton = (Button) findViewById(R.id.mainStopButton);
        startButton = (Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gridLayout.setVisibility(View.VISIBLE);
                infoLayout.setVisibility(View.VISIBLE);
                extraLayout.setVisibility(View.VISIBLE);

                initDatabase();
                fillChampions();
                generateQuestion();
                updateTimer();
                playAgain(findViewById(R.id.playAgainButton));

                startButton.setVisibility(View.INVISIBLE);
            }
        });
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

            try {

                mediaPlayer.stop();

            } catch (Exception e) {

                e.printStackTrace();
            }

            Intent i = new Intent(getApplicationContext(), Settings.class);
            startActivity(i);

            return true;
        }

        if (id == R.id.stats) {

            try {

                mediaPlayer.stop();

            } catch (Exception e) {

                e.printStackTrace();
            }

            Intent i = new Intent(getApplicationContext(), Stats.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void playCorrect() {

        mediaPlayer = MediaPlayer.create(this, R.raw.ting);

        mediaPlayer.start();
    }

    public void playIncorrect() {
        mediaPlayer = MediaPlayer.create(this, R.raw.wrong);

        mediaPlayer.start();
    }

    public void playFinish() {
        mediaPlayer = MediaPlayer.create(this, R.raw.end);

        mediaPlayer.start();
    }
}
