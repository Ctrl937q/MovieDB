package com.example.moviedb.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.adapters.AdapterForSearch;
import com.example.moviedb.fragments.search.FragmentSearch;
import com.example.moviedb.internet.TestInternetConnection;
import com.example.moviedb.model.search.Result;
import com.example.moviedb.model.search.SearchResponse;
import com.example.moviedb.retrofit.ApiClient;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySearch extends AppCompatActivity implements View.OnClickListener{

    private MaterialViewPager viewPager;
    Button button;
    TextView textViewRetry;
    NavigationView mNavigationView;
    DrawerLayout drawerLayout;
    Drawable drawable1;
    Call<SearchResponse> call;
    FragmentSearch fragmentSearch;
    AdapterForSearch adapterForSearch;
    LinearLayoutManager linearLayoutManager;
    List<Result> list;
    LayoutInflater layoutInflater;
    RecyclerView rv;
    String text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        viewPager = (MaterialViewPager) findViewById(R.id.materialViewPager_search);
        button = (Button) findViewById(R.id.button_search);
        textViewRetry = (TextView) findViewById(R.id.textView_retry_internet_search);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff_main_search);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_search);
        fragmentSearch = new FragmentSearch();
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        button.setOnClickListener(this);
        Intent intent = getIntent();
        text = intent.getStringExtra("text");
        final Toolbar toolbar = viewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        setTitle("Search");

        drawable1 = getResources().getDrawable(R.drawable.searchpicture);
        viewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                if (page == 0) {
                    return HeaderDesign.fromColorResAndDrawable(
                            R.color.gray, drawable1);
                }
                return null;
            }
        });
        viewPager.notifyHeaderChanged();
        viewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return FragmentSearch.newInstance();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "";
                }
                return "";
            }
        });
        viewPager.getViewPager().setOffscreenPageLimit(0);
        viewPager.getPagerTitleStrip().setViewPager(viewPager.getViewPager());

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                if (menuItem.getItemId() == R.id.item_movies) {
                    Intent intent = new Intent(ActivitySearch.this, MainActivity.class);
                    intent.putExtra("startActivityFromTVShow", "Movies");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.item_tv_shows) {
                    Intent intent = new Intent(ActivitySearch.this, MainActivity.class);
                    intent.putExtra("startActivityFromTVShow", "TV_Show");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if(menuItem.getItemId() == R.id.item_genres){
                    Intent intent = new Intent(ActivitySearch.this, MainActivity.class);
                    intent.putExtra("startActivityFromTVShow", "Genres");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                return false;
            }
        });

        if (!TestInternetConnection.checkConnection(getApplicationContext())) {
            viewPager.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            textViewRetry.setVisibility(View.VISIBLE);
        }

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
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
}
