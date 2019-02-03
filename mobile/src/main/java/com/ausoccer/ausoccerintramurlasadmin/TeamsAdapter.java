package com.ausoccer.ausoccerintramurlasadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TeamsAdapter extends BaseAdapter {

    Context context;
    ArrayList<TeamsModel> teamsModelArrayList;
    TeamsModel teamsModel;

    public TeamsAdapter(Context context, ArrayList<TeamsModel> teamsModelArrayList) {
        this.context = context;
        this.teamsModelArrayList = teamsModelArrayList;
    }

    @Override
    public int getCount() {
        return teamsModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return teamsModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.teams_grid_model, viewGroup, false);
        }

        ImageView teamLogo = view.findViewById(R.id.team_logo);
        TextView teamName = view.findViewById(R.id.team_name);

        teamsModel = (TeamsModel) this.getItem(i);

        String teamLogoUrl = teamsModel.getTeamLogoUrl();

        teamName.setText(teamsModel.getTeamName());
        Glide.with(context).load(teamLogoUrl).into(teamLogo);

        return view;
}

    // This method fixes the issue of having the listview scroll all the way to the top when DB is updated.
    public void refill(ArrayList<TeamsModel> matchesModelArrayList) {
        matchesModelArrayList.clear();
        matchesModelArrayList.addAll(matchesModelArrayList);
        notifyDataSetChanged();

    }
}
