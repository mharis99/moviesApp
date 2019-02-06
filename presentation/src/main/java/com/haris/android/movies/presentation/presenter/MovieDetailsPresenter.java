/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haris.android.movies.presentation.presenter;

import android.support.annotation.NonNull;

import com.haris.android.movies.domain.Movie;
import com.haris.android.movies.domain.exception.DefaultErrorBundle;
import com.haris.android.movies.domain.exception.ErrorBundle;
import com.haris.android.movies.domain.interactor.DefaultObserver;
import com.haris.android.movies.domain.interactor.GetMovieDetails;
import com.haris.android.movies.domain.interactor.GetMovieDetails.Params;
import com.haris.android.movies.presentation.exception.ErrorMessageFactory;
import com.haris.android.movies.presentation.internal.di.PerActivity;
import com.haris.android.movies.presentation.model.MovieModel;
import com.haris.android.movies.presentation.mapper.MovieModelDataMapper;
import com.haris.android.movies.presentation.view.MovieDetailsView;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class MovieDetailsPresenter implements Presenter {

  private MovieDetailsView viewDetailsView;

  private final GetMovieDetails getMovieDetailsUseCase;
  private final MovieModelDataMapper movieModelDataMapper;

  @Inject
  public MovieDetailsPresenter(GetMovieDetails getMovieDetailsUseCase,
                               MovieModelDataMapper movieModelDataMapper) {
    this.getMovieDetailsUseCase = getMovieDetailsUseCase;
    this.movieModelDataMapper = movieModelDataMapper;
  }

  public void setView(@NonNull MovieDetailsView view) {
    this.viewDetailsView = view;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  @Override public void destroy() {
    this.getMovieDetailsUseCase.dispose();
    this.viewDetailsView = null;
  }

  /**
   * Initializes the presenter by showing/hiding proper views
   * and retrieving movie details.
   */
  public void initialize(int movieId) {
    this.hideViewRetry();
    this.showViewLoading();
    this.getMovieDetails(movieId);
  }

  private void getMovieDetails(int movieId) {
    this.getMovieDetailsUseCase.execute(new MovieDetailsObserver(), Params.forMovie(movieId));
  }

  private void showViewLoading() {
    this.viewDetailsView.showLoading();
  }

  private void hideViewLoading() {
    this.viewDetailsView.hideLoading();
  }

  private void showViewRetry() {
    this.viewDetailsView.showRetry();
  }

  private void hideViewRetry() {
    this.viewDetailsView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.context(),
        errorBundle.getException());
    this.viewDetailsView.showError(errorMessage);
  }

  private void showMovieDetailsInView(Movie movie) {
    final MovieModel movieModel = this.movieModelDataMapper.transform(movie);
    this.viewDetailsView.renderMovie(movieModel);
  }

  private final class MovieDetailsObserver extends DefaultObserver<Movie> {

    @Override public void onComplete() {
      MovieDetailsPresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
      MovieDetailsPresenter.this.hideViewLoading();
      MovieDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      MovieDetailsPresenter.this.showViewRetry();
    }

    @Override public void onNext(Movie movie) {
      MovieDetailsPresenter.this.showMovieDetailsInView(movie);
    }
  }
}
