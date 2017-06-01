package com.example.moviedb.retrofit;

import com.example.moviedb.model.genre.GenreDetails;
import com.example.moviedb.model.genre.Genres;
import com.example.moviedb.model.movie.CastDetails;
import com.example.moviedb.model.movie.MovieDetails;
import com.example.moviedb.model.movie.MovieResponse;
import com.example.moviedb.model.search.SearchResponse;
import com.example.moviedb.model.tv.details.TVDetailsResponseTV;
import com.example.moviedb.model.tv.standart.TVResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("discover/movie")
    Call<MovieResponse> getPopularyMovies(@Query("page") Integer numberPage, @Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("page") Integer numberPage, @Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("page") Integer numberPage, @Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@Query("page") Integer numberPage, @Query("api_key") String apiKey);

//---------------------------------------------------------------------------------------------------------------

    @GET("movie/{id}?append_to_response=releases%2Ctrailers%2Ccasts%2Cimages%2Csimilar")
    Call<MovieDetails> getGenre(@Path("id") Integer id, @Query("api_key") String apiKey);

    @GET("person/{id}?append_to_response=combined_credits%2Cimages")
    Call<CastDetails> getCastInfo(@Path("id") Integer id, @Query("api_key") String apiKey);

//---------------------------------------------------------------------------------------------------------------

    @GET("discover/tv")
    Call<TVResponse> getPopularyTVShow(@Query("page") Integer numberPage, @Query("api_key") String apiKey);

    @GET("tv/top_rated")
    Call<TVResponse> getTopRatedTVShow(@Query("page") Integer numberPage, @Query("api_key") String apiKey);

    @GET("tv/airing_today")
    Call<TVResponse> getAiringTodayTVShow(@Query("page") Integer numberPage, @Query("api_key") String apiKey);

    @GET("tv/on_the_air")
    Call<TVResponse> getOnTheAirTVShow(@Query("page") Integer numberPage, @Query("api_key") String apiKey);
//------------------------------------------------------------------------------------------------------------------

    @GET("tv/{id}?append_to_response=images,credits,similar")
    Call<TVDetailsResponseTV> getTVDetails(@Path("id") Integer id, @Query("api_key") String apiKey);

//------------------------------------------------------------------------------------------------------------------

    @GET("genre/movie/list")
    Call<Genres> getGenres(@Query("api_key") String apiKey);

    @GET("genre/{id}/movies")
    Call<GenreDetails> getGenreDetails(@Path("id") Integer id, @Query("page") Integer numberPage, @Query("api_key") String apiKey);

    @GET("search/multi")
    Call<SearchResponse> getSearch(@Query("page") Integer numberPage, @Query("query") String text, @Query("api_key") String apiKey);
}
