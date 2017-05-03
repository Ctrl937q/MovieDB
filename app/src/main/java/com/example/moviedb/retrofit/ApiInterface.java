package com.example.moviedb.retrofit;

import com.example.moviedb.model.CastDetails;
import com.example.moviedb.model.MovieDetails;
import com.example.moviedb.model.MovieResponse;
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



    @GET("movie/{id}?append_to_response=releases%2Ctrailers%2Ccasts%2Cimages%2Csimilar")
    Call<MovieDetails> getGenre(@Path("id") Integer id, @Query("api_key") String apiKey);

    @GET("person/{id}?append_to_response=combined_credits%2Cimages")
    Call<CastDetails>getCastInfo(@Path("id")Integer id, @Query("api_key") String apiKey);

}
