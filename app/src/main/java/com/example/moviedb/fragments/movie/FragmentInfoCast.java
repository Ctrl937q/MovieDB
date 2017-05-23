package com.example.moviedb.fragments.movie;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.adapters.GridViewCastDetailsAdapter;
import com.example.moviedb.converter.DateConverter;
import com.example.moviedb.model.movie.CastDetails;
import com.example.moviedb.model.movie.CombinedCredits;
import com.example.moviedb.retrofit.ApiClient;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.squareup.picasso.Picasso;

import android.support.annotation.Nullable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
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
    TextView textView_knownFor;
    private final int CacheSize = 52428800;
    private final int MinFreeSpace = 2048;
    LinearLayout linearLayout;
    CoordinatorLayout coordinatorLayout;
    LinearLayout linearLayoutInfoCast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_info_cast, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.image_view_cast_info);
        textView_name = (TextView) rootView.findViewById(R.id.text_view_cast_name);
        textView_year = (TextView) rootView.findViewById(R.id.text_view_cast_year);
        gridView = (GridView) rootView.findViewById(R.id.grid_view_for_cast_details);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar_info_cast_fragment);
        textView_knownFor = (TextView) rootView.findViewById(R.id.textView_RelatedMovies);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.linear_layout_name_actor);
        linearLayoutInfoCast = (LinearLayout) rootView.findViewById(R.id.linear_layout_info_cast);
        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.coordinator_layout_info_cast);
        final int castId = getActivity().getIntent().getIntExtra("cast_id", 1);
        Call<CastDetails> call = ApiClient.getClient().getCastInfo(castId, Const.API_KEY);
        call.enqueue(new Callback<CastDetails>() {
            @Override
            public void onResponse(Call<CastDetails> call, Response<CastDetails> response) {
                try {
                    listCredits = new ArrayList<>();
                    listCredits = response.body().getCombinedCredits().getCast();
                    name = response.body().getName();
                    year = DateConverter.formateDateFromstring("yyyy-MM-dd", "dd.MM.yyy", response.body().getBirthday());
                    imagePath = response.body().getProfilePath();
                    textView_name.setText(name);
                    textView_year.setText(year);

                    if (listCredits.size() == 0) {
                        textView_knownFor.setVisibility(View.GONE);
                        gridView.setVisibility(View.GONE);
                    } else {
                        gridView.setAdapter(new GridViewCastDetailsAdapter(getContext(), listCredits));
                    }

                    File cacheDir = StorageUtils.getCacheDirectory(getActivity());
                    long size = 0;
                    File[] filesCache = cacheDir.listFiles();
                    for (File file : filesCache) {
                        size += file.length();
                    }
                    if (cacheDir.getUsableSpace() < MinFreeSpace || size > CacheSize) {
                        ImageLoader.getInstance().getDiskCache().clear();
                    }
                    setImage();
                } catch (NullPointerException | IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                linearLayoutInfoCast.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.placeholder_backdrop));
                linearLayout.setBackgroundColor(getResources().getColor(R.color.background_for_top_info_movie));
            }

            @Override
            public void onFailure(Call<CastDetails> call, Throwable t) {

            }
        });
        return rootView;
    }

    public void setImage() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if (imagePath == null || imagePath.equals("")) {
                    Glide
                            .with(getActivity())
                            .load(Const.IMAGE_POSTER_PATH_URL + imagePath)
                            .override(270, 210)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.placeholder_backdrop)
                            .crossFade()
                            .into(imageView);
                } else {
                    Glide
                            .with(getActivity())
                            .load(Const.IMAGE_POSTER_PATH_URL + imagePath)
                            .override(270, 210)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.placeholder_backdrop)
                            .crossFade()
                            .into(imageView);
                }

            }
        });
        t.run();
    }

}
