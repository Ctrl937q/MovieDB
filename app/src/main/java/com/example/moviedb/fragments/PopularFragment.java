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
import com.example.moviedb.adapters.PopularAdapter;
import com.example.moviedb.model.Movie;
import com.example.moviedb.model.MovieResponse;
import com.example.moviedb.retrofit.ApiClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularFragment extends Fragment{

    List<Movie> list;
    int pageNumber = 2;

    RecyclerView rv;
    ProgressBar progressBar;
    PopularAdapter popularAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_popular, container, false);
        pageNumber = 2;
        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view_second);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarSecond);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        Call<MovieResponse> call = ApiClient.getClient().getPopularyMovies(1, Const.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                list = response.body().getResults();
                popularAdapter = new PopularAdapter(getActivity(), list);
                rv.setLayoutManager(linearLayoutManager);
                rv.setAdapter(popularAdapter);
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
