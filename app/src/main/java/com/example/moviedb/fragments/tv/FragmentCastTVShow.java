package com.example.moviedb.fragments.tv;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.activity.ActivityCastDetailsTVShow;
import com.example.moviedb.adapters.CastListViewTVShowAdapter;
import com.example.moviedb.fragments.movie.FragmentCast;
import com.example.moviedb.internet.TestInternetConnection;
import com.example.moviedb.model.tv.details.CastTV;
import com.example.moviedb.model.tv.details.TVDetailsResponseTV;
import com.example.moviedb.retrofit.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCastTVShow extends Fragment {

    List<CastTV> castsList;
    ListView castListView;
    CastListViewTVShowAdapter castListViewTVShowAdapter;
    private ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    Call<TVDetailsResponseTV> call;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_cast_tv_show, container, false);
        final int itemId = getActivity().getIntent().getIntExtra("id", 1);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar_cast_fragment_tv);
        castListView = (ListView) rootView.findViewById(R.id.list_view_for_cast_tv);
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_cast_fragment_tv);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!TestInternetConnection.checkConnection(getContext())) {
                    Toast.makeText(getContext(), "no internet connection", Toast.LENGTH_SHORT).show();
                }else {
                    Call<TVDetailsResponseTV> call = ApiClient.getClient().getTVDetails(itemId, Const.API_KEY);
                    call.enqueue(new Callback<TVDetailsResponseTV>() {
                        @Override
                        public void onResponse(Call<TVDetailsResponseTV> call, Response<TVDetailsResponseTV> response) {
                            castsList = response.body().getCreditsTV().getCastTV();
                            castListViewTVShowAdapter = new CastListViewTVShowAdapter(getActivity(), castsList);
                            castListView.setAdapter(castListViewTVShowAdapter);
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<TVDetailsResponseTV> call, Throwable t) {

                        }
                    });
                }
                castListView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.destroyDrawingCache();
            }
        });
        castsList = new ArrayList<>();

        castListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ActivityCastDetailsTVShow.class);
                Log.d("log", "click listener for cast 1");
                intent.putExtra("cast_id", castsList.get(position).getId());
                intent.putExtra("profile_path", castsList.get(position).getProfilePath());
                intent.putExtra("name", castsList.get(position).getName());
                startActivity(intent);
            }
        });

        Call<TVDetailsResponseTV> call = ApiClient.getClient().getTVDetails(itemId, Const.API_KEY);
        call.enqueue(new Callback<TVDetailsResponseTV>() {
            @Override
            public void onResponse(Call<TVDetailsResponseTV> call, Response<TVDetailsResponseTV> response) {
                castsList = response.body().getCreditsTV().getCastTV();
                castListViewTVShowAdapter = new CastListViewTVShowAdapter(getActivity(), castsList);
                castListView.setAdapter(castListViewTVShowAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<TVDetailsResponseTV> call, Throwable t) {

            }
        });
        castListView.setVisibility(View.VISIBLE);
        return rootView;
    }

    public static FragmentCast newInstance() {
        return new FragmentCast();
    }
}
