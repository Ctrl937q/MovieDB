package com.example.moviedb.fragments.tv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.adapters.GridViewCastDetailsAdapter;
import com.example.moviedb.converter.DateConverter;
import com.example.moviedb.model.movie.CastDetails;
import com.example.moviedb.model.movie.CombinedCredits;
import com.example.moviedb.retrofit.ApiClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentInfoCastTVShow extends Fragment{

    ImageView imageView;
    TextView textView_name;
    TextView textView_year;
    String name;
    String imagePath;
    String year;
    GridView gridView;
    List<CombinedCredits.Cast> listCredits;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    LinearLayout linearLayoutNameActor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_info_cast_tv, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.image_view_cast_info_tv);
        textView_name = (TextView) rootView.findViewById(R.id.text_view_cast_name_tv);
        textView_year = (TextView)rootView.findViewById(R.id.text_view_cast_year_tv);
        gridView = (GridView) rootView.findViewById(R.id.grid_view_for_cast_details_tv);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar_info_cast_fragment_tv);
        linearLayout = (LinearLayout)rootView.findViewById(R.id.linear_layout_info_cast_tv_grid_view);
        linearLayoutNameActor = (LinearLayout)rootView.findViewById(R.id.linear_layout_name_actor_tv_show);
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
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Glide
                                        .with(getActivity())
                                        .load(Const.IMAGE_POSTER_PATH_URL + imagePath)
                                        .override(270, 210)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .placeholder(R.drawable.placeholder_backdrop)
                                        .crossFade()
                                        .into(imageView);
                            }
                        });
                        t.run();
                    }else {
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Glide
                                        .with(getActivity())
                                        .load(Const.IMAGE_POSTER_PATH_URL + imagePath)
                                        .override(300, 240)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .placeholder(R.drawable.placeholder_backdrop)
                                        .crossFade()
                                        .into(imageView);
                            }
                        });
                        t.run();

                    }
                } catch (NullPointerException | IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.INVISIBLE);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.placeholder_backdrop));
                linearLayout.setVisibility(View.VISIBLE);
                linearLayoutNameActor.setBackgroundColor(getResources().getColor(R.color.background_for_top_info_movie));
            }

            @Override
            public void onFailure(Call<CastDetails> call, Throwable t) {

            }
        });
        return rootView;
    }
}
