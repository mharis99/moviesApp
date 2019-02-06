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
package com.haris.android.movies.data.repository.datasource;

import com.haris.android.movies.data.entity.MovieEntity;
import com.haris.android.movies.domain.Movie;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface MovieDataStore {
    /**
     * Get an {@link Observable} which will emit a List of {@link MovieEntity}.
     */
    Observable<List<MovieEntity>> movieEntityList(int page);


    /**
     * Get an {@link Observable} which will emit a List of popular {@link MovieEntity}.
     */
    Observable<List<MovieEntity>> popularMovieEntityList(int page);

    /**
     * Get an {@link Observable} which will emit a List of top {@link MovieEntity}.
     */
    Observable<List<MovieEntity>> topMovieEntityList(int page);


    /**
     * Get an {@link Observable} which will emit a Filtered List of {@link Movie}
     * based on minYear and maxYear
     */
    Observable<List<MovieEntity>> filterMovieEntityList(int page, String minYear, String maxYear);

    /**
     * Get an {@link Observable} which will emit a {@link MovieEntity} by its id.
     *
     * @param movieId The id to retrieve movie data.
     */
    Observable<MovieEntity> movieEntityDetails(final int movieId);

    /**
     * Get an {@link Observable} which will emit a List of {@link MovieEntity}.
     * @param query
     */
    Observable<List<MovieEntity>> searchMovieEntityList(String query);

}
