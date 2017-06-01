package com.example.moviedb.fragments.tv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.adapters.GridViewTVShowDetailsAdapter;
import com.example.moviedb.converter.DateConverter;
import com.example.moviedb.model.tv.details.GenreTV;
import com.example.moviedb.model.tv.details.ProductionCompanyTV;
import com.example.moviedb.model.tv.details.ResultTV;
import com.example.moviedb.model.tv.details.TVDetailsResponseTV;
import com.example.moviedb.retrofit.ApiClient;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentInfoTVShow extends Fragment implements View.OnClickListener {

    int itemId;
    String url_image_backdrop_path_tv;
    String url_image_poster_path_tv;
    String name;
    String status;
    String type;
    List<Integer> episodeRuntime;
    int numberOfEpisodes;
    int numberOfSeasons;
    String firstAirDate;
    String lastAirDate;
    List<String> originalCountry;
    int voteCount;
    double voteAverage;
    ImageView imageView_backdrop_path;
    ImageView imageView_poster_path;
    TextView textView_name;
    TextView textView_status;
    TextView textView_runtime;
    TextView textView_type;
    TextView textView_number_of_episodes;
    TextView textView_number_of_seasons;
    TextView textView_first_air_date;
    TextView text_view_last_air_date;
    TextView text_view_origin_country;
    TextView textView_production_company;
    TextView text_view_genres;
    TextView textView_all_votes;
    TextView textView_RelatedTVShow;
    ProgressBar progressBar;
    GridView gridView_tv;
    RatingBar rating_bar_info_fragment_tv;
    NestedScrollView nestedScrollView;
    Call<TVDetailsResponseTV> call;
    List<ProductionCompanyTV> productionCompanyList;
    List<GenreTV> genreList;
    List<String> productionCompanyListString;
    List<String> genreListString;
    List<ResultTV> resultTvList;
    private final int CacheSize = 52428800;
    private final int MinFreeSpace = 2048;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.info_tv_show_fragment, container, false);
        Runtime.getRuntime().maxMemory();
        initializeViewById(rootView);
        imageView_backdrop_path.setFocusableInTouchMode(true);
        productionCompanyList = new ArrayList<>();
        productionCompanyListString = new ArrayList<>();
        genreList = new ArrayList<>();
        genreListString = new ArrayList<>();
        resultTvList = new ArrayList<>();
        episodeRuntime = new ArrayList<>();
        originalCountry = new ArrayList<>();
        itemId = getActivity().getIntent().getIntExtra("id", 1);

        call = ApiClient.getClient().getTVDetails(itemId, Const.API_KEY);
        call.enqueue(new Callback<TVDetailsResponseTV>() {
            @Override
            public void onResponse(Call<TVDetailsResponseTV> call, Response<TVDetailsResponseTV> response) {
                name = response.body().getName();
                status = response.body().getStatus();
                type = response.body().getType();
                episodeRuntime = response.body().getEpisodeRunTime();
                url_image_backdrop_path_tv = response.body().getBackdropPath();
                url_image_poster_path_tv = response.body().getPosterPath();
                numberOfEpisodes = response.body().getNumberOfEpisodes();
                numberOfSeasons = response.body().getNumberOfSeasons();
                firstAirDate = response.body().getFirstAirDate();
                lastAirDate = response.body().getLastAirDate();
                originalCountry = response.body().getOriginCountry();
                productionCompanyList = response.body().getProductionCompanies();
                genreList = response.body().getGenreTVs();
                voteCount = response.body().getVoteCount();
                voteAverage= response.body().getVoteAverage() / 2;
                resultTvList = response.body().getSimilarTV().getResultTVs();
                imageLoad();
                setContent();
                setImage();
                rating_bar_info_fragment_tv.setVisibility(View.VISIBLE);
                imageView_poster_path.setVisibility(View.VISIBLE);
                imageView_backdrop_path.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TVDetailsResponseTV> call, Throwable t) {

            }
        });
        return rootView;
    }

    @Override
    public void onClick(View v) {

    }

    public void setContent() {
        textView_name.setText(name);
        textView_status.setText(status);
        textView_type.setText("Type: " + type);
        textView_runtime.setText("" + TextUtils.join(", ", episodeRuntime) + " min.");
        textView_number_of_episodes.setText("Number of episodes: " + numberOfEpisodes);
        textView_number_of_seasons.setText("Number of seasons: " + numberOfSeasons);
        textView_first_air_date.setText("First air date: " + DateConverter.formateDateFromstring("yyyy-MM-dd", "dd.MM.yyyy", firstAirDate));
        text_view_last_air_date.setText("Last air date: " + DateConverter.formateDateFromstring("yyyy-MM-dd", "dd.MM.yyyy", lastAirDate));
        text_view_origin_country.setText("" + TextUtils.join(", ", originalCountry));
        for (int i = 0; i < productionCompanyList.size(); i++) {
            productionCompanyListString.add(productionCompanyList.get(i).getName());
        }
        textView_production_company.setText("" + TextUtils.join(", ", productionCompanyListString));

        for (int i = 0; i < genreList.size(); i++) {
            genreListString.add(genreList.get(i).getName());
        }
        text_view_genres.setText("" + TextUtils.join(", ", genreListString));
        textView_all_votes.setText("" + voteCount);
        float floatVoteAverage = (float) voteAverage;
        rating_bar_info_fragment_tv.setRating(floatVoteAverage);
        if(resultTvList.size() == 0){
            textView_RelatedTVShow.setVisibility(View.GONE);
            gridView_tv.setVisibility(View.GONE);
        }else {
            gridView_tv.setAdapter(new GridViewTVShowDetailsAdapter(getActivity(), resultTvList));
        }
    }

    public void imageLoad(){
        File cacheDir;
        cacheDir = StorageUtils.getCacheDirectory(getActivity());
        if(cacheDir != null) {
            long size = 0;
            File[] filesCache = cacheDir.listFiles();
            for (File file : filesCache) {
                size += file.length();
            }
            if (cacheDir.getUsableSpace() < MinFreeSpace || size > CacheSize) {
                ImageLoader.getInstance().getDiskCache().clear();
            }
        }
    }

    public void setImage() {
        if (url_image_backdrop_path_tv == null) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Glide
                            .with(getActivity())
                            .load(Const.IMAGE_POSTER_PATH_URL + url_image_poster_path_tv)
                            .override(320, 270)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.placeholder_backdrop)
                            .crossFade()
                            .into(imageView_backdrop_path);
                }
            });
            t.run();
        }else {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Glide
                            .with(getActivity())
                            .load(Const.IMAGE_POSTER_PATH_URL + url_image_backdrop_path_tv)
                            .override(320, 270)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.placeholder_backdrop)
                            .crossFade()
                            .into(imageView_backdrop_path);
                }
            });
            t.run();
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Glide
                        .with(getActivity())
                        .load(Const.IMAGE_POSTER_PATH_URL + url_image_poster_path_tv)
                        .override(80, 80)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder_item_recycler_view)
                        .crossFade()
                        .into(imageView_poster_path);
            }
        });
        t.run();
    }

    public void initializeViewById(View rootView) {
        textView_name = (TextView) rootView.findViewById(R.id.text_view_name_film_details_tv);
        imageView_backdrop_path = (ImageView) rootView.findViewById(R.id.image_view_backprop_details_tv);
        imageView_poster_path = (ImageView) rootView.findViewById(R.id.image_view_posterPath_details_tv);
        textView_status = (TextView) rootView.findViewById(R.id.text_view_status_tv);
        textView_runtime = (TextView) rootView.findViewById(R.id.text_view_duration_tv);
        textView_type = (TextView) rootView.findViewById(R.id.text_view_type_tv);
        textView_number_of_episodes = (TextView) rootView.findViewById(R.id.text_view_number_of_episodes_tv);
        textView_number_of_seasons = (TextView) rootView.findViewById(R.id.text_view_number_of_season_tv);
        textView_first_air_date = (TextView) rootView.findViewById(R.id.text_view_first_air_date_tv);
        text_view_last_air_date = (TextView) rootView.findViewById(R.id.text_view_last_air_date_tv);
        text_view_origin_country = (TextView) rootView.findViewById(R.id.text_view_country_tv);
        textView_production_company = (TextView) rootView.findViewById(R.id.text_view_companies_tv);
        text_view_genres = (TextView) rootView.findViewById(R.id.text_view_genre_tv);
        textView_all_votes = (TextView) rootView.findViewById(R.id.text_view_votes_details_tv);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar_info_fragment_tv);
        gridView_tv = (GridView) rootView.findViewById(R.id.grid_view_for_related_movies_tv);
        textView_RelatedTVShow = (TextView) rootView.findViewById(R.id.textView_RelatedMovies_tv);
        rating_bar_info_fragment_tv = (RatingBar) rootView.findViewById(R.id.rating_bar_info_fragment_tv);
        nestedScrollView = (NestedScrollView) rootView.findViewById(R.id.nested_scroll_view_info_fragment_tv);
        imageView_backdrop_path.setOnClickListener(this);
    }
}
