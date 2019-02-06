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
package com.haris.android.movies.presentation.presenter;

import android.support.annotation.NonNull;

import com.haris.android.movies.domain.Movie;
import com.haris.android.movies.domain.exception.DefaultErrorBundle;
import com.haris.android.movies.domain.exception.ErrorBundle;
import com.haris.android.movies.domain.interactor.DefaultObserver;
import com.haris.android.movies.domain.interactor.GetMovieList;
import com.haris.android.movies.domain.interactor.SearchMovies;
import com.haris.android.movies.presentation.exception.ErrorMessageFactory;
import com.haris.android.movies.presentation.internal.di.PerActivity;
import com.haris.android.movies.presentation.mapper.MovieModelDataMapper;
import com.haris.android.movies.presentation.model.MovieModel;
import com.haris.android.movies.presentation.view.MovieListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class MovieSearchPresenter implements Presenter {

    private MovieListView viewListView;

    private final GetMovieList getMovieListUseCase;
    private final SearchMovies searchMoviesUseCase;

    private final MovieModelDataMapper movieModelDataMapper;

    @Inject
    public MovieSearchPresenter(GetMovieList getMovieListMovieCase,
                                SearchMovies searchMoviesUseCase,
                                MovieModelDataMapper movieModelDataMapper) {
        this.getMovieListUseCase = getMovieListMovieCase;
        this.searchMoviesUseCase = searchMoviesUseCase;
        this.movieModelDataMapper = movieModelDataMapper;
    }

    public void setView(@NonNull MovieListView view) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getMovieListUseCase.dispose();
        this.viewListView = null;
    }

    /**
     * Initializes the presenter by start retrieving the movie list.
     */
    public void initialize() {
        this.loadMovieList();
    }

    /**
     * Loads all movies.
     */
    private void loadMovieList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getMovieList();
    }

    /**
     * Load movies based on this query string
     */
    public void searchMovies(String query) {
        this.hideViewRetry();
        this.showViewLoading();
        this.loadSearchedMovies(query);
    }

    public void onMovieClicked(MovieModel movieModel) {
        this.viewListView.viewMovie(movieModel);
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.context(),
                errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void showMoviesCollectionInView(Collection<Movie> moviesCollection) {
        final Collection<MovieModel> movieModelsCollection =
                this.movieModelDataMapper.transform(moviesCollection);
        this.viewListView.renderMovieList(movieModelsCollection);
    }

    private void addMoreMoviesCollectionInView(Collection<Movie> moviesCollection) {
        final Collection<MovieModel> movieModelsCollection =
                this.movieModelDataMapper.transform(moviesCollection);
        this.viewListView.renderMoreMovies(movieModelsCollection);
    }

    private void getMovieList() {
        this.getMovieListUseCase.execute(new MovieListObserver(), GetMovieList.Params.forList(1));
    }

    private void loadSearchedMovies(String query) {
        this.searchMoviesUseCase.execute(new MovieListObserver(), SearchMovies.Params.forSearch(query));
    }

    private final class MovieListObserver extends DefaultObserver<List<Movie>> {

        @Override
        public void onComplete() {
            MovieSearchPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            MovieSearchPresenter.this.hideViewLoading();
            MovieSearchPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            MovieSearchPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Movie> movies) {
            MovieSearchPresenter.this.showMoviesCollectionInView(movies);
        }
    }
}
