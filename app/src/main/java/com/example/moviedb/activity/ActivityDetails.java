package com.example.moviedb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviedb.R;
import com.example.moviedb.adapters.PagerAdapterDetails;
import com.example.moviedb.fragments.movie.FragmentInfo;
import com.example.moviedb.fragments.movie.NowPlayingFragment;
import com.example.moviedb.fragments.movie.PopularFragment;
import com.example.moviedb.fragments.movie.TopRatedFragment;
import com.example.moviedb.fragments.movie.UpComingFragment;
import com.example.moviedb.fragments.tv.AiringTodayFragmentTV;
import com.example.moviedb.fragments.tv.OnTheAirFragmentTV;
import com.example.moviedb.fragments.tv.PopularFragmentTV;
import com.example.moviedb.fragments.tv.TopRatedFragmentTV;
import com.example.moviedb.internet.TestInternetConnection;
import com.github.florent37.materialviewpager.MaterialViewPager;

public class ActivityDetails extends AppCompatActivity implements View.OnClickListener {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    PagerAdapter pagerAdapter;
    CoordinatorLayout coordinatorLayout;
    Button button_retry;
    TextView textView_retry;
    private MaterialViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_info_fragment);
        button_retry = (Button) findViewById(R.id.button_retry_activity_details);
        textView_retry = (TextView) findViewById(R.id.textView_retry_internet_activity_details);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
        viewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        button_retry.setOnClickListener(this);
        if (TestInternetConnection.checkConnection(this)) {
            ifInternetOn();
        } else {
            ifInternetOFF();
        }
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                if (menuItem.getItemId() == R.id.item_movies) {
                    Intent intent = new Intent(ActivityDetails.this, MainActivity.class);
                    intent.putExtra("startActivityFromTVShow", "Movies");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.item_tv_shows) {
                    Intent intent = new Intent(ActivityDetails.this, MainActivity.class);
                    intent.putExtra("startActivityFromTVShow", "TV_Show");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if(menuItem.getItemId() == R.id.item_genres){
                    Intent intent = new Intent(ActivityDetails.this, MainActivity.class);
                    intent.putExtra("startActivityFromTVShow", "Genres");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == button_retry.getId()) {
            if (TestInternetConnection.checkConnection(this)) {
                ifInternetOn();
            } else {
                ifInternetOFF();
            }
        }
    }

    public void ifInternetOn() {
        button_retry.setVisibility(View.GONE);
        textView_retry.setVisibility(View.GONE);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_details);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_details);
        pagerAdapter = new PagerAdapterDetails(getSupportFragmentManager(), ActivityDetails.this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_details);
        tabLayout.setupWithViewPager(viewPager);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_details);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        toolbar.setTitle(title);
        FragmentInfo.newInstance();
    }

    public void ifInternetOFF() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_details);
        toolbar.setTitle(title);
        button_retry.setVisibility(View.VISIBLE);
        textView_retry.setVisibility(View.VISIBLE);
        Toast.makeText(this, "no internet connection", Toast.LENGTH_SHORT).show();
    }
}