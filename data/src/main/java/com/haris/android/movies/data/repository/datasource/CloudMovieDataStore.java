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

import com.haris.android.movies.data.net.RestApi;
import com.haris.android.movies.data.cache.MovieCache;
import com.haris.android.movies.data.entity.MovieEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * {@link MovieDataStore} implementation based on connections to the api (Cloud).
 */
class CloudMovieDataStore implements MovieDataStore {

    private final RestApi restApi;
    private final MovieCache movieCache;

    /**
     * Construct a {@link MovieDataStore} based on connections to the api (Cloud).
     *
     * @param restApi    The {@link RestApi} implementation to use.
     * @param movieCache A {@link MovieCache} to cache data retrieved from the api.
     */
    CloudMovieDataStore(RestApi restApi, MovieCache movieCache) {
        this.restApi = restApi;
        this.movieCache = movieCache;
    }

    @Override
    public Observable<List<MovieEntity>> movieEntityList(int page) {
        return this.restApi.movieEntityList(page).map(response -> response.getData());
    }


    @Override
    public Observable<List<MovieEntity>> popularMovieEntityList(int page) {
        return this.restApi.popularMovieEntityList(page).map(response -> response.getData());
    }


    @Override
    public Observable<List<MovieEntity>> topMovieEntityList(int page) {
        return this.restApi.topMovieEntityList(page).map(response -> response.getData());
    }


    @Override
    public Observable<List<MovieEntity>> filterMovieEntityList(int page, String minYear, String maxYear) {
        return this.restApi.movieEntityFilterList(page, minYear, maxYear).map(response -> response.getData());
    }

    @Override
    public Observable<MovieEntity> movieEntityDetails(final int movieId) {
        return this.restApi.movieEntityById(movieId).doOnNext(CloudMovieDataStore.this.movieCache::put);
    }

    @Override
    public Observable<List<MovieEntity>> searchMovieEntityList(String query) {
        return this.restApi.searchMovieEntityList(query).map(response -> response.getData());
    }
}
