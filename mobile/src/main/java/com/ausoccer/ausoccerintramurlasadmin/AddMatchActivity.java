package com.ausoccer.ausoccerintramurlasadmin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddMatchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Calendar calendar = Calendar.getInstance();
    TimePickerDialog timePickerDialog;
    TextView selectedDate, selectedTime;


    Button selectDateButton, selectTimeButton;
    EditText matchMumber;
    Spinner selectMatchday;
    List<String > matchdayList = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference matchdaysRef = database.getReference().child("Matches");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);


        selectedDate = findViewById(R.id.selected_date);
        selectedTime = findViewById(R.id.selected_time);

        selectDateButton = findViewById(R.id.select_date_button);
        selectTimeButton = findViewById(R.id.select_time_button);
        matchMumber = findViewById(R.id.match_number);



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
                        selectedTime.setText(hourOfDay + ":" + minute);
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

        selectMatchday = findViewById(R.id.matchday_spinner);

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, matchdayList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectMatchday.setAdapter(dataAdapter);

        selectMatchday.setOnItemSelectedListener(this);

        // Retrieving all match days and rounds. Just the keys, not the values.
        matchdaysRef.addValueEventListener(new ValueEventListener() {
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
        });






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
        String item = parent.getItemAtPosition(position).toString();
        matchMumber.setText(item);

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }




}
