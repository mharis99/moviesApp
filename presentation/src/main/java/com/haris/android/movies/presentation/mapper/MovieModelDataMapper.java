/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haris.android.movies.presentation.mapper;

import com.haris.android.movies.domain.Movie;
import com.haris.android.movies.presentation.internal.di.PerActivity;
import com.haris.android.movies.presentation.model.MovieModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Movie} (in the domain layer) to {@link MovieModel} in the
 * presentation layer.
 */
@PerActivity
public class MovieModelDataMapper {

    @Inject
    public MovieModelDataMapper() {}

    /**
     * Transform a {@link Movie} into an {@link MovieModel}.
     *
     * @param movie Object to be transformed.
     * @return {@link MovieModel}.
     */
    public MovieModel transform(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final MovieModel movieModel = new MovieModel(movie.getMovieId());
        movieModel.setAdult(movie.isAdult());
        movieModel.setBackdropPath(movie.getBackdropPath());
        movieModel.setDescription(movie.getDescription());
        movieModel.setGenreIds(movie.getGenreIds());
        movieModel.setOriginalLanguage(movie.getOriginalLanguage());
        movieModel.setOriginalTitle(movie.getOriginalTitle());
        movieModel.setOverview(movie.getOverview());
        movieModel.setPopularity(movie.getPopularity());
        movieModel.setPosterPath(movie.getPosterPath());
        movieModel.setReleaseDate(movie.getReleaseDate());
        movieModel.setTitle(movie.getTitle());
        movieModel.setVideo(movie.isVideo());
        movieModel.setVoteAverage(movie.getVoteAverage());
        movieModel.setVoteCount(movie.getVoteCount());

        return movieModel;
    }

    /**
     * Transform a Collection of {@link Movie} into a Collection of {@link MovieModel}.
     *
     * @param MoviesCollection Objects to be transformed.
     * @return List of {@link MovieModel}.
     */
    public Collection<MovieModel> transform(Collection<Movie> MoviesCollection) {
        Collection<MovieModel> movieModelsCollection;

        if (MoviesCollection != null && !MoviesCollection.isEmpty()) {
            movieModelsCollection = new ArrayList<>();
            for (Movie movie : MoviesCollection) {
                movieModelsCollection.add(transform(movie));
            }
        } else {
            movieModelsCollection = Collections.emptyList();
        }

        return movieModelsCollection;
    }
}
