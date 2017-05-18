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
import com.example.moviedb.adapters.NowPlayingAdapter;
import com.example.moviedb.internet.TestInternetConnection;
import com.example.moviedb.model.movie.Movie;
import com.example.moviedb.model.movie.MovieResponse;
import com.example.moviedb.retrofit.ApiClient;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlayingFragment extends Fragment {

    List<Movie> list;
    RecyclerView rv;
    ProgressBar progressBar;
    NowPlayingAdapter nowPlayingAdapter;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    Call<MovieResponse> call;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_now_playing, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_layout_now_playing_movies);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view_fourth);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarFourth);
        linearLayoutManager = new LinearLayoutManager(getContext());
        resp();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!TestInternetConnection.checkConnection(getContext())) {
                    Toast.makeText(getContext(), "no internet connection", Toast.LENGTH_SHORT).show();
                }else {
                    call = ApiClient.getClient().getNowPlayingMovies(1, Const.API_KEY);
                    call.enqueue(new Callback<MovieResponse>() {
                        @Override
                        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                            list = response.body().getResults();
                            nowPlayingAdapter = new NowPlayingAdapter(getContext(), list);
                            rv.setLayoutManager(linearLayoutManager);
                            rv.setAdapter(nowPlayingAdapter);
                        }

                        @Override
                        public void onFailure(Call<MovieResponse> call, Throwable t) {

                        }
                    });
                }
                nowPlayingAdapter = new NowPlayingAdapter(getContext(), list);
                rv.setAdapter(nowPlayingAdapter);
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.destroyDrawingCache();
            }
        });
        return rootView;
    }


    public void resp(){
        call = ApiClient.getClient().getNowPlayingMovies(1, Const.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                list = response.body().getResults();
                nowPlayingAdapter = new NowPlayingAdapter(getContext(), list);
                rv.setLayoutManager(linearLayoutManager);
                rv.addItemDecoration(new MaterialViewPagerHeaderDecorator());
                rv.setAdapter(nowPlayingAdapter);
                rv.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        });
    }

    public static NowPlayingFragment newInstance() {
        return new NowPlayingFragment();
    }
}
