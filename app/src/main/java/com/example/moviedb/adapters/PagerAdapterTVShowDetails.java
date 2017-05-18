package com.example.moviedb.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.moviedb.fragments.tv.FragmentCastTVShow;
import com.example.moviedb.fragments.tv.FragmentInfoTVShow;
import com.example.moviedb.fragments.tv.FragmentOverViewTVShow;

public class PagerAdapterTVShowDetails extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Info", "Cast", "Overview"};
    private Context context;

    public PagerAdapterTVShowDetails(FragmentManager fm, Context context) {
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
                return new FragmentInfoTVShow();
            case 1:
                return new FragmentCastTVShow();
            case 2:
                return new FragmentOverViewTVShow();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}

