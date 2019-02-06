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

import com.haris.android.movies.data.cache.MovieCache;
import com.haris.android.movies.data.entity.MovieEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * {@link MovieDataStore} implementation based on file system data store.
 */
class DiskMovieDataStore implements MovieDataStore {

    private final MovieCache movieCache;

    /**
     * Construct a {@link MovieDataStore} based file system data store.
     *
     * @param movieCache A {@link MovieCache} to cache data retrieved from the api.
     */
    DiskMovieDataStore(MovieCache movieCache) {
        this.movieCache = movieCache;
    }

    @Override
    public Observable<List<MovieEntity>> movieEntityList(int page) {
        //TODO: implement simple cache for storing/retrieving collections of movies.
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<List<MovieEntity>> popularMovieEntityList(int page) {
        //TODO: implement simple cache for storing/retrieving collections of movies.
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<List<MovieEntity>> topMovieEntityList(int page) {
        //TODO: implement simple cache for storing/retrieving collections of movies.
        throw new UnsupportedOperationException("Operation is not available!!!");
    }


    @Override
    public Observable<List<MovieEntity>> filterMovieEntityList(int page, String minYear, String maxYear) {
        //TODO: implement simple cache for storing/retrieving collections of movies.
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<MovieEntity> movieEntityDetails(final int movieId) {
        return this.movieCache.get(movieId);
    }

    @Override
    public Observable<List<MovieEntity>> searchMovieEntityList(String query) {
        //TODO: implement simple cache for storing/retrieving collections of movies.
        throw new UnsupportedOperationException("Operation is not available!!!");
    }
}
