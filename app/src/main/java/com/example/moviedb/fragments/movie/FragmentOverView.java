package com.example.moviedb.fragments.movie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.model.movie.MovieDetails;
import com.example.moviedb.retrofit.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOverView extends Fragment {

    TextView textViewOverView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.overview_fragment, container, false);
        textViewOverView = (TextView) rootView.findViewById(R.id.text_view_overView);
        final int itemId = getActivity().getIntent().getIntExtra("id", 1);
        Call<MovieDetails> call = ApiClient.getClient().getGenre(itemId, Const.API_KEY);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                String overView = response.body().getOverview();
                textViewOverView.setText(overView);
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

            }
        });


        return rootView;
    }
}
