package com.ausoccer.ausoccerintramurlasadmin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MatchDetailsPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public MatchDetailsPagerAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LineupsFragment tab1 = new LineupsFragment();
                return tab1;
            case 1:
                StatisticsFragment tab2 = new StatisticsFragment();
                return tab2;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
