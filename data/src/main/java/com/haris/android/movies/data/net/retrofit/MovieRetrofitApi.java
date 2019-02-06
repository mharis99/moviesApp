package com.haris.android.movies.data.net.retrofit;

import com.haris.android.movies.data.entity.Response;
import com.haris.android.movies.data.entity.MovieEntity;
import com.haris.android.movies.data.net.RestApi;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit API interface for movies
 */
public interface MovieRetrofitApi {
    @GET(RestApi.API_URL_GET_MOVIES_LIST)
    Response<List<MovieEntity>> getMovieList(@Query("page") int page);

    @GET(RestApi.API_URL_GET_POPULAR_MOVIES_LIST)
    Response<List<MovieEntity>> getPopularMovieList(@Query("page") int page);

    @GET(RestApi.API_URL_GET_TOP_MOVIES_LIST)
    Response<List<MovieEntity>> getTopMovieList(@Query("page") int page);

    @GET(RestApi.API_URL_GET_MOVIES_LIST)
    Response<List<MovieEntity>> filterMovieList(@Query("page") int page, @Query("primary_release_date.gte") String minYear, @Query("primary_release_date.lte") String maxYear);

    @GET(RestApi.API_URL_GET_MOVIE_DETAILS)
    MovieEntity getMovieDetails(@Path("movie_id") int movieId);

    @GET(RestApi.API_URL_SEARCH_MOVIES)
    Response<List<MovieEntity>> searchMovies(@Query("query") String query);
}
