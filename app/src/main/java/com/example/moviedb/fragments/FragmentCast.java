package com.example.moviedb.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.ResponseClass;
import com.example.moviedb.activity.ActivityCastDetails;
import com.example.moviedb.adapters.CastListViewAdapter;
import com.example.moviedb.model.Cast;
import com.example.moviedb.model.MovieDetails;
import com.example.moviedb.retrofit.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCast extends Fragment {

    List<Cast> castsList;
    ListView castListView;
    CastListViewAdapter castListViewAdapter;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cast_fragment, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar_cast_fragment);
        castListView = (ListView) rootView.findViewById(R.id.list_view_for_cast);
        castsList = new ArrayList<>();
        final int itemId = getActivity().getIntent().getIntExtra("id", 1);
        castListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ActivityCastDetails.class);
                intent.putExtra("cast_id", castsList.get(position).getId());
                intent.putExtra("profile_path", castsList.get(position).getProfilePath());
                intent.putExtra("name", castsList.get(position).getName());
                startActivity(intent);
            }
        });

        Call<MovieDetails> call = ApiClient.getClient().getGenre(itemId, Const.API_KEY);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                castsList = response.body().getCasts().getCast();
                castListViewAdapter = new CastListViewAdapter(getContext(), castsList);
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
