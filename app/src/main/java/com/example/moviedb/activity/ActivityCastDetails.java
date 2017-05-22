package com.example.moviedb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviedb.R;
import com.example.moviedb.adapters.PagerAdapterCastDetails;
import com.example.moviedb.internet.TestInternetConnection;

public class ActivityCastDetails extends AppCompatActivity implements View.OnClickListener {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    PagerAdapterCastDetails pagerAdapterCastDetails;
    CoordinatorLayout coordinatorLayout;
    Button button_retry;
    TextView textView_retry;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_details);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_info_cast);
        button_retry = (Button) findViewById(R.id.button_retry_cast_details);
        textView_retry = (TextView) findViewById(R.id.textView_retry_internet_cast_details);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_cast_details);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
        viewPager = (ViewPager) findViewById(R.id.viewpager_cast_details);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_cast_details);
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
                    Intent intent = new Intent(ActivityCastDetails.this, MainActivity.class);
                    intent.putExtra("startActivityFromMovies", 1);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.item_tv_shows) {
                    Intent intent = new Intent(ActivityCastDetails.this, MainActivity.class);
                    intent.putExtra("startActivityFromTVShow", "TV_Show");
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
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int id = intent.getIntExtra("cast_id", 1);
        String profile_path = intent.getStringExtra("profile_path");
        pagerAdapterCastDetails = new PagerAdapterCastDetails(getSupportFragmentManager(), ActivityCastDetails.this);
        viewPager.setAdapter(pagerAdapterCastDetails);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_cast_details);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        toolbar.setTitle(name);
    }

    public void ifInternetOFF() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int id = intent.getIntExtra("cast_id", 1);
        String profile_path = intent.getStringExtra("profile_path");
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_cast_details);
        toolbar.setTitle(name);
        button_retry.setVisibility(View.VISIBLE);
        textView_retry.setVisibility(View.VISIBLE);
        Toast.makeText(this, "no internet connection", Toast.LENGTH_SHORT).show();
    }
}