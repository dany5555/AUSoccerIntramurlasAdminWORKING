package com.ausoccer.ausoccerintramurlasadmin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditTeamsActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference teamRef = database.getReference("Teams");

    TeamsAdapter teamsAdapter;
    TeamsModel teamsModel;

    EditText teamName, teamLogoUrl;
    ImageView teamLogo;
    Button submitButton;

    String teamUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teams);

        teamUid = getIntent().getStringExtra("teamUid");

        teamName = findViewById(R.id.team_name);
        teamLogoUrl = findViewById(R.id.team_logo_url);
        teamLogo = findViewById(R.id.team_logo);
        submitButton = findViewById(R.id.submitButton);





        teamRef.child(teamUid).child("teamName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teamName.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        teamRef.child(teamUid).child("teamLogoUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teamLogoUrl.setText(dataSnapshot.getValue().toString());
                String mTeamLogoUrl = teamLogoUrl.getText().toString().trim();
                Glide.with(getApplicationContext()).load(mTeamLogoUrl).into(teamLogo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mTeamName = teamName.getText().toString().trim();
                String mTeamLogoUrl = teamLogoUrl.getText().toString().trim();

                if (TextUtils.isEmpty(mTeamName)) {
                    Toast.makeText(getApplicationContext(), "Please enter a team name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mTeamLogoUrl)) {
                    Toast.makeText(getApplicationContext(), "Please enter a team logo url", Toast.LENGTH_SHORT).show();
                    return;
                }

                teamsModel = new TeamsModel();

                teamsModel.setTeamName(mTeamName);
                teamsModel.setTeamUid(mTeamName);
                teamsModel.setTeamLogoUrl(mTeamLogoUrl);

                try {
                    teamRef.child(teamUid).removeValue();
                    teamRef.child(teamsModel.getTeamUid()).setValue(teamsModel);
                } catch (DatabaseException ex) {
                    ex.printStackTrace();
                }


            }
        });


    }
}
