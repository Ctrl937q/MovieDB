package com.example.moviedb.fragments.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.moviedb.activity.ActivityTrailerPreview;
import com.example.moviedb.adapters.GridViewMovieDetailsAdapter;
import com.example.moviedb.converter.DateConverter;
import com.example.moviedb.model.movie.Genre;
import com.example.moviedb.model.movie.MovieDetails;
import com.example.moviedb.model.movie.ProductionCompany;
import com.example.moviedb.model.movie.ProductionCountry;
import com.example.moviedb.model.movie.Similar;
import com.example.moviedb.retrofit.ApiClient;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.StorageUtils;
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
    private final int CacheSize = 52428800;
    private final int MinFreeSpace = 2048;
    LinearLayout linearLayoutBot;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.info_fragment, container, false);
        Runtime.getRuntime().maxMemory();
        initializeViewById(rootView);
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
                    long size = 0;
                    File[] filesCache = cacheDir.listFiles();
                    for (File file : filesCache) {
                        size += file.length();
                    }
                    if (cacheDir.getUsableSpace() < MinFreeSpace || size > CacheSize) {
                        ImageLoader.getInstance().getDiskCache().clear();
                    }
                    setImage();
                    if (listRelatedMovies.size() == 0) {
                        textView_RelatedMovies.setVisibility(View.GONE);
                        gridView.setVisibility(View.GONE);
                    } else {
                        gridView.setAdapter(new GridViewMovieDetailsAdapter(getContext(), listRelatedMovies));
                    }

                } catch (NullPointerException | IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                imageView_poster_path.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                rating_bar_info_fragment.setVisibility(View.VISIBLE);
                nestedScrollView.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.VISIBLE);
                imageView_backdrop_path.setImageDrawable(getResources().getDrawable(R.drawable.placeholder_backdrop));
                linearLayoutBot.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

            }
        });
        return rootView;
    }

    public void setImage() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Glide
                        .with(getActivity())
                        .load(Const.IMAGE_POSTER_PATH_URL + url_image_backdrop_path)
                        .override(250, 210)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder_backdrop)
                        .crossFade()
                        .into(imageView_backdrop_path);
                Glide
                        .with(getActivity())
                        .load(Const.IMAGE_POSTER_PATH_URL + url_image_poster_path)
                        .override(110, 110)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder_item_recycler_view)
                        .crossFade()
                        .into(imageView_poster_path);
            }
        });
        t.run();
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
        linearLayoutBot = (LinearLayout) rootView.findViewById(R.id.linear_layout_info_fragment_bottom);
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
           /* Intent intent = new Intent(getActivity(), ActivityImage.class);
            intent.putExtra("film_id", itemId);
            startActivity(intent);*/
        }
    }


    public static FragmentInfo newInstance() {
        return new FragmentInfo();
    }
}