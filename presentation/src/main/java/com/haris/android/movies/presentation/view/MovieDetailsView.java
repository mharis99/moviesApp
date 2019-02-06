/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.haris.android.movies.presentation.view;

import com.haris.android.movies.presentation.model.MovieModel;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a movie profile.
 */
public interface MovieDetailsView extends LoadDataView {
  /**
   * Render a movie in the UI.
   *
   * @param movie The {@link MovieModel} that will be shown.
   */
  void renderMovie(MovieModel movie);
}
