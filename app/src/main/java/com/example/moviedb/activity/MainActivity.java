package com.example.moviedb.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviedb.R;
import com.example.moviedb.fragments.tv.AiringTodayFragmentTV;
import com.example.moviedb.fragments.movie.NowPlayingFragment;
import com.example.moviedb.fragments.tv.OnTheAirFragmentTV;
import com.example.moviedb.fragments.movie.PopularFragment;
import com.example.moviedb.fragments.tv.PopularFragmentTV;
import com.example.moviedb.fragments.movie.TopRatedFragment;
import com.example.moviedb.fragments.tv.TopRatedFragmentTV;
import com.example.moviedb.fragments.movie.UpComingFragment;
import com.example.moviedb.internet.TestInternetConnection;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialViewPager viewPager;
    Button button;
    TextView textViewRetry;
    NavigationView mNavigationView;
    DrawerLayout drawerLayout;
    NowPlayingFragment nowPlayingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nowPlayingFragment = new NowPlayingFragment();
        viewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        button = (Button) findViewById(R.id.button);
        textViewRetry = (TextView) findViewById(R.id.textView_retry_internet);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        button.setOnClickListener(this);
        Intent intent = getIntent();
        String keyFromActivity = intent.getStringExtra("startActivityFromTVShow");

        if (!TestInternetConnection.checkConnection(getApplicationContext())) {
            viewPager.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            textViewRetry.setVisibility(View.VISIBLE);
        }


            clickOnMovies();




        final Toolbar toolbar = viewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                if (menuItem.getItemId() == R.id.item_movies) {
                    clickOnMovies();
                } else if (menuItem.getItemId() == R.id.item_tv_shows) {
                    clickOnTv();
                }
                return false;
            }
        });
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);

        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();



        final Drawable drawable1 = getResources().getDrawable(R.drawable.iron);
        final Drawable drawable2 = getResources().getDrawable(R.drawable.superman);
        final Drawable drawable3 = getResources().getDrawable(R.drawable.spider);
        final Drawable drawable4 = getResources().getDrawable(R.drawable.starwars);

        viewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                if (page == 0) {
                    return HeaderDesign.fromColorResAndDrawable(
                            R.color.gray, drawable1);
                } else if (page == 1) {
                    return HeaderDesign.fromColorResAndDrawable(
                            R.color.gray, drawable2);
                } else if (page == 2) {
                    return HeaderDesign.fromColorResAndDrawable(
                            R.color.gray, drawable3);
                } else if (page == 3) {
                    return HeaderDesign.fromColorResAndDrawable(
                            R.color.gray, drawable4);
                }
                return null;
            }
        });

        if(keyFromActivity != null){
            if(keyFromActivity.equals("TV_Show")) {
                clickOnTv();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == button.getId()) {
            if (TestInternetConnection.checkConnection(getApplicationContext())) {
                viewPager.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
                textViewRetry.setVisibility(View.GONE);
            } else {
                Toast.makeText(getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void clickOnMovies(){
        setTitle("Movies");
        viewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return UpComingFragment.newInstance();
                } else if (position == 1) {
                    return NowPlayingFragment.newInstance();
                } else if (position == 2) {
                    return PopularFragment.newInstance();
                } else if (position == 3) {
                    return TopRatedFragment.newInstance();
                }
                return null;
            }
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "UpComing";
                    case 1:
                        return "Now Playing";
                    case 2:
                        return "Popular";
                    case 3:
                        return "Top Rated";
                }
                return "";
            }
        });
        viewPager.getViewPager().setOffscreenPageLimit(4);
        viewPager.getPagerTitleStrip().setViewPager(viewPager.getViewPager());

    }

    public void clickOnTv() {
        setTitle("TV shows");
        viewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return OnTheAirFragmentTV.newInstance();
                } else if (position == 1) {
                    return AiringTodayFragmentTV.newInstance();
                } else if (position == 2) {
                    return PopularFragmentTV.newInstance();
                } else if (position == 3) {
                    return TopRatedFragmentTV.newInstance();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "ON THE AIR";
                    case 1:
                        return "AIRING TODAY";
                    case 2:
                        return "POPULAR";
                    case 3:
                        return "TOP RATED";
                }
                return "";
            }
        });
        viewPager.getViewPager().setOffscreenPageLimit(4);
        viewPager.getPagerTitleStrip().setViewPager(viewPager.getViewPager());
    }
}