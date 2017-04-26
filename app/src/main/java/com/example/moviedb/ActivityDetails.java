package com.example.moviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;

import com.example.moviedb.adapters.PagerAdapterDetails;

public class ActivityDetails extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_details);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_details);

        pagerAdapter = new PagerAdapterDetails(getSupportFragmentManager(), ActivityDetails.this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_details);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        String title =  intent.getStringExtra("title");

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
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_details);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        toolbar.setTitle(title);
    }
}


