package com.example.moviedb;

import com.example.moviedb.model.movie.Cast;
import com.example.moviedb.model.movie.Genre;
import com.example.moviedb.model.movie.MovieDetails;
import com.example.moviedb.model.movie.ProductionCompany;
import com.example.moviedb.model.movie.Similar;
import com.example.moviedb.retrofit.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponseClass {

    private List<ProductionCompany> listCompanies;
    private List<Genre> listGenres;
    private List<Similar.Result> listRelatedMovies;
    private List<Cast> castsList;
    private ArrayList<String> listStringCompany;
    private ArrayList<String> listStringGenres;

    String title;
    String date_release;
    String url_image_backdrop_path;
    String url_image_poster_path;
    String tagline;
    String production_countries;
    int runtime;
    int votes;

    public void responseMovieDetails(int id) {
        listCompanies = new ArrayList<>();
        listGenres = new ArrayList<>();
        listStringCompany = new ArrayList<>();
        listStringGenres = new ArrayList<>();
        castsList = new ArrayList<>();

        Call<MovieDetails> call = ApiClient.getClient().getGenre(id, Const.API_KEY);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                try {
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
                    castsList = response.body().getCasts().getCast();

                } catch (NullPointerException | IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

            }
        });

    }



    public List<ProductionCompany> getListCompanies() {
        return listCompanies;
    }

    public List<Genre> getListGenres() {
        return listGenres;
    }

    public List<Similar.Result> getListRelatedMovies() {
        return listRelatedMovies;
    }

    public List<Cast> getCastsList() {
        return castsList;
    }

    public ArrayList<String> getListStringCompany() {
        return listStringCompany;
    }

    public ArrayList<String> getListStringGenres() {
        return listStringGenres;
    }

    public String getTitle() {
        return title;
    }

    public String getDate_release() {
        return date_release;
    }

    public String getUrl_image_backdrop_path() {
        return url_image_backdrop_path;
    }

    public String getUrl_image_poster_path() {
        return url_image_poster_path;
    }

    public String getTagline() {
        return tagline;
    }

    public String getProduction_countries() {
        return production_countries;
    }

    public int getRuntime() {
        return runtime;
    }

    public int getVotes() {
        return votes;
    }
}
