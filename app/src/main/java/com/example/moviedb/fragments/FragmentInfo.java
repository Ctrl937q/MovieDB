package com.example.moviedb.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Log;
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

import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.ResponseClass;
import com.example.moviedb.adapters.GridViewAdapter;
import com.example.moviedb.converter.DateConverter;
import com.example.moviedb.model.Casts;
import com.example.moviedb.model.Genre;
import com.example.moviedb.model.MovieDetails;
import com.example.moviedb.model.ProductionCompany;
import com.example.moviedb.model.Similar;
import com.example.moviedb.retrofit.ApiClient;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentInfo extends Fragment {

    private String title;
    private String date_release;
    private String url_image_backdrop_path;
    private String url_image_poster_path;
    private String tagline;
    private String production_countries;
    private int runtime;
    private int votes;
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
    private ResponseClass responseClass;
    private Button button_afterRelatedMovies;
    private LinearLayout linearLayout;
    private RatingBar rating_bar_info_fragment;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.info_fragment, container, false);
        responseClass = new ResponseClass();
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
       // linearLayout = (LinearLayout) rootView.findViewById(R.id.linear_layout_related_movies);
        textView_RelatedMovies = (TextView)rootView.findViewById(R.id.textView_RelatedMovies);
        button_afterRelatedMovies = (Button)rootView.findViewById(R.id.button_afterRelatedMovies);
        rating_bar_info_fragment = (RatingBar) rootView.findViewById(R.id.rating_bar_info_fragment);


        listCompanies = new ArrayList<>();
        listGenres = new ArrayList<>();
        listStringCompany = new ArrayList<>();
        listStringGenres = new ArrayList<>();



        final int itemId = getActivity().getIntent().getIntExtra("id", 1);
        Call<MovieDetails> call = ApiClient.getClient().getGenre(itemId, Const.API_KEY);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                try {
                    double d = response.body().getVoteAverage() / 2;
                    float f = (float)d;
                    rating_bar_info_fragment.setRating(f);
                    //Drawable progress = rating_bar_info_fragment.getProgressDrawable();
                    //DrawableCompat.setTint(progress, Color.YELLOW);
                    title = response.body().getTitle();
                    date_release = response.body().getReleaseDate();
                    runtime = response.body().getRuntime();
                    tagline = response.body().getTagline();
                    production_countries = response.body().getProductionCountries().get(0).getName();
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
                    textView_production_countries.setText("" + production_countries);

                    for (int i = 0; i < listCompanies.size(); i++) {
                        listStringCompany.add(listCompanies.get(i).getName());
                    }

                    textView_production_companies.setText("" + TextUtils.join(", ", listStringCompany));

                    for (int i = 0; i < listGenres.size(); i++) {
                        listStringGenres.add(listGenres.get(i).getName());
                    }
                    text_view_genre.setText("" + TextUtils.join(", ", listStringGenres));
                    text_view_votes.setText("" + votes + " votes");

                    if(url_image_backdrop_path == null){
                        Picasso.with(getActivity()).load(Const.IMAGE_POSTER_PATH_URL + url_image_poster_path)
                                .placeholder(R.drawable.placeholder_item_recycler_view)
                                .resize(700, 500)
                                .centerCrop()
                                .into(imageView_backdrop_path);
                    }else {
                        Picasso.with(getActivity()).load(Const.IMAGE_POSTER_PATH_URL + url_image_backdrop_path)
                                .placeholder(R.drawable.placeholder_backdrop)
                                .resize(700, 500)
                                .into(imageView_backdrop_path);
                    }

                    Picasso.with(getActivity()).load(Const.IMAGE_POSTER_PATH_URL + url_image_poster_path)
                            .placeholder(R.drawable.placeholder_item_recycler_view)
                            .resize(170, 180)
                            .into(imageView_poster_path);

                    if (listRelatedMovies.size() == 0) {
                        textView_RelatedMovies.setVisibility(View.GONE);
                        button_afterRelatedMovies.setVisibility(View.GONE);
                        gridView.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.GONE);
                    } else {
                        gridView.setAdapter(new GridViewAdapter(getActivity(), listRelatedMovies));
                    }

                } catch (NullPointerException | IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.INVISIBLE);
                floatingActionButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

            }
        });

        return rootView;
    }

}