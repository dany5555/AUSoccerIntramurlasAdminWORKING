package com.ausoccer.ausoccerintramurlasadmin;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class MatchControlActivity extends AppCompatActivity {

    // Countdown timer stuff
    long START_TIME_IN_MILLIS = 1200000;
    CountDownTimer countDownTimer;
    boolean timerRunning;
    long timeLeftInMillis = START_TIME_IN_MILLIS;

    public String matchId;
    public FirebaseDatabase database;
    public DatabaseReference currentMatchRef;
    MatchesModel matchesModel;


    // Not played match objects.
    RelativeLayout notPlayedMatchLayout;
    TextView notPlayedMatchNumber, notPlayedMatchDate, notPlayedMatchTime;

    // Live match objects.
    RelativeLayout liveMatchLayout;
    TextView liveMatchNumber, liveResult, liveStatus;


    Button lineupsButton, firstHalfButton, halfTimeButton, secondHalfButton, finalButton;
    private TextView timer;
    private boolean running;
    private long pauseOffset;

    TextView homeTeamName, awayTeamName;
    ImageView homeTeamLogo, awayTeamLogo;

    // Game status objects.

    // Goals objects.
    Button homeDecreaseGoalButton, homeIncreaseGoalButton, awayDecreaseGoalButton, awayIncreaseGoalButton;
    TextView homeGoals, awayGoals;



    // Corners objects.
    Button homeDecreaseCornersButton, homeIncreaseCornersButton, awayDecreaseCornersButton, awayIncreaseCornersButton;
    TextView homeCorners, awayCorners;

    // Off sides objects.
    Button homeDecreaseOffSidesButton, homeIncreaseOffSidesButton, awayDecreaseOffSidesButton, awayIncreaseOffSidesButton;
    TextView homeOffSides, awayOffSides;

    // Yellow cards objects.
    Button homeDecreaseYellowCardsButton, homeIncreaseYellowCardsButton, awayDecreaseYellowCardsButton, awayIncreaseYellowCardsButton;
    TextView homeYellowCards, awayYellowCards;

    // Red cards objects.
    Button homeDecreaseRedCardsButton, homeIncreaseRedCardsButton, awayDecreaseRedCardsButton, awayIncreaseRedCardsButton;
    TextView homeRedCards, awayRedCards;

    // Fouls objects.
    Button homeDecreaseFoulsButton, homeIncreaseFoulsButton, awayDecreaseFoulsButton, awayIncreaseFoulsButton;
    TextView homeFouls, awayFouls;

    int currentHomeGoals = 0;
    int currentAwayGoals = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_control);

        // Game status button casting.
        lineupsButton = findViewById(R.id.lineups_button);
        firstHalfButton = findViewById(R.id.first_half_button);
        halfTimeButton = findViewById(R.id.half_time_button);
        secondHalfButton = findViewById(R.id.second_half_button);
        finalButton = findViewById(R.id.final_button);

        // Not played casting
        notPlayedMatchLayout = findViewById(R.id.match_info_layout_not_played);
        notPlayedMatchNumber = findViewById(R.id.not_played_match_number);
        notPlayedMatchDate = findViewById(R.id.not_played_match_date);
        notPlayedMatchTime = findViewById(R.id.not_played_match_time);

        // Live casting
        liveMatchLayout = findViewById(R.id.match_info_layout_live);
        liveMatchNumber = findViewById(R.id.live_match_number);
        liveResult = findViewById(R.id.live_result);
        liveStatus = findViewById(R.id.live_status);

        timer = findViewById(R.id.timer);
        //timer.setBase(SystemClock.elapsedRealtime());

        /*timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                liveStatus.setText(timer.getText());
                currentMatchRef.child("matchStatus").setValue(liveStatus.getText().toString());
            }
        });*/

        lineupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notPlayedMatchLayout.setVisibility(View.GONE);
                liveMatchLayout.setVisibility(View.VISIBLE);
                liveStatus.setText("LINEUPS");
                currentMatchRef.child("matchStatus").setValue(liveStatus.getText().toString());

            }
        });



        firstHalfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();

                }

            }
        });



        halfTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                currentMatchRef.child("matchStatus").setValue("HALF TIME");



            }
        });

        secondHalfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();

                }
            }
        });

        finalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               resetTimer();
                currentMatchRef.child("matchStatus").setValue("FINAL");

            }
        });









        matchesModel = new MatchesModel();


        matchId = getIntent().getStringExtra("id");
        database = FirebaseDatabase.getInstance();
        currentMatchRef = database.getReference("Matches").child(getIntent().getStringExtra("matchday")).child(matchId);

        Log.v("id", matchId);

        homeTeamName = findViewById(R.id.home_team_name);
        awayTeamName = findViewById(R.id.away_team_name);
        homeTeamLogo = findViewById(R.id.home_team_logo);
        awayTeamLogo = findViewById(R.id.away_team_logo);

        currentMatchRef.child("matchDate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notPlayedMatchDate.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        currentMatchRef.child("matchTime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notPlayedMatchTime.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        currentMatchRef.child("result").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                liveResult.setText(dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final String notPlayed = "NOT PLAYED";
        currentMatchRef.child("matchStatus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String check = dataSnapshot.getValue().toString();
                if (check.equals(notPlayed)) {
                    notPlayedMatchLayout.setVisibility(View.VISIBLE);
                    liveMatchLayout.setVisibility(View.GONE);
                } else {
                    notPlayedMatchLayout.setVisibility(View.GONE);
                    liveMatchLayout.setVisibility(View.VISIBLE);
                }

                liveStatus.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        currentMatchRef.child("matchNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notPlayedMatchNumber.setText("MATCH " + dataSnapshot.getValue().toString());
                liveMatchNumber.setText("MATCH " + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        currentMatchRef.child("homeTeamLogoUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Glide.with(getApplicationContext()).load(dataSnapshot.getValue().toString()).into(homeTeamLogo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        currentMatchRef.child("awayTeamLogoUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Glide.with(getApplicationContext()).load(dataSnapshot.getValue().toString()).into(awayTeamLogo);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        currentMatchRef.child("homeTeamName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                homeTeamName.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        currentMatchRef.child("awayTeamName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                awayTeamName.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Game status objects.





        // Goals objects.
        homeDecreaseGoalButton = findViewById(R.id.home_decrease_goal_button);
        homeIncreaseGoalButton = findViewById(R.id.home_increase_goal_button);
        awayDecreaseGoalButton = findViewById(R.id.away_decrease_goal_button);
        awayIncreaseGoalButton = findViewById(R.id.away_increase_goal_button);
        homeGoals = findViewById(R.id.home_goals_textview);
        awayGoals = findViewById(R.id.away_goals_textview);

        homeDecreaseGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentHomeGoals == 0) {
                    //stop decreasing.
                    homeGoals.setText(Integer.toString(currentHomeGoals));
                } else if (currentHomeGoals > 0){
                    currentHomeGoals -= 1;
                    homeGoals.setText(Integer.toString(currentHomeGoals));
                }

                currentMatchRef.child("result").setValue(homeGoals.getText().toString() + " - " + awayGoals.getText().toString());
            }
        });

        homeIncreaseGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentHomeGoals >= 0) {
                    currentHomeGoals += 1;
                    homeGoals.setText(Integer.toString(currentHomeGoals));

                }
                Log.v("sd", "time: " + liveStatus.getText().toString());
                currentMatchRef.child("result").setValue(homeGoals.getText().toString() + " - " + awayGoals.getText().toString());


            }
        });

        awayDecreaseGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentAwayGoals == 0) {
                    //stop decreasing.
                    awayGoals.setText(Integer.toString(currentAwayGoals));
                } else if (currentAwayGoals > 0){
                    currentAwayGoals -= 1;
                    awayGoals.setText(Integer.toString(currentAwayGoals));
                }

                currentMatchRef.child("result").setValue(homeGoals.getText().toString() + " - " + awayGoals.getText().toString());


            }
        });

        awayIncreaseGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentAwayGoals >= 0) {
                    currentAwayGoals += 1;
                    awayGoals.setText(Integer.toString(currentAwayGoals));
                }

                currentMatchRef.child("result").setValue(homeGoals.getText().toString() + " - " + awayGoals.getText().toString());

            }
        });






    }

    private void startTimer () {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();

            }

            @Override
            public void onFinish() {
                timerRunning = false;



            }
        }.start();

        timerRunning = true;
    }

    private void pauseTimer () {
        if (timerRunning) {
            countDownTimer.cancel();
            timerRunning = false;
        }

    }

    private void resetTimer () {
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountdownText();
        countDownTimer.cancel();

    }

    private void updateCountdownText () {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String .format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timer.setText(timeLeftFormatted);
        currentMatchRef.child("matchStatus").setValue(timeLeftFormatted);

    }

    @Override
    protected void onStart() {
        super.onStart();



        //homeTeamName.setText(getIntent().getStringExtra("homeTeamName"));
        //awayTeamName.setText(getIntent().getStringExtra("awayTeamName"));
        //matchDateAndResult.setText(getIntent().getStringExtra("matchDate"));
        //matchTimeAndStatus.setText(getIntent().getStringExtra("matchTime"));
        // matchNumber.setText(getIntent().getStringExtra("matchNumber"));






    }



}
