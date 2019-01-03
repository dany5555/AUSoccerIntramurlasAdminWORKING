package com.ausoccer.ausoccerintramurlasadmin;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MatchesTabFragment tab1 = new MatchesTabFragment();
                return tab1;
            case 1:
                TeamsTabFragment tab2 = new TeamsTabFragment();
                return tab2;
            case 2:
                HeadlinesTabFragment tab3 = new HeadlinesTabFragment();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}