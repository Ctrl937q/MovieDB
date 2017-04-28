package com.example.moviedb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.ResponseClass;
import com.example.moviedb.adapters.CastListViewAdapter;
import com.example.moviedb.adapters.GridViewAdapter;
import com.example.moviedb.adapters.PopularAdapter;
import com.example.moviedb.converter.DateConverter;
import com.example.moviedb.model.Cast;
import com.example.moviedb.model.Casts;
import com.example.moviedb.model.Genre;
import com.example.moviedb.model.Movie;
import com.example.moviedb.model.MovieDetails;
import com.example.moviedb.model.MovieResponse;
import com.example.moviedb.retrofit.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCast extends Fragment {

    List<Cast> castsList;
    ListView castListView;
    FragmentCast fragmentCast;
    SwipeRefreshLayout swipeRefreshLayout;
    CastListViewAdapter castListViewAdapter;
    ResponseClass responseClass;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cast_fragment, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar_cast_fragment);
        responseClass = new ResponseClass();
        castListView = (ListView) rootView.findViewById(R.id.list_view_for_cast);
        castsList = new ArrayList<>();
        final int itemId = getActivity().getIntent().getIntExtra("id", 1);

        Call<MovieDetails> call = ApiClient.getClient().getGenre(itemId, Const.API_KEY);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                castsList = response.body().getCasts().getCast();
                castListViewAdapter = new CastListViewAdapter(getActivity(), castsList);
                castListView.setAdapter(castListViewAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

            }
        });
        castListView.setVisibility(View.VISIBLE);
        return rootView;
    }
}
