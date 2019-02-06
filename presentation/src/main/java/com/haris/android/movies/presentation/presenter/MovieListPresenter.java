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
import com.haris.android.movies.domain.interactor.FilterMovieList;
import com.haris.android.movies.domain.interactor.GetMovieList;
import com.haris.android.movies.domain.interactor.GetPopularMoviesList;
import com.haris.android.movies.domain.interactor.GetTopMoviesList;
import com.haris.android.movies.presentation.exception.ErrorMessageFactory;
import com.haris.android.movies.presentation.internal.di.PerActivity;
import com.haris.android.movies.presentation.model.MovieModel;
import com.haris.android.movies.presentation.view.MovieListView;
import com.haris.android.movies.presentation.mapper.MovieModelDataMapper;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class MovieListPresenter implements Presenter {

    private MovieListView viewListView;

    private final GetMovieList getMovieListUseCase;
    private final FilterMovieList filterMovieListUseCase;
    private final GetPopularMoviesList popularMoviesList;
    private final GetTopMoviesList topMoviesList;

    private final MovieModelDataMapper movieModelDataMapper;

    @Inject
    public MovieListPresenter(GetMovieList getMovieListMovieCase,
                              FilterMovieList filterMovieListUseCase,
                              GetPopularMoviesList popularMoviesList,
                              GetTopMoviesList topMoviesList,
                              MovieModelDataMapper movieModelDataMapper) {
        this.getMovieListUseCase = getMovieListMovieCase;
        this.movieModelDataMapper = movieModelDataMapper;
        this.popularMoviesList = popularMoviesList;
        this.topMoviesList = topMoviesList;
        this.filterMovieListUseCase = filterMovieListUseCase;

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
     * Initializes the presenter by start retrieving the popular movie list.
     */
    public void initializePopularMovies() {
        this.loadPopularMovieList();
    }

    /**
     * Initializes the presenter by start retrieving the top movie list.
     */
    public void initializeTopMovies() {
        this.loadTopMovieList();
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
     * Loads popular movies.
     */
    private void loadPopularMovieList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getPopularMovieList();
    }

    /**
     * Loads popular movies.
     */
    private void loadTopMovieList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getTopMovieList();
    }

    /**
     * Loads filtered movies based on minYear and maxYear
     *
     * @param minYear
     * @param maxYear
     */
    public void filterMovies(int page, String minYear, String maxYear) {
        this.hideViewRetry();
        this.showViewLoading();
        this.filterMovieList(page, minYear, maxYear);
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


    private void getPopularMovieList() {
        this.popularMoviesList.execute(new MovieListObserver(), GetPopularMoviesList.Params.forList(1));
    }

    private void getTopMovieList() {
        this.topMoviesList.execute(new MovieListObserver(), GetTopMoviesList.Params.forList(1));
    }

    private void filterMovieList(int page, String minYear, String maxYear) {
        this.filterMovieListUseCase.execute(new MovieListObserver(), FilterMovieList.Params.forFilter(page, minYear, maxYear));
    }

    public void loadMoreMovies(int page) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getMovieListUseCase.execute(new MoreMovieListObserver(), GetMovieList.Params.forList(page));
    }

    public void loadMorePopularMovies(int page) {
        this.hideViewRetry();
        this.showViewLoading();
        this.popularMoviesList.execute(new MoreMovieListObserver(), GetPopularMoviesList.Params.forList(page));
    }

    public void loadMoreTopMovies(int page) {
        this.hideViewRetry();
        this.showViewLoading();
        this.topMoviesList.execute(new MoreMovieListObserver(), GetTopMoviesList.Params.forList(page));
    }

    public void loadMoreFilteredMovies(int page, String minYear, String maxYear) {
        this.hideViewRetry();
        this.showViewLoading();
        this.filterMovieListUseCase.execute(new MoreMovieListObserver(), FilterMovieList.Params.forFilter(page, minYear, maxYear));
    }

    public void toggleFilterView(boolean filter) {
        viewListView.showFilterView(filter);
    }

    private final class MovieListObserver extends DefaultObserver<List<Movie>> {

        @Override
        public void onComplete() {
            MovieListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            MovieListPresenter.this.hideViewLoading();
            MovieListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            MovieListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Movie> movies) {
            MovieListPresenter.this.showMoviesCollectionInView(movies);
        }
    }

    private final class MoreMovieListObserver extends DefaultObserver<List<Movie>> {

        @Override
        public void onComplete() {
            MovieListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            MovieListPresenter.this.hideViewLoading();
            MovieListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            MovieListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Movie> movies) {
            MovieListPresenter.this.addMoreMoviesCollectionInView(movies);
        }
    }
}