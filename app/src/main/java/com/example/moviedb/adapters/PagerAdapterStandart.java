package com.example.moviedb.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.moviedb.fragments.movie.PopularFragment;
import com.example.moviedb.fragments.movie.TopRatedFragment;
import com.example.moviedb.fragments.movie.UpComingFragment;

public class PagerAdapterStandart extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"UpComing", "Popular", "Top Rating"};
    private Context context;

    public PagerAdapterStandart(FragmentManager fm, Context context) {
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
                return new UpComingFragment();
            case 1:
                return new PopularFragment();
            case 2:
                return new TopRatedFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
