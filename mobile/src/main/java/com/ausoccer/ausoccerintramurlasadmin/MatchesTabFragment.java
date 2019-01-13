package com.ausoccer.ausoccerintramurlasadmin;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesTabFragment extends Fragment {

    FloatingActionButton addMatchButton;
    MatchesModel matchesModel;
    MatchesAdapter matchday1Adapter, matchday2Adapter, matchday3Adapter, matchday4Adapter, matchday5Adapter, matchday6Adapter;

    ArrayList<MatchesModel> matchday1ArrayList = new ArrayList<>();
    ArrayList<MatchesModel> matchday2ArrayList = new ArrayList<>();
    ArrayList<MatchesModel> matchday3ArrayList = new ArrayList<>();
    ArrayList<MatchesModel> matchday4ArrayList = new ArrayList<>();
    ArrayList<MatchesModel> matchday5ArrayList = new ArrayList<>();
    ArrayList<MatchesModel> matchday6ArrayList = new ArrayList<>();

    String matchday_1 = "Matchday1";
    String matchday_2 = "Matchday2";
    String matchday_3 = "Matchday3";
    String matchday_4 = "Matchday4";
    String matchday_5 = "Matchday5";
    String matchday_6 = "Matchday6";




    CustomListView matchday1list, matchday2list, matchday3list, matchday4list, matchday5list, matchday6list;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference matchday1 = database.getReference("Matches").child("Matchday1");
    DatabaseReference matchday2 = database.getReference("Matches").child("Matchday2");
    DatabaseReference matchday3 = database.getReference("Matches").child("Matchday3");
    DatabaseReference matchday4 = database.getReference("Matches").child("Matchday4");
    DatabaseReference matchday5 = database.getReference("Matches").child("Matchday5");
    DatabaseReference matchday6 = database.getReference("Matches").child("Matchday6");

    public MatchesTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_matches_tab, container, false);

        addMatchButton = v.findViewById(R.id.add_match_fab);
        matchday1list = v.findViewById(R.id.matchday1_list);
        matchday2list = v.findViewById(R.id.matchday2_list);
        matchday3list = v.findViewById(R.id.matchday3_list);
        matchday4list = v.findViewById(R.id.matchday4_list);
        matchday5list = v.findViewById(R.id.matchday5_list);
        matchday6list = v.findViewById(R.id.matchday6_list);

        matchesModel = new MatchesModel();
        matchday1Adapter = new MatchesAdapter(getActivity(), matchday1ArrayList);
        matchday2Adapter = new MatchesAdapter(getActivity(), matchday2ArrayList);
        matchday3Adapter = new MatchesAdapter(getActivity(), matchday3ArrayList);
        matchday4Adapter = new MatchesAdapter(getActivity(), matchday4ArrayList);
        matchday5Adapter = new MatchesAdapter(getActivity(), matchday5ArrayList);
        matchday6Adapter = new MatchesAdapter(getActivity(), matchday6ArrayList);

        matchday1list.setAdapter(matchday1Adapter);
        matchday2list.setAdapter(matchday2Adapter);
        matchday3list.setAdapter(matchday3Adapter);
        matchday4list.setAdapter(matchday4Adapter);
        matchday5list.setAdapter(matchday5Adapter);
        matchday6list.setAdapter(matchday6Adapter);

        matchday1.orderByChild("matchNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (matchday1list.getAdapter() == null) {
                    MatchesAdapter adapter2 = new MatchesAdapter(getActivity(), matchday1ArrayList);
                    matchday1list.setAdapter(adapter2);
                } else {
                    ((MatchesAdapter)matchday1list.getAdapter()).refill(matchday1ArrayList);
                }

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    matchesModel = ds.getValue(MatchesModel.class);
                    //String status = matchesModel.getMatchStatus();
                    matchday1ArrayList.add(matchesModel);

            }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        matchday1list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                matchesModel = new MatchesModel();
                matchesModel = matchday1ArrayList.get(i);

                Intent intent = new Intent(getActivity(), MatchControlActivity.class);

                String id = matchesModel.getMatchUid();

                intent.putExtra("id", id);
                intent.putExtra("matchday", matchday_1);
                startActivity(intent);

            }
        });

        matchday2.orderByChild("matchNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (matchday2list.getAdapter() == null) {
                    MatchesAdapter adapter = new MatchesAdapter(getActivity(), matchday2ArrayList);
                    matchday2list.setAdapter(adapter);
                } else {
                    ((MatchesAdapter)matchday2list.getAdapter()).refill(matchday2ArrayList);
                }

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    matchesModel = ds.getValue(MatchesModel.class);
                    //String status = matchesModel.getMatchStatus();
                    matchday2ArrayList.add(matchesModel);

                }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        matchday2list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                matchesModel = new MatchesModel();
                matchesModel = matchday2ArrayList.get(i);

                //Intent intent = new Intent(getActivity(), *Name of activity for editing and managing selected match*);

                String id = matchesModel.getMatchUid();

                //intent.putExtra("id", id);
                //intent.putExtra("matchday", matchday_2);
                //startActivity(intent);

            }
        });

        matchday3.orderByChild("matchNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (matchday3list.getAdapter() == null) {
                    MatchesAdapter adapter = new MatchesAdapter(getActivity(), matchday3ArrayList);
                    matchday3list.setAdapter(adapter);
                } else {
                    ((MatchesAdapter)matchday3list.getAdapter()).refill(matchday3ArrayList);
                }

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    matchesModel = ds.getValue(MatchesModel.class);
                    //String status = matchesModel.getMatchStatus();
                    matchday3ArrayList.add(matchesModel);

                }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        matchday3list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                matchesModel = new MatchesModel();
                matchesModel = matchday3ArrayList.get(i);

                //Intent intent = new Intent(getActivity(), *Name of activity for editing and managing selected match*);

                String id = matchesModel.getMatchUid();

                //intent.putExtra("id", id);
                //intent.putExtra("matchday", matchday_3);
                //startActivity(intent);

            }
        });

        matchday4.orderByChild("matchNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (matchday4list.getAdapter() == null) {
                    MatchesAdapter adapter = new MatchesAdapter(getActivity(), matchday4ArrayList);
                    matchday4list.setAdapter(adapter);
                } else {
                    ((MatchesAdapter)matchday4list.getAdapter()).refill(matchday4ArrayList);
                }

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    matchesModel = ds.getValue(MatchesModel.class);
                    //String status = matchesModel.getMatchStatus();
                    matchday4ArrayList.add(matchesModel);

                }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        matchday4list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                matchesModel = new MatchesModel();
                matchesModel = matchday4ArrayList.get(i);

                //Intent intent = new Intent(getActivity(), *Name of activity for editing and managing selected match*);

                String id = matchesModel.getMatchUid();

                //intent.putExtra("id", id);
                //intent.putExtra("matchday", matchday_4);
                //startActivity(intent);

            }
        });

        matchday5.orderByChild("matchNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (matchday5list.getAdapter() == null) {
                    MatchesAdapter adapter = new MatchesAdapter(getActivity(), matchday5ArrayList);
                    matchday5list.setAdapter(adapter);
                } else {
                    ((MatchesAdapter)matchday5list.getAdapter()).refill(matchday5ArrayList);
                }

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    matchesModel = ds.getValue(MatchesModel.class);
                    //String status = matchesModel.getMatchStatus();
                    matchday5ArrayList.add(matchesModel);

                }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        matchday5list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                matchesModel = new MatchesModel();
                matchesModel = matchday5ArrayList.get(i);

                //Intent intent = new Intent(getActivity(), *Name of activity for editing and managing selected match*);

                String id = matchesModel.getMatchUid();

                //intent.putExtra("id", id);
                //intent.putExtra("matchday", matchday_5);
                //startActivity(intent);

            }
        });

        matchday6.orderByChild("matchNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (matchday6list.getAdapter() == null) {
                    MatchesAdapter adapter = new MatchesAdapter(getActivity(), matchday6ArrayList);
                    matchday6list.setAdapter(adapter);
                } else {
                    ((MatchesAdapter)matchday6list.getAdapter()).refill(matchday6ArrayList);
                }

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    matchesModel = ds.getValue(MatchesModel.class);
                    //String status = matchesModel.getMatchStatus();
                    matchday6ArrayList.add(matchesModel);

                }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        matchday6list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                matchesModel = new MatchesModel();
                matchesModel = matchday6ArrayList.get(i);

                //Intent intent = new Intent(getActivity(), *Name of activity for editing and managing selected match*);

                String id = matchesModel.getMatchUid();

                //intent.putExtra("id", id);
                //intent.putExtra("matchday", matchday_6);
                //startActivity(intent);

            }
        });


        addMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddMatchActivity.class);
                startActivity(intent);
            }
        });


        return v;
    }

}
