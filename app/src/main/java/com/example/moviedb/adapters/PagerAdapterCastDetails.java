package com.example.moviedb.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.moviedb.fragments.FragmentCastBiography;
import com.example.moviedb.fragments.FragmentInfoCast;


public class PagerAdapterCastDetails extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Info", "Biography"};
    private Context context;

    public PagerAdapterCastDetails(FragmentManager fm, Context context) {
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
                return new FragmentInfoCast();
            case 1:
                return new FragmentCastBiography();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
