package com.ausoccer.ausoccerintramurlasadmin;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class TeamsTabFragment extends Fragment {

    GridView gridView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference teams = database.getReference("Teams");
    ArrayList<TeamsModel> teamsModelArrayList = new ArrayList<>();
    TeamsModel teamsModel;
    TeamsAdapter teamsAdapter;
    public TeamsTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_teams_tab, container, false);

        gridView = v.findViewById(R.id.simpleGridView);

        gridView.setAdapter(teamsAdapter);

        teams.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (gridView.getAdapter() == null) {
                    TeamsAdapter teamsAdapter = new TeamsAdapter(getActivity(), teamsModelArrayList);
                    gridView.setAdapter(teamsAdapter);
                } else {
                    ((TeamsAdapter)gridView.getAdapter()).refill(teamsModelArrayList);
                }
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    teamsModel = ds.getValue(TeamsModel.class);
                    teamsModelArrayList.add(teamsModel);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                teamsModel = new TeamsModel();
                teamsModel = teamsModelArrayList.get(i);

                Intent intent = new Intent(getActivity(), EditTeamsActivity.class);

                String teamUid = teamsModel.getTeamUid();
                intent.putExtra("teamUid", teamUid);
                startActivity(intent);
            }
        });



        return v;
    }

}
