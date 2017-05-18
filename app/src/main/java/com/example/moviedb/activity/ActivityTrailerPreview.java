package com.example.moviedb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.adapters.AdapterForTrailerRecyclerView;
import com.example.moviedb.model.movie.MovieDetails;
import com.example.moviedb.model.movie.Trailers;
import com.example.moviedb.retrofit.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityTrailerPreview extends AppCompatActivity {

    private List<Trailers.Youtube> list;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    AdapterForTrailerRecyclerView adapterForTrailerRecyclerView;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_youtube_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_trailer_image);
        list = new ArrayList<>();
        Intent intent = getIntent();
        int filmID = intent.getIntExtra("film_id", 1);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        Call<MovieDetails> call = ApiClient.getClient().getGenre(filmID, Const.API_KEY);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                list = response.body().getTrailers().getYoutube();
                adapterForTrailerRecyclerView = new AdapterForTrailerRecyclerView(getApplicationContext(), list);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                recyclerView.setAdapter(adapterForTrailerRecyclerView);
                adapterForTrailerRecyclerView.setOnClickAdapterForTrailerRecyclerView(new AdapterForTrailerRecyclerView.OnClickAdapterForTrailerRecyclerView() {
                    @Override
                    public void onClick(int position) {
                        Intent intentForStartTrailer = new Intent(getApplicationContext(), ActivityYouTube.class);
                        intentForStartTrailer.putExtra("source" ,list.get(position).getSource());
                        startActivity(intentForStartTrailer);
                    }
                });
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

            }
        });
    }
}
