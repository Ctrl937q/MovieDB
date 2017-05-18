package com.example.moviedb.fragments.movie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.adapters.TopRatingAdapter;
import com.example.moviedb.internet.TestInternetConnection;
import com.example.moviedb.model.movie.Movie;
import com.example.moviedb.model.movie.MovieResponse;
import com.example.moviedb.retrofit.ApiClient;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedFragment extends Fragment {

    List<Movie> list;
    RecyclerView rv;
    ProgressBar progressBar;
    TopRatingAdapter topRatingAdapter;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    Call<MovieResponse> call;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_top_rating, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_layout_top_rated_movies);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view_third);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarThird);
        linearLayoutManager = new LinearLayoutManager(getContext());
        resp();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!TestInternetConnection.checkConnection(getContext())) {
                    Toast.makeText(getContext(), "no internet connection", Toast.LENGTH_SHORT).show();
                } else {
                    call = ApiClient.getClient().getTopRatedMovies(1, Const.API_KEY);
                    call.enqueue(new Callback<MovieResponse>() {
                        @Override
                        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                            list = response.body().getResults();
                            topRatingAdapter = new TopRatingAdapter(getContext(), list);
                            rv.setLayoutManager(linearLayoutManager);
                            rv.setAdapter(topRatingAdapter);
                        }

                        @Override
                        public void onFailure(Call<MovieResponse> call, Throwable t) {

                        }
                    });
                }
                topRatingAdapter = new TopRatingAdapter(getContext(), list);
                rv.setAdapter(topRatingAdapter);
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.destroyDrawingCache();
            }
        });
        return rootView;
    }

    public void resp() {
        call = ApiClient.getClient().getTopRatedMovies(1, Const.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                list = response.body().getResults();
                topRatingAdapter = new TopRatingAdapter(getContext(), list);
                rv.setLayoutManager(linearLayoutManager);
                rv.addItemDecoration(new MaterialViewPagerHeaderDecorator());
                rv.setAdapter(topRatingAdapter);
                rv.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        });
    }

    public static TopRatedFragment newInstance() {
        return new TopRatedFragment();
    }
}
