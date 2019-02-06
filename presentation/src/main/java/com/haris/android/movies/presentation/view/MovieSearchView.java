/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.haris.android.movies.presentation.view;

import com.haris.android.movies.presentation.model.MovieModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link MovieModel}.
 */
public interface MovieSearchView extends MovieListView {
    /**
     * Render a movie list in the UI.
     *
     * @param movieModelCollection The collection of {@link MovieModel} that will be shown.
     */
    void renderMovieList(Collection<MovieModel> movieModelCollection);

    /**
     * View a {@link MovieModel} profile/details.
     *
     * @param movieModel The movie that will be shown.
     */
    void viewMovie(MovieModel movieModel);

    void renderMoreMovies(Collection<MovieModel> movieModelsCollection);
}
