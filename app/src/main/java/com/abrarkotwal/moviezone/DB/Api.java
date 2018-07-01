package com.abrarkotwal.moviezone.DB;

import com.abrarkotwal.moviezone.Adapter.Pojo.Movie;
import com.abrarkotwal.moviezone.Adapter.Pojo.MovieImages;
import com.abrarkotwal.moviezone.Adapter.Pojo.SingleMovie;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {


    public static final String API_KEY      = "b7cd3340a794e5a2f35e3abb820b497f";
    public static final String IMG_DISP_URL = "http://image.tmdb.org/t/p/w500/";

    @GET("movie/upcoming")
    Call<Movie> getUpcommingMovieList(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<SingleMovie> getMovieDetail(@Path("movie_id") int id, @Query("api_key") String apiKey);


    @GET("movie/{movie_id}/images")
    Call<MovieImages> getMovieImageList(@Path("movie_id") int id, @Query("api_key") String apiKey);
}