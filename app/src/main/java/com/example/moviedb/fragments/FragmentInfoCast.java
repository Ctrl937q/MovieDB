package com.example.moviedb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.adapters.GridViewCastDetailsAdapter;
import com.example.moviedb.adapters.GridViewMovieDetailsAdapter;
import com.example.moviedb.converter.DateConverter;
import com.example.moviedb.model.CastDetails;
import com.example.moviedb.model.CombinedCredits;
import com.example.moviedb.retrofit.ApiClient;
import com.squareup.picasso.Picasso;

import android.support.annotation.Nullable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentInfoCast extends Fragment {

    ImageView imageView;
    TextView textView_name;
    TextView textView_year;
    String name;
    String imagePath;
    String year;
    GridView gridView;
    List<CombinedCredits.Cast> listCredits;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_info_cast, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.image_view_cast_info);
        textView_name = (TextView) rootView.findViewById(R.id.text_view_cast_name);
        textView_year = (TextView)rootView.findViewById(R.id.text_view_cast_year);
        gridView = (GridView) rootView.findViewById(R.id.grid_view_for_cast_details);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar_info_cast_fragment);
        final int castId = getActivity().getIntent().getIntExtra("cast_id", 1);
        Call<CastDetails> call = ApiClient.getClient().getCastInfo(castId, Const.API_KEY);
        call.enqueue(new Callback<CastDetails>() {
            @Override
            public void onResponse(Call<CastDetails> call, Response<CastDetails> response) {
                try {
                    listCredits = new ArrayList<>();
                    listCredits = response.body().getCombinedCredits().getCast();
                    gridView.setAdapter(new GridViewCastDetailsAdapter(getContext(), listCredits));
                    name = response.body().getName();
                    year = DateConverter.formateDateFromstring("yyyy-MM-dd", "dd.MM.yyy", response.body().getBirthday());
                    imagePath = response.body().getProfilePath();
                    textView_name.setText(name);
                    textView_year.setText(year);
                    if (imagePath == null || imagePath.equals("")) {
                        Picasso.with(getActivity()).load(Const.IMAGE_POSTER_PATH_URL + imagePath)
                                .placeholder(R.drawable.placeholder_item_recycler_view)
                                .resize(700, 500)
                                .centerCrop()
                                .into(imageView);
                    } else {
                        Picasso.with(getActivity()).load(Const.IMAGE_POSTER_PATH_URL + imagePath)
                                .placeholder(R.drawable.placeholder_backdrop)
                                .into(imageView);
                    }
                } catch (NullPointerException | IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<CastDetails> call, Throwable t) {

            }
        });
        return rootView;
    }
}
