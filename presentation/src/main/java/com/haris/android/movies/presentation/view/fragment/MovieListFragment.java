/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.haris.android.movies.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.haris.android.movies.presentation.internal.di.components.MovieComponent;
import com.haris.android.movies.presentation.model.MovieModel;
import com.haris.android.movies.presentation.presenter.MovieListPresenter;
import com.haris.android.movies.presentation.view.MovieListView;
import com.haris.android.movies.presentation.view.adapter.MoviesAdapter;
import com.haris.android.movies.presentation.view.adapter.MoviesLayoutManager;
import com.haris.android.movies.presentation.view.component.EndlessRecyclerViewScrollListener;
import com.haris.android.movies.presentation.R;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment that shows a list of Movies.
 */
public class MovieListFragment extends BaseFragment implements MovieListView {

    private static final String PARAM_FILTER = "param_filter";

    private static final String PARAM_POPULAR_MOVIES = "param_popular";

    private static final String PARAM_TOP_MOVIES = "top_popular";

    /**
     * Interface for listening movie list events.
     */
    public interface MovieListListener {
        void onMovieClicked(final MovieModel movieModel);
    }

    @Inject
    MovieListPresenter movieListPresenter;
    @Inject
    MoviesAdapter moviesAdapter;

    @Bind(R.id.filter_movies_view)
    LinearLayout filter_movies_view;
    @Bind(R.id.dd_min_year)
    Spinner dd_min_year_view;
    @Bind(R.id.dd_max_year)
    Spinner dd_max_year_view;
    @Bind(R.id.rv_movies)
    RecyclerView rv_movies;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

    private String minYear;
    private String maxYear;
    private MovieListListener movieListListener;

    public MovieListFragment() {
        setRetainInstance(true);
    }

    public static MovieListFragment getListFragment(boolean filter, boolean popularMovies, Boolean topMovies) {
        final MovieListFragment movieListFragment = new MovieListFragment();
        final Bundle arguments = new Bundle();
        arguments.putBoolean(PARAM_FILTER, filter);
        arguments.putBoolean(PARAM_POPULAR_MOVIES, popularMovies);
        arguments.putBoolean(PARAM_TOP_MOVIES, topMovies);
        movieListFragment.setArguments(arguments);
        return movieListFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MovieListListener) {
            this.movieListListener = (MovieListListener) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MovieComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        setupSpinners();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.movieListPresenter.setView(this);
        this.movieListPresenter.toggleFilterView(this.filter());
        if (savedInstanceState == null) {
            if (this.filter()) {
                minYear = dd_min_year_view.getSelectedItem().toString();
                maxYear = dd_max_year_view.getSelectedItem().toString();
                this.filterMovieList(minYear, maxYear);
            } else if (this.popularMovies()) {
                this.loadPopularMovieList();
            } else if (this.topMovies()) {
                this.loadTopMovieList();
            } else {
                this.loadMovieList();

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.movieListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.movieListPresenter.pause();
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
        this.movieListPresenter.destroy();
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
        this.filter_movies_view.setVisibility(filter ? View.VISIBLE : View.GONE);
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

        // Retain an instance so that you can call `resetState()` for fresh searches
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(moviesLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                System.out.println("Page: " + page);
                System.out.println("totalItemsCount: " + totalItemsCount);
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };

        // Adds the scroll listener to RecyclerView
        this.rv_movies.setOnScrollListener(scrollListener);
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int page) {
        if (filter()) {
            this.movieListPresenter.loadMoreFilteredMovies(page, minYear, maxYear);
        } else {
            this.movieListPresenter.loadMoreMovies(page);
        }
    }

    private void setupSpinners() {
        this.dd_min_year_view.setOnItemSelectedListener(onMinYearDropDownSelectedListener);
        this.dd_max_year_view.setOnItemSelectedListener(onMaxYearDropDownSelectedListener);
    }

    /**
     * Loads all movies.
     */
    private void loadMovieList() {
        this.movieListPresenter.initialize();
    }

    /**
     * Loads popular movies.
     */
    private void loadPopularMovieList() {
        this.movieListPresenter.initializePopularMovies();
    }

    /**
     * Loads top movies.
     */
    private void loadTopMovieList() {
        this.movieListPresenter.initializeTopMovies();
    }


    /**
     * Filter movies.
     *
     * @param minYear
     * @param maxYear
     */
    private void filterMovieList(String minYear, String maxYear) {
        this.movieListPresenter.filterMovies(1, minYear, maxYear);
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        MovieListFragment.this.loadMovieList();
    }

    private MoviesAdapter.OnItemClickListener onItemClickListener =
            new MoviesAdapter.OnItemClickListener() {
                @Override
                public void onMovieItemClicked(MovieModel movieModel) {
                    if (MovieListFragment.this.movieListPresenter != null && movieModel != null) {
                        MovieListFragment.this.movieListPresenter.onMovieClicked(movieModel);
                    }
                }
            };

    /**
     * When min year value gets changed by the Movie from the drop down
     */
    private AdapterView.OnItemSelectedListener onMinYearDropDownSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            minYear = parent.getSelectedItem().toString();
            filterMovieList(minYear, maxYear);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // doNothing
        }
    };

    /**
     * When max year value gets changed by the Movie from the drop down
     */
    private AdapterView.OnItemSelectedListener onMaxYearDropDownSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            maxYear = parent.getSelectedItem().toString();
            filterMovieList(minYear, maxYear);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // doNothing
        }
    };

    /**
     * check if filter
     */
    private boolean filter() {
        final Bundle arguments = getArguments();
        return arguments.getBoolean(PARAM_FILTER, false);
    }


    /**
     * check if popular movies
     */
    private boolean popularMovies() {
        final Bundle arguments = getArguments();
        return arguments.getBoolean(PARAM_POPULAR_MOVIES, false);
    }

    /**
     * check if top movies
     */
    private boolean topMovies() {
        final Bundle arguments = getArguments();
        return arguments.getBoolean(PARAM_TOP_MOVIES, false);
    }
}