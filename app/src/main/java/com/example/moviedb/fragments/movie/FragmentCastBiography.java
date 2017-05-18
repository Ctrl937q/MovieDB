package com.example.moviedb.fragments.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.model.movie.CastDetails;
import com.example.moviedb.retrofit.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCastBiography extends Fragment{

    TextView textViewBiography;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_cast_biography_tv, container, false);
        textViewBiography = (TextView)rootView.findViewById(R.id.text_view_biography_tv);
        final int castId = getActivity().getIntent().getIntExtra("cast_id", 1);
        Call<CastDetails> call = ApiClient.getClient().getCastInfo(castId, Const.API_KEY);
        call.enqueue(new Callback<CastDetails>() {
            @Override
            public void onResponse(Call<CastDetails> call, Response<CastDetails> response) {
                if(response.body().getBiography() == null){
                    textViewBiography.setText("No biography for this actor");
                    textViewBiography.setGravity(View.TEXT_ALIGNMENT_CENTER);
                }else {
                    textViewBiography.setText(response.body().getBiography());
                }
            }

            @Override
            public void onFailure(Call<CastDetails> call, Throwable t) {

            }
        });
        return rootView;
    }
}
