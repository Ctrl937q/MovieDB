package com.example.moviedb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.adapters.RecyclerViewAdapter;
import com.example.moviedb.model.Movie;
import com.example.moviedb.model.MovieResponse;
import com.example.moviedb.retrofit.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpComingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab, container, false);
        final RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view_first);
        rv.setVisibility(View.GONE);
        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        Call<MovieResponse> call = ApiClient.getClient().getUpcomingMovies(Const.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> list = response.body().getResults();
                rv.setAdapter(new RecyclerViewAdapter(getActivity(), list));
                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
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
