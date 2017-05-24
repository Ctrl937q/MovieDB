package com.example.moviedb.fragments.genre;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.adapters.AdapterForGenreDetails;
import com.example.moviedb.model.genre.GenreDetails;
import com.example.moviedb.model.genre.Result;
import com.example.moviedb.retrofit.ApiClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.moviedb.internet.TestInternetConnection;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

public class FragmentGenreDetails extends Fragment{

    List<Result> list;
    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    Call<GenreDetails> call;
    int id;
    AdapterForGenreDetails adapterForGenreDetails;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_genre_details, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_genre_details);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar_genre_details);
        linearLayoutManager = new LinearLayoutManager(getContext());
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_layout_genre_details);
        id = getActivity().getIntent().getIntExtra("id", 1);
        call = ApiClient.getClient().getGenreDetails(id, 1, Const.API_KEY);
        call.enqueue(new Callback<GenreDetails>() {
            @Override
            public void onResponse(Call<GenreDetails> call, Response<GenreDetails> response) {
                list = response.body().getResults();
                adapterForGenreDetails = new AdapterForGenreDetails(getActivity(), list, id);
                rv.setLayoutManager(linearLayoutManager);
                rv.addItemDecoration(new MaterialViewPagerHeaderDecorator());
                rv.setAdapter(adapterForGenreDetails);
                rv.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<GenreDetails> call, Throwable t) {

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!TestInternetConnection.checkConnection(getContext())) {
                    Toast.makeText(getContext(), "no internet connection", Toast.LENGTH_SHORT).show();
                }else {
                    call = ApiClient.getClient().getGenreDetails(id, 1, Const.API_KEY);
                    call.enqueue(new Callback<GenreDetails>() {
                        @Override
                        public void onResponse(Call<GenreDetails> call, Response<GenreDetails> response) {
                            list = response.body().getResults();
                            adapterForGenreDetails = new AdapterForGenreDetails(getActivity(), list, id);
                            rv.setLayoutManager(linearLayoutManager);
                            rv.setAdapter(adapterForGenreDetails);
                            rv.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFailure(Call<GenreDetails> call, Throwable t) {

                        }
                    });

                }

                adapterForGenreDetails = new AdapterForGenreDetails(getContext(), list, id);
                rv.setAdapter(adapterForGenreDetails);
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.destroyDrawingCache();
            }
        });
        return rootView;
    }

    public static FragmentGenreDetails newInstance() {
        return new FragmentGenreDetails();
    }
}
