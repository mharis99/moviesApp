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

package com.haris.android.movies.domain.repository;

import com.haris.android.movies.domain.Movie;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface that represents a Repository for getting {@link Movie} related data.
 */
public interface MovieRepository {
    /**
     * Get an {@link Observable} which will emit a List of {@link Movie}.
     */
    Observable<List<Movie>> movies(int page);

    /**
     * Get an {@link Observable} which will emit a List of popular {@link Movie}.
     */
    Observable<List<Movie>> popularMovies(int page);

    /**
     * Get an {@link Observable} which will emit a List of top {@link Movie}.
     */
    Observable<List<Movie>> topMovies(int page);

    /**
     * Get an {@link Observable} which will emit a Filtered List of {@link Movie}
     * based on minYear and maxYear
     */
    Observable<List<Movie>> filterMovies(int page, String minYear, String maxYear);

    /**
     * Get an {@link Observable} which will emit a {@link Movie}.
     *
     * @param movieId The movie id used to retrieve movie data.
     */
    Observable<Movie> movie(final int movieId);

    /**
     * Get an {@link Observable} which will emit a List of {@link Movie}.
     */
    Observable<List<Movie>> searchMovies(String query);
}
