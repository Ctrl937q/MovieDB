package com.example.moviedb.fragments;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.activity.ActivityImage;
import com.example.moviedb.activity.ActivityTrailerPreview;
import com.example.moviedb.adapters.GridViewMovieDetailsAdapter;
import com.example.moviedb.converter.DateConverter;
import com.example.moviedb.model.Genre;
import com.example.moviedb.model.MovieDetails;
import com.example.moviedb.model.ProductionCompany;
import com.example.moviedb.model.ProductionCountry;
import com.example.moviedb.model.Similar;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentInfo extends Fragment implements View.OnClickListener {

    private String title;
    private String date_release;
    private String url_image_backdrop_path;
    private String url_image_poster_path;
    private String tagline;
    private List<ProductionCountry> production_countries;
    private int runtime;
    private int votes;
    private int itemId;
    private TextView textView_title;
    private TextView textView_date_release;
    private TextView textView_runtime;
    private TextView textView_tagline;
    private TextView textView_production_companies;
    private TextView textView_production_countries;
    private TextView text_view_genre;
    private TextView text_view_votes;
    private TextView textView_RelatedMovies;
    private ImageView imageView_backdrop_path;
    private ImageView imageView_poster_path;
    private List<ProductionCompany> listCompanies;
    private List<Genre> listGenres;
    private ProgressBar progressBar;
    private List<Similar.Result> listRelatedMovies;
    private ArrayList<String> listStringCompany;
    private ArrayList<String> listStringGenres;
    private GridView gridView;
    private FloatingActionButton floatingActionButton;
    private RatingBar rating_bar_info_fragment;
    private NestedScrollView nestedScrollView;
    private ImageLoader imageLoader;
    private final int CacheSize = 52428800; // 50MB
    private final int MinFreeSpace = 2048; // 2MB

    DisplayImageOptions optionsBackdrop = new DisplayImageOptions.Builder()
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .cacheInMemory(false)
            .cacheOnDisk(true)
            .build();

    DisplayImageOptions optionsPoster = new DisplayImageOptions.Builder()
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .cacheInMemory(false)
            .cacheOnDisk(true)
            .build();


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.info_fragment, container, false);
        Runtime.getRuntime().maxMemory();
        initializeViewById(rootView);
        rating_bar_info_fragment.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        listCompanies = new ArrayList<>();
        listGenres = new ArrayList<>();
        listStringCompany = new ArrayList<>();
        listStringGenres = new ArrayList<>();
        production_countries = new ArrayList<>();
        imageView_backdrop_path.setFocusableInTouchMode(true);
        itemId = getActivity().getIntent().getIntExtra("id", 1);
        Call<MovieDetails> call = ApiClient.getClient().getGenre(itemId, Const.API_KEY);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                try {
                    double d = response.body().getVoteAverage() / 2;
                    float f = (float) d;
                    rating_bar_info_fragment.setRating(f);
                    title = response.body().getTitle();
                    date_release = response.body().getReleaseDate();
                    runtime = response.body().getRuntime();
                    tagline = response.body().getTagline();
                    production_countries = response.body().getProductionCountries();
                    url_image_backdrop_path = response.body().getBackdropPath();
                    url_image_poster_path = response.body().getPosterPath();
                    listCompanies = response.body().getProductionCompanies();
                    listGenres = response.body().getGenres();
                    votes = response.body().getVoteCount();
                    listRelatedMovies = response.body().getSimilar().getResults();
                    textView_title.setText(title);
                    textView_date_release.setText(DateConverter.formateDateFromstring("yyyy-MM-dd", "dd, MMMM, yyy", date_release));
                    textView_runtime.setText("" + runtime + " min");
                    if (tagline.equals("")) {
                        textView_tagline.setText("");
                        textView_tagline.setVisibility(View.GONE);
                    } else {
                        textView_tagline.setText("''" + tagline + "''");
                    }

                    if (production_countries.size() != 0) {
                        textView_production_countries.setText("" + production_countries.get(0).getName());
                    } else {
                        textView_production_countries.setText("");
                        textView_production_countries.setVisibility(View.GONE);
                    }

                    for (int i = 0; i < listCompanies.size(); i++) {
                        listStringCompany.add(listCompanies.get(i).getName());
                    }

                    textView_production_companies.setText("" + TextUtils.join(", ", listStringCompany));

                    for (int i = 0; i < listGenres.size(); i++) {
                        listStringGenres.add(listGenres.get(i).getName());
                    }
                    text_view_genre.setText("" + TextUtils.join(", ", listStringGenres));
                    text_view_votes.setText("" + votes + " votes");

                    File cacheDir = StorageUtils.getCacheDirectory(getActivity());
                    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                            .diskCache(new UnlimitedDiskCache(cacheDir))
                            .defaultDisplayImageOptions(optionsBackdrop)
                            .build();
                    ImageLoader.getInstance().init(config);
                    imageLoader = ImageLoader.getInstance();
                    long size = 0;
                    File[] filesCache = cacheDir.listFiles();
                    for (File file : filesCache) {
                        size += file.length();
                    }
                    if (cacheDir.getUsableSpace() < MinFreeSpace || size > CacheSize) {
                        ImageLoader.getInstance().getDiskCache().clear();
                    }

                    ImageSize targetSizePoster = new ImageSize(130, 130);
                    ImageSize targetSizeBackdrop = new ImageSize(300, 300);

                    if (url_image_backdrop_path == null) {
                        imageLoader.loadImage(Const.IMAGE_POSTER_PATH_URL + url_image_poster_path,
                                targetSizeBackdrop, optionsPoster, new SimpleImageLoadingListener() {
                                    @Override
                                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                        imageView_backdrop_path.setImageBitmap(loadedImage);
                                    }
                                });
                        //imageLoader.displayImage(Const.IMAGE_POSTER_PATH_URL + url_image_poster_path, imageView_backdrop_path);
                    } else {
                        imageLoader.loadImage(Const.IMAGE_POSTER_PATH_URL + url_image_backdrop_path,
                                targetSizeBackdrop, optionsPoster, new SimpleImageLoadingListener() {
                                    @Override
                                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                        imageView_backdrop_path.setImageBitmap(loadedImage);
                                    }
                                });
                        //imageLoader.displayImage(Const.IMAGE_POSTER_PATH_URL + url_image_backdrop_path, imageView_backdrop_path);
                    }

                    imageLoader.loadImage(Const.IMAGE_POSTER_PATH_URL + url_image_poster_path,
                            targetSizePoster, optionsBackdrop, new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    imageView_poster_path.setImageBitmap(loadedImage);
                                }
                            });

                    //imageLoader.displayImage(Const.IMAGE_POSTER_PATH_URL + url_image_poster_path, imageView_poster_path);
                    if (listRelatedMovies.size() == 0) {
                        textView_RelatedMovies.setVisibility(View.GONE);
                        gridView.setVisibility(View.GONE);
                    } else {
                        gridView.setAdapter(new GridViewMovieDetailsAdapter(getContext(), listRelatedMovies));
                    }

                } catch (NullPointerException | IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.INVISIBLE);
                rating_bar_info_fragment.setVisibility(View.VISIBLE);
                nestedScrollView.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

            }
        });

        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initializeViewById(View rootView) {
        textView_title = (TextView) rootView.findViewById(R.id.text_view_name_film_details);
        imageView_backdrop_path = (ImageView) rootView.findViewById(R.id.image_view_backprop_details);
        imageView_poster_path = (ImageView) rootView.findViewById(R.id.image_view_posterPath_details);
        textView_date_release = (TextView) rootView.findViewById(R.id.text_view_date_film_details);
        textView_runtime = (TextView) rootView.findViewById(R.id.text_view_duration_film_details);
        textView_tagline = (TextView) rootView.findViewById(R.id.text_view_tagline_film_details);
        textView_production_companies = (TextView) rootView.findViewById(R.id.text_view_production_companies);
        textView_production_countries = (TextView) rootView.findViewById(R.id.text_view_production_countries);
        text_view_votes = (TextView) rootView.findViewById(R.id.text_view_votes_details);
        text_view_genre = (TextView) rootView.findViewById(R.id.text_view_genre);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar_info_fragment);
        gridView = (GridView) rootView.findViewById(R.id.grid_view_for_related_movies);
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton_movieDetails);
        textView_RelatedMovies = (TextView) rootView.findViewById(R.id.textView_RelatedMovies);
        rating_bar_info_fragment = (RatingBar) rootView.findViewById(R.id.rating_bar_info_fragment);
        nestedScrollView = (NestedScrollView) rootView.findViewById(R.id.nested_scroll_view_info_fragment);
        floatingActionButton.setOnClickListener(this);
        imageView_backdrop_path.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == floatingActionButton.getId()) {
            Intent intent = new Intent(getContext(), ActivityTrailerPreview.class);
            intent.putExtra("film_id", itemId);
            startActivity(intent);
        } else if (v.getId() == imageView_backdrop_path.getId()) {
            Intent intent = new Intent(getActivity(), ActivityImage.class);
            startActivity(intent);
        }
    }
}