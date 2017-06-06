package com.example.moviedb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.adapters.AdapterForListViewGenres;
import com.example.moviedb.internet.TestInternetConnection;
import com.example.moviedb.model.genre.Genres;
import com.example.moviedb.retrofit.ApiClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityGenres extends AppCompatActivity {

    Call<Genres> call;
    List<com.example.moviedb.model.genre.Genre> list;
    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);
        progressBar = (ProgressBar)findViewById(R.id.progress_activityGenres);
        listView = (ListView) findViewById(R.id.listView_genres);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout_genres);
        navigationView = (NavigationView) findViewById(R.id.shitstuff_main_genres);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_genres);
        navigationView.getMenu().getItem(2).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                if (menuItem.getItemId() == R.id.item_movies) {
                    Intent intent = new Intent(ActivityGenres.this, MainActivity.class);
                    intent.putExtra("startActivityFromMovies", 1);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.item_tv_shows) {
                    Intent intent = new Intent(ActivityGenres.this, MainActivity.class);
                    intent.putExtra("startActivityFromTVShow", "TV_Show");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ActivityGenres.this, ActivityGenreDetails.class);
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("title", list.get(position).getName());
                startActivity(intent);
            }
        });

        call = ApiClient.getClient().getGenres(Const.API_KEY);
        call.enqueue(new Callback<Genres>() {
            @Override
            public void onResponse(Call<Genres> call, Response<Genres> response) {
                list = response.body().getGenres();
                AdapterForListViewGenres adapterForListViewGenres = new AdapterForListViewGenres(getApplicationContext(), list);
                listView.setAdapter(adapterForListViewGenres);
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Genres> call, Throwable t) {

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!TestInternetConnection.checkConnection(ActivityGenres.this)) {
                    Toast.makeText(ActivityGenres.this, "no internet connection", Toast.LENGTH_SHORT).show();
                } else {
                    call = ApiClient.getClient().getGenres(Const.API_KEY);
                    call.enqueue(new Callback<Genres>() {
                        @Override
                        public void onResponse(Call<Genres> call, Response<Genres> response) {
                            list = response.body().getGenres();
                            AdapterForListViewGenres adapterForListViewGenres = new AdapterForListViewGenres(getApplicationContext(), list);
                            listView.setAdapter(adapterForListViewGenres);
                        }

                        @Override
                        public void onFailure(Call<Genres> call, Throwable t) {

                        }
                    });
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
