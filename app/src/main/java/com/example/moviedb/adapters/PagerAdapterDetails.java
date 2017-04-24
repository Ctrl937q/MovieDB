package com.example.moviedb.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.moviedb.fragments.FragmentCast;
import com.example.moviedb.fragments.FragmentInfo;
import com.example.moviedb.fragments.FragmentOverView;
import com.example.moviedb.fragments.PopularFragment;
import com.example.moviedb.fragments.TopRatedFragment;
import com.example.moviedb.fragments.UpComingFragment;

public class PagerAdapterDetails extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Info", "Cast", "Overview"};
    private Context context;

    public PagerAdapterDetails(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentInfo();
            case 1:
                return new FragmentCast();
            case 2:
                return new FragmentOverView();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
