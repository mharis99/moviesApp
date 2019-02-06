package com.haris.android.movies.presentation.listener;

import com.haris.android.movies.presentation.model.MovieModel;

/**
 * Interface for listening movie list events.
 */
public interface MovieListListener {
    void onMovieClicked(final MovieModel movieModel);
}