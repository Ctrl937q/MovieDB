package com.example.moviedb.retrofit;

import com.example.moviedb.model.movie.CastDetails;
import com.example.moviedb.model.movie.MovieDetails;
import com.example.moviedb.model.movie.MovieResponse;
import com.example.moviedb.model.tv.details.TVDetailsResponseTV;
import com.example.moviedb.model.tv.standart.TVResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("discover/movie?page=")
    Call<MovieResponse> getPopularyMovies(@Query("page")Integer numberPage,@Query("api_key") String apiKey);

    @GET("movie/top_rated?page=")
    Call<MovieResponse> getTopRatedMovies(@Query("page")Integer numberPage, @Query("api_key") String apiKey);

    @GET("movie/upcoming?page=")
    Call<MovieResponse> getUpcomingMovies(@Query("page") Integer numberPage, @Query("api_key") String apiKey);

    @GET("movie/now_playing?page=")
    Call<MovieResponse> getNowPlayingMovies(@Query("page") Integer numberPage, @Query("api_key") String apiKey);

//---------------------------------------------------------------------------------------------------------------

    @GET("movie/{id}?append_to_response=releases%2Ctrailers%2Ccasts%2Cimages%2Csimilar")
    Call<MovieDetails> getGenre(@Path("id") Integer id, @Query("api_key") String apiKey);

    @GET("person/{id}?append_to_response=combined_credits%2Cimages")
    Call<CastDetails>getCastInfo(@Path("id")Integer id, @Query("api_key") String apiKey);
//---------------------------------------------------------------------------------------------------------------

    @GET("discover/tv?page=")
    Call<TVResponse>getPopularyTVShow(@Query("page")Integer numberPage, @Query("api_key")String apiKey);

    @GET("tv/top_rated?page=")
    Call<TVResponse>getTopRatedTVShow(@Query("page")Integer numberPage, @Query("api_key")String apiKey);

    @GET("tv/airing_today?page=")
    Call<TVResponse>getAiringTodayTVShow(@Query("page")Integer numberPage, @Query("api_key")String apiKey);

    @GET("tv/on_the_air?page=")
    Call<TVResponse>getOnTheAirTVShow(@Query("page")Integer numberPage, @Query("api_key")String apiKey);
//------------------------------------------------------------------------------------------------------------------

    @GET("tv/{id}?append_to_response=images,credits,similar")
    Call<TVDetailsResponseTV>getTVDetails(@Path("id") Integer id, @Query("api_key") String apiKey);

}
