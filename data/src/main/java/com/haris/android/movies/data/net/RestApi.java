/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haris.android.movies.data.net;

import com.haris.android.movies.data.entity.Response;
import com.haris.android.movies.data.entity.MovieEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
    String API_BASE_URL = "https://api.themoviedb.org/";
    /**
     * Base Url for image accessing
     */
    String BASE_IMAGES_URL = "https://image.tmdb.org/t/p/w500";

    /**
     * Api url for getting movies list
     */
    String API_URL_GET_MOVIES_LIST = "3/discover/movie";


    /**
     * Api url for getting popular movies
     */
    String API_URL_GET_POPULAR_MOVIES_LIST = "3/movie/popular";

    /**
     * Api url for getting top movies
     */
    String API_URL_GET_TOP_MOVIES_LIST = "3/movie/top_rated";

    /**
     * Api url for getting a movie details
     */
    String API_URL_GET_MOVIE_DETAILS = "3/movie/{movie_id}";

    /**
     * Api url for searching movies
     */
    String API_URL_SEARCH_MOVIES = "3/search/movie";

    /**
     * Api key v3
     */
    String API_KEY = "de1514eb58f4646ac43a38d1a8f4dcd3";

    /**
     * Retrieves an {@link Observable} which will emit a List of {@link MovieEntity}.
     */
    Observable<Response<List<MovieEntity>>> movieEntityList(int page);


    /**
     * Retrieves an {@link Observable} which will emit a List of popular {@link MovieEntity}.
     */
    Observable<Response<List<MovieEntity>>> popularMovieEntityList(int page);


    /**
     * Retrieves an {@link Observable} which will emit a List of top {@link MovieEntity}.
     */
    Observable<Response<List<MovieEntity>>> topMovieEntityList(int page);



    /**
     * Retrieves an {@link Observable} which will emit a filtered List of {@link MovieEntity}.
     * based on minYear and maxYear
     */
    Observable<Response<List<MovieEntity>>> movieEntityFilterList(int page, String minYear, String maxYear);

    /**
     * Retrieves an {@link Observable} which will emit a {@link MovieEntity}.
     *
     * @param movieId The movie id used to get movie data.
     */
    Observable<MovieEntity> movieEntityById(final int movieId);

    /**
     * Retrieves an {@link Observable} which will emit a List of {@link MovieEntity}.
     */
    Observable<Response<List<MovieEntity>>> searchMovieEntityList(String query);
}
