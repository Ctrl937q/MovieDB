package com.example.moviedb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.adapters.TopRatingAdapter;
import com.example.moviedb.model.Movie;
import com.example.moviedb.model.MovieResponse;
import com.example.moviedb.retrofit.ApiClient;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedFragment extends Fragment {

    List<Movie> list;
    RecyclerView rv;
    ProgressBar progressBar;
    TopRatingAdapter topRatingAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_top_rating, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view_third);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarThird);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        Call<MovieResponse> call = ApiClient.getClient().getTopRatedMovies(1, Const.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                list = response.body().getResults();
                topRatingAdapter = new TopRatingAdapter(getActivity(), list);
                rv.setLayoutManager(linearLayoutManager);
                rv.setAdapter(topRatingAdapter);
                rv.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        });

        return rootView;
    }
}
