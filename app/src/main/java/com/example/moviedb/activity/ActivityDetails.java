package com.example.moviedb.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviedb.R;
import com.example.moviedb.adapters.PagerAdapterDetails;
import com.example.moviedb.fragments.FragmentInfo;
import com.example.moviedb.internet.TestInternetConnection;

public class ActivityDetails extends AppCompatActivity implements View.OnClickListener {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    PagerAdapter pagerAdapter;
    CoordinatorLayout coordinatorLayout;
    Button button_retry;
    TextView textView_retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_info_fragment);
        button_retry = (Button) findViewById(R.id.button_retry_activity_details);
        textView_retry = (TextView) findViewById(R.id.textView_retry_internet_activity_details);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
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

                // if (menuItem.getItemId() == R.id.nav_item_sent) {
                //     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

                // }

                //if (menuItem.getItemId() == R.id.nav_item_inbox) {
                //    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();

                //}

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


