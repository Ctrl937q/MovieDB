package com.example.moviedb.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import com.example.moviedb.fragments.tv.FragmentCastBiographyTVShow;
import com.example.moviedb.fragments.tv.FragmentInfoCastTVShow;


public class PagerAdapterCastDetailsTVShow extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Info", "Biography"};
    private Context context;


    public PagerAdapterCastDetailsTVShow(FragmentManager fm, Context context) {
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
                return new FragmentInfoCastTVShow();
            case 1:
                return new FragmentCastBiographyTVShow();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
