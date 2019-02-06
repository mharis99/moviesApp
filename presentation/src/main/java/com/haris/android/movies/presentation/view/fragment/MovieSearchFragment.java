/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */

package com.haris.android.movies.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.haris.android.movies.presentation.internal.di.components.MovieComponent;
import com.haris.android.movies.presentation.model.MovieModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.haris.android.movies.presentation.R;

import com.haris.android.movies.presentation.presenter.MovieSearchPresenter;
import com.haris.android.movies.presentation.view.MovieSearchView;
import com.haris.android.movies.presentation.view.activity.MovieSearchActivity;
import com.haris.android.movies.presentation.view.adapter.MoviesAdapter;
import com.haris.android.movies.presentation.view.adapter.MoviesLayoutManager;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment that shows a list of Movies.
 */
public class MovieSearchFragment extends BaseFragment implements MovieSearchView {

    @Inject
    MovieSearchPresenter movieSearchPresenter;
    @Inject
    MoviesAdapter moviesAdapter;

    @Bind(R.id.rv_movies)
    RecyclerView rv_movies;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

    private MovieListFragment.MovieListListener movieListListener;


    public MovieSearchFragment() {
        setRetainInstance(true);
    }

    public static MovieSearchFragment getMovieSearchFragment() {
        final MovieSearchFragment movieListFragment = new MovieSearchFragment();
        return movieListFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof MovieListFragment.MovieListListener) {
            this.movieListListener = (MovieListFragment.MovieListListener) activity;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MovieComponent.class).inject(this);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView =
                inflater.inflate(R.layout.fragment_movie_search, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        setupSearchView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.movieSearchPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadMovieList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.movieSearchPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.movieSearchPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rv_movies.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.movieSearchPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.movieListListener = null;

    }

    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
    }

    @Override
    public void renderMovieList(Collection<MovieModel> movieModelCollection) {
        if (movieModelCollection != null) {
            this.moviesAdapter.setMoviesCollection(movieModelCollection);
        }
    }

    @Override
    public void renderMoreMovies(Collection<MovieModel> movieModelCollection) {
        if (movieModelCollection != null) {
            this.moviesAdapter.addMoviesCollection(movieModelCollection);
        }
    }

    @Override
    public void viewMovie(MovieModel movieModel) {
        if (this.movieListListener != null) {
            this.movieListListener.onMovieClicked(movieModel);
        }
    }

    @Override
    public void showFilterView(boolean filter) {

    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    private void setupRecyclerView() {
        this.moviesAdapter.setOnItemClickListener(onItemClickListener);
        MoviesLayoutManager moviesLayoutManager = new MoviesLayoutManager(context());
        this.rv_movies.setLayoutManager(moviesLayoutManager);
        this.rv_movies.setAdapter(moviesAdapter);
    }

    private void setupSearchView() {
        MaterialSearchView searchView = ((MovieSearchActivity) getActivity()).getSearchView();
        searchView.setVoiceSearch(false);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Snackbar
                        .make(getActivity().findViewById(R.id.container), "Query: " + query, Snackbar.LENGTH_LONG)
                        .show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.length() > 0) {
                    searchMovies(query);
                }

                return true;
            }
        });
    }

    /**
     * Loads all movies.
     */
    private void loadMovieList() {
        this.movieSearchPresenter.initialize();
    }

    /**
     * Search movies.
     */
    private void searchMovies(String query) {
        this.movieSearchPresenter.searchMovies(query);
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        MovieSearchFragment.this.loadMovieList();
    }

    private MoviesAdapter.OnItemClickListener onItemClickListener =
            new MoviesAdapter.OnItemClickListener() {
                @Override
                public void onMovieItemClicked(MovieModel movieModel) {
                    if (MovieSearchFragment.this.movieSearchPresenter != null && movieModel != null) {
                        MovieSearchFragment.this.movieSearchPresenter.onMovieClicked(movieModel);
                    }
                }
            };
}
