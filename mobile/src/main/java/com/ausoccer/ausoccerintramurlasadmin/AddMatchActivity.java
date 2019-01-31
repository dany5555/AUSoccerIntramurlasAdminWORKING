package com.ausoccer.ausoccerintramurlasadmin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddMatchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView homeTeamName;
    TextView awayTeamName;

    ImageView homeTeamLogo, awayTeamLogo;

    Calendar calendar = Calendar.getInstance();
    TimePickerDialog timePickerDialog;
    TextView selectedDate, selectedTime;


    Button selectDateButton, selectTimeButton, doneButton;
    EditText mMatchNumber, mMatchday;
    //Spinner selectMatchday;
    ArrayList<String> matchdayList = new ArrayList<>();
    ArrayList<String> teamsList = new ArrayList<>();
    ArrayList<String> selectedTeamLogo = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference matchdaysRef = database.getReference().child("Matches");
    DatabaseReference teamsRef = database.getReference().child("Teams");

    // Recover data to add to new match object.
    String mHomeTeamName, mHomeTeamLogoUrl;
    String mAwayTeamName, mAwayTeamLogoUrl;
    String matchday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);

        teamsList = retrieveTeams(teamsRef,teamsList);

        homeTeamName = findViewById(R.id.home_team_name);
        awayTeamName = findViewById(R.id.away_team_name);

        homeTeamLogo = findViewById(R.id.home_team_logo);
        awayTeamLogo = findViewById(R.id.away_team_logo);

        doneButton = findViewById(R.id.done_button);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMatch(matchdaysRef, mHomeTeamName, mHomeTeamLogoUrl, mAwayTeamName, mAwayTeamLogoUrl);
            }
        });

        homeTeamLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String [] teamNames = teamsList.toArray(new String[teamsList.size()]);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddMatchActivity.this);
                builder.setTitle("Select Home Team");
                builder.setSingleChoiceItems(teamNames, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(getApplicationContext(), "You have selected " + teamNames[i], Toast.LENGTH_SHORT).show();
                        homeTeamName.setText(teamNames[i]);
                        mHomeTeamName = homeTeamName.getText().toString();

                        teamsRef.child(homeTeamName.getText().toString()).child("teamLogoUrl").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mHomeTeamLogoUrl = dataSnapshot.getValue().toString();
                                Glide.with(getApplicationContext()).load(mHomeTeamLogoUrl).into(homeTeamLogo);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        dialogInterface.dismiss();


                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        awayTeamLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String [] teamNames = teamsList.toArray(new String[teamsList.size()]);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddMatchActivity.this);
                builder.setTitle("Select Home Team");
                builder.setSingleChoiceItems(teamNames, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(getApplicationContext(), "You have selected " + teamNames[i], Toast.LENGTH_SHORT).show();
                        awayTeamName.setText(teamNames[i]);
                        mAwayTeamName = awayTeamName.getText().toString();

                        teamsRef.child(awayTeamName.getText().toString()).child("teamLogoUrl").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mAwayTeamLogoUrl = dataSnapshot.getValue().toString();
                                Glide.with(getApplicationContext()).load(mAwayTeamLogoUrl).into(awayTeamLogo);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        dialogInterface.dismiss();


                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });


        selectedDate = findViewById(R.id.selected_date);
        selectedTime = findViewById(R.id.selected_time);

        selectDateButton = findViewById(R.id.select_date_button);
        selectTimeButton = findViewById(R.id.select_time_button);
        mMatchNumber = findViewById(R.id.match_number);
        mMatchday = findViewById(R.id.match_day);



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 int hour = calendar.get(Calendar.HOUR_OF_DAY);
                 int minutes = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(AddMatchActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hour;
                        String minutes;

                        if (hourOfDay < 10) {
                            hour = "0" + hourOfDay;
                        } else {
                            hour = String.valueOf(hourOfDay);
                        }

                        if (minute < 10) {
                            minutes = "0" + minute;
                        } else {
                            minutes = String.valueOf(minute);
                        }

                        selectedTime.setText(hour + ":" + minutes);
                    }
                }, hour, minutes, false);

                timePickerDialog.show();
            }
        });

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddMatchActivity.this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        matchday = mMatchday.getText().toString();

        //selectMatchday = findViewById(R.id.matchday_spinner);

        //final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, matchdayList);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //selectMatchday.setAdapter(dataAdapter);

        //selectMatchday.setOnItemSelectedListener(this);

        // Retrieving all match days and rounds. Just the keys, not the values.
        /*matchdaysRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                matchdayList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    matchdayList.add(ds.getKey());
                }

                selectMatchday.setAdapter(dataAdapter);
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/










    }

    private void updateLabel() {
        //String myFormat = "EEEE"; //In which you need put here
        //SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        //String s = sdf.format(calendar.getTime());
        //String a = (sdf.format(calendar.getTime())).substring(0, 3);
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
        String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String fullDate = dayOfWeek + " " + month + " " + day;
        selectedDate.setText(fullDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        //String item = parent.getItemAtPosition(position).toString();
        //mMatchday.setText(item);
        //matchday = mMatchday.getText().toString();

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private ArrayList<String> retrieveTeams (final DatabaseReference databaseReference, final ArrayList<String> teams) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teams.clear();
                //selectedTeamLogo.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    teams.add(ds.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return teams;

    }

    private void addMatch (DatabaseReference matchdaysRef, String homeTeamName, String homeTeamLogoUrl, String awayTeamName, String awayTeamLogoUrl) {

        String matchday = mMatchday.getText().toString();

        String homeTeamUid = homeTeamName;
        String awayTeamUid = awayTeamName;
        String matchUid = homeTeamName + " vs " + awayTeamName;
        String result = "0 - 0";
        String matchStatus = "NOT PLAYED";
        int homeTeamGoals = 0;
        int awayTeamGoals = 0;
        int matchNumber = Integer.valueOf(mMatchNumber.getText().toString());
        String matchDate = selectedDate.getText().toString();
        String matchTime = selectedTime.getText().toString();

        MatchesModel matchesModel = new MatchesModel();

        matchesModel.setHomeTeamName(homeTeamName);
        matchesModel.setHomeTeamLogoUrl(homeTeamLogoUrl);
        matchesModel.setHomeTeamUid(homeTeamUid);
        matchesModel.setHomeTeamGoals(homeTeamGoals);

        matchesModel.setAwayTeamName(awayTeamName);
        matchesModel.setAwayTeamLogoUrl(awayTeamLogoUrl);
        matchesModel.setAwayTeamUid(awayTeamUid);
        matchesModel.setAwayTeamGoals(awayTeamGoals);

        matchesModel.setMatchUid(matchUid);
        matchesModel.setMatchDate(matchDate);
        matchesModel.setMatchTime(matchTime);
        matchesModel.setMatchNumber(matchNumber);
        matchesModel.setMatchStatus(matchStatus);
        matchesModel.setResult(result);

        try {
            matchdaysRef.child(matchday).child(matchesModel.getMatchUid()).setValue(matchesModel);
        } catch (DatabaseException ex) {
            ex.printStackTrace();
        }

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);

    }




}
