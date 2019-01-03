package com.ausoccer.ausoccerintramurlasadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MatchesAdapter extends BaseAdapter {

    Context context;
    ArrayList<MatchesModel> matchesModelArrayList;
    MatchesModel matchesModel;

    public MatchesAdapter(Context context, ArrayList<MatchesModel> matchesModelArrayList) {
        this.context = context;
        this.matchesModelArrayList = matchesModelArrayList;
    }

    @Override
    public int getCount() {
        return matchesModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return matchesModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.matches_tab_list_model, viewGroup, false);
        }

        TextView matchNumber = view.findViewById(R.id.match_number_textView);
        TextView matchTeams = view.findViewById(R.id.match_teams_textView);

        matchesModel = (MatchesModel) this.getItem(i);

        matchNumber.setText(String.valueOf(matchesModel.getMatchNumber()));
        matchTeams.setText(matchesModel.getHomeTeamName() + " vs. " + matchesModel.getAwayTeamName());

        return view;
    }

    // This method fixes the issue of having the listview scroll all the way to the top when DB is updated.
    public void refill(ArrayList<MatchesModel> matchesModelArrayList) {
        matchesModelArrayList.clear();
        matchesModelArrayList.addAll(matchesModelArrayList);
        notifyDataSetChanged();

    }

}