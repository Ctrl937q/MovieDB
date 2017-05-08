package com.example.moviedb.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviedb.R;
import com.example.moviedb.fragments.NowPlayingFragment;
import com.example.moviedb.fragments.PopularFragment;
import com.example.moviedb.fragments.TopRatedFragment;
import com.example.moviedb.fragments.UpComingFragment;
import com.example.moviedb.internet.TestInternetConnection;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialViewPager viewPager;
    Button button;
    TextView textViewRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        button = (Button) findViewById(R.id.button);
        textViewRetry = (TextView) findViewById(R.id.textView_retry_internet);
        button.setOnClickListener(this);
        if (!TestInternetConnection.checkConnection(getApplicationContext())) {
            viewPager.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            textViewRetry.setVisibility(View.VISIBLE);
        }

        setTitle("MovieDB");

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
                        return NowPlayingFragment.newInstance();
                    case 2:
                        return PopularFragment.newInstance();
                    case 3:
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
                switch (position % 4) {
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

        final Drawable drawable1 = getResources().getDrawable(R.drawable.iron);
        final Drawable drawable2 = getResources().getDrawable(R.drawable.superman);
        final Drawable drawable3 = getResources().getDrawable(R.drawable.spider);
        final Drawable drawable4 = getResources().getDrawable(R.drawable.starwars);

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
                    case 3:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.gray, drawable4);
                }
                return null;
            }
        });
        viewPager.getViewPager().setOffscreenPageLimit(4);
        viewPager.getPagerTitleStrip().setViewPager(viewPager.getViewPager());
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