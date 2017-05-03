package com.example.moviedb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import com.example.moviedb.R;
import com.example.moviedb.adapters.PagerAdapterCastDetails;

public class ActivityCastDetails extends AppCompatActivity{

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    PagerAdapterCastDetails pagerAdapterCastDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_details);
        Intent intent = getIntent();
        String name =  intent.getStringExtra("name");
        int id = intent.getIntExtra("cast_id", 1);
        Log.d("id", " " + id);
        String profile_path = intent.getStringExtra("profile_path");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_cast_details);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_cast_details);

        pagerAdapterCastDetails = new PagerAdapterCastDetails(getSupportFragmentManager(), ActivityCastDetails.this);
        viewPager.setAdapter(pagerAdapterCastDetails);
        viewPager.setOffscreenPageLimit(2);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_cast_details);
        tabLayout.setupWithViewPager(viewPager);

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
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_cast_details);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        toolbar.setTitle(name);
    }
}