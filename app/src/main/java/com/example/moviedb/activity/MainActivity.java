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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.fragments.search.FragmentSearch;
import com.example.moviedb.fragments.tv.AiringTodayFragmentTV;
import com.example.moviedb.fragments.movie.NowPlayingFragment;
import com.example.moviedb.fragments.tv.OnTheAirFragmentTV;
import com.example.moviedb.fragments.movie.PopularFragment;
import com.example.moviedb.fragments.tv.PopularFragmentTV;
import com.example.moviedb.fragments.movie.TopRatedFragment;
import com.example.moviedb.fragments.tv.TopRatedFragmentTV;
import com.example.moviedb.fragments.movie.UpComingFragment;
import com.example.moviedb.internet.TestInternetConnection;
import com.example.moviedb.model.movie.MovieResponse;
import com.example.moviedb.model.search.Result;
import com.example.moviedb.model.search.SearchResponse;
import com.example.moviedb.retrofit.ApiClient;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialViewPager viewPager;
    Button button;
    TextView textViewRetry;
    NavigationView mNavigationView;
    DrawerLayout drawerLayout;
    NowPlayingFragment nowPlayingFragment;
    Drawable drawable1;
    Drawable drawable2;
    Drawable drawable3;
    Drawable drawable4;
    Drawable drawable5;
    Drawable drawable6;
    Drawable drawable7;
    Drawable drawable8;
    Call<SearchResponse> call;
    List<Result> list;
    int totalRes;

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
                } else if (menuItem.getItemId() == R.id.item_genres) {
                    clickOnGenres();
                }
                return false;
            }
        });
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);

        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        if (keyFromActivity != null) {
            if (keyFromActivity.equals("TV_Show")) {
                clickOnTv();
            } else if (keyFromActivity.equals("Movies")) {
                clickOnMovies();
            } else if (keyFromActivity.equals("Genres")) {
                clickOnGenres();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TestInternetConnection.checkConnection(getApplicationContext())) {
                    viewPager.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                    textViewRetry.setVisibility(View.VISIBLE);
                } else {
                    call = ApiClient.getClient().getSearch(1, query, Const.API_KEY);
                    call.enqueue(new Callback<SearchResponse>() {
                        @Override
                        public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                            totalRes = response.body().getTotalResults();
                        }

                        @Override
                        public void onFailure(Call<SearchResponse> call, Throwable t) {

                        }
                    });
                        Intent intent = new Intent(MainActivity.this, ActivitySearch.class);
                        intent.putExtra("text", query);
                        startActivity(intent);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.getQuery();
        return super.onCreateOptionsMenu(menu);
    }

    public void clickOnMovies() {
        mNavigationView.getMenu().getItem(1).setChecked(false);
        mNavigationView.getMenu().getItem(2).setChecked(false);
        mNavigationView.getMenu().getItem(0).setChecked(true);
        setTitle("Movies");
        drawable1 = getResources().getDrawable(R.drawable.iron);
        drawable2 = getResources().getDrawable(R.drawable.superman);
        drawable3 = getResources().getDrawable(R.drawable.spider);
        drawable4 = getResources().getDrawable(R.drawable.starwars);
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
        viewPager.notifyHeaderChanged();
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
        viewPager.getViewPager().setOffscreenPageLimit(0);
        viewPager.getPagerTitleStrip().setViewPager(viewPager.getViewPager());
    }

    public void clickOnTv() {
        mNavigationView.getMenu().getItem(1).setChecked(true);
        mNavigationView.getMenu().getItem(0).setChecked(false);
        mNavigationView.getMenu().getItem(2).setChecked(false);
        drawable5 = getResources().getDrawable(R.drawable.flash);
        drawable6 = getResources().getDrawable(R.drawable.game);
        drawable7 = getResources().getDrawable(R.drawable.spartakus);
        drawable8 = getResources().getDrawable(R.drawable.sherlok);
        viewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                if (page == 0) {
                    return HeaderDesign.fromColorResAndDrawable(
                            R.color.gray, drawable5);
                } else if (page == 1) {
                    return HeaderDesign.fromColorResAndDrawable(
                            R.color.gray, drawable6);
                } else if (page == 2) {
                    return HeaderDesign.fromColorResAndDrawable(
                            R.color.gray, drawable7);
                } else if (page == 3) {
                    return HeaderDesign.fromColorResAndDrawable(
                            R.color.gray, drawable8);
                }
                return null;
            }
        });
        viewPager.notifyHeaderChanged();

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
        viewPager.getViewPager().setOffscreenPageLimit(0);
        viewPager.getPagerTitleStrip().setViewPager(viewPager.getViewPager());
    }

    public void clickOnGenres() {
        setTitle("Genres");
        Intent intent = new Intent(MainActivity.this, ActivityGenres.class);
        startActivity(intent);
    }
}