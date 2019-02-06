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
package com.haris.android.movies.data.repository;

import com.haris.android.movies.data.entity.mapper.MovieEntityDataMapper;
import com.haris.android.movies.data.repository.datasource.MovieDataStore;
import com.haris.android.movies.data.repository.datasource.MovieDataStoreFactory;
import com.haris.android.movies.domain.Movie;
import com.haris.android.movies.domain.repository.MovieRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link MovieRepository} for retrieving movie data.
 */
@Singleton
public class MovieDataRepository implements MovieRepository {

    private final MovieDataStoreFactory movieDataStoreFactory;
    private final MovieEntityDataMapper movieEntityDataMapper;

    /**
     * Constructs a {@link MovieRepository}.
     *
     * @param dataStoreFactory A factory to construct different data source implementations.
     * @param movieEntityDataMapper {@link MovieEntityDataMapper}.
     */
    @Inject
    MovieDataRepository(MovieDataStoreFactory dataStoreFactory,
                        MovieEntityDataMapper movieEntityDataMapper) {
        this.movieDataStoreFactory = dataStoreFactory;
        this.movieEntityDataMapper = movieEntityDataMapper;
    }

    @Override
    public Observable<List<Movie>> movies(int page) {
        // We always get all movies from the cloud
        final MovieDataStore movieDataStore = this.movieDataStoreFactory.createCloudDataStore();
        return movieDataStore.movieEntityList(page).map(this.movieEntityDataMapper::transform);
    }

    @Override
    public Observable<List<Movie>> searchMovies(String query) {
        // We always get all movies from the cloud
        final MovieDataStore movieDataStore = this.movieDataStoreFactory.createCloudDataStore();
        return movieDataStore.searchMovieEntityList(query).map(this.movieEntityDataMapper::transform);
    }


    @Override
    public Observable<List<Movie>> popularMovies(int page) {
        // We always get all movies from the cloud
        final MovieDataStore movieDataStore = this.movieDataStoreFactory.createCloudDataStore();
        return movieDataStore.popularMovieEntityList(page).map(this.movieEntityDataMapper::transform);
    }

    @Override
    public Observable<List<Movie>> topMovies(int page) {
        // We always get all movies from the cloud
        final MovieDataStore movieDataStore = this.movieDataStoreFactory.createCloudDataStore();
        return movieDataStore.topMovieEntityList(page).map(this.movieEntityDataMapper::transform);
    }


    @Override
    public Observable<List<Movie>> filterMovies(int page, String minYear, String maxYear) {
        // Filter movies from the cloud
        final MovieDataStore movieDataStore = this.movieDataStoreFactory.createCloudDataStore();
        return movieDataStore.filterMovieEntityList(page, minYear, maxYear).map(this.movieEntityDataMapper::transform);
    }

    @Override
    public Observable<Movie> movie(int movieId) {
        final MovieDataStore movieDataStore = this.movieDataStoreFactory.create(movieId);
        return movieDataStore.movieEntityDetails(movieId).map(this.movieEntityDataMapper::transform);
    }
}
