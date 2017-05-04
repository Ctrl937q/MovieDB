package com.example.moviedb.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.moviedb.R;
import com.example.moviedb.adapters.TopRatingAdapter;
import com.example.moviedb.fragments.PopularFragment;
import com.example.moviedb.fragments.TopRatedFragment;
import com.example.moviedb.fragments.UpComingFragment;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;


public class MainActivity extends AppCompatActivity {

    private MaterialViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("MovieDB");
        viewPager = (MaterialViewPager)findViewById(R.id.materialViewPager);

        final Toolbar toolbar = viewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        // progressBar = (ProgressBar)findViewById(R.id.progressBar);

        viewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    case 0:
                        return UpComingFragment.newInstance();

                    case 1:
                        return PopularFragment.newInstance();

                    case 2:
                        return TopRatedFragment.newInstance();

                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "UpComing";
                    case 1:
                        return "Populary";
                    case 2:
                        return "Top Rated";
                }
                return "";
            }
        });

        final Drawable drawable1 = getResources().getDrawable(R.drawable.iron);
        final Drawable drawable2 = getResources().getDrawable(R.drawable.superman);
        final Drawable drawable3 = getResources().getDrawable(R.drawable.spider);

        viewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.gray, drawable1);
                    case 1:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.gray, drawable2);
                    case 2:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.gray, drawable3);
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        viewPager.getViewPager().setOffscreenPageLimit(viewPager.getViewPager().getAdapter().getCount());
        viewPager.getPagerTitleStrip().setViewPager(viewPager.getViewPager());

    }
}