/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.haris.android.movies.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.haris.android.movies.presentation.R;
import com.haris.android.movies.presentation.internal.di.components.MovieComponent;
import com.haris.android.movies.presentation.model.MovieModel;
import com.haris.android.movies.presentation.presenter.MovieDetailsPresenter;
import com.haris.android.movies.presentation.view.MovieDetailsView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.internal.Preconditions;

/**
 * Fragment that shows details of a certain movie.
 */
public class MoviesDetailsFragment extends BaseFragment implements MovieDetailsView {
    private static final String PARAM_MOVIE_ID = "param_movie_id";

    @Inject
    MovieDetailsPresenter movieDetailsPresenter;

    @Bind(R.id.iv_poster)
    ImageView iv_poster;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_original_title)
    TextView tv_original_title;
    @Bind(R.id.tv_release_date)
    TextView tv_release_date;
    @Bind(R.id.tv_overview)
    TextView tv_overview;
    @Bind(R.id.tv_popularity)
    TextView tv_popularity;
    @Bind(R.id.tv_vote_count)
    TextView tv_vote_count;
    @Bind(R.id.tv_vote_average)
    TextView tv_vote_average;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

    public static MoviesDetailsFragment forMovie(int movieId) {
        final MoviesDetailsFragment moviesDetailsFragment = new MoviesDetailsFragment();
        final Bundle arguments = new Bundle();
        arguments.putInt(PARAM_MOVIE_ID, movieId);
        moviesDetailsFragment.setArguments(arguments);
        return moviesDetailsFragment;
    }

    public MoviesDetailsFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MovieComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_movies_details, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.movieDetailsPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadMovieDetails();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.movieDetailsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.movieDetailsPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.movieDetailsPresenter.destroy();
    }

    @Override
    public void renderMovie(MovieModel movie) {
        if (movie != null) {
            Picasso.with(getActivity().getApplicationContext()).load(movie.getPosterPath()).into(this.iv_poster);
            this.tv_title.setText(movie.getTitle());
            this.tv_original_title.setText(movie.getOriginalTitle());
            this.tv_release_date.setText(movie.getReleaseDate());
            this.tv_overview.setText(movie.getOverview());
            this.tv_popularity.setText(String.valueOf(movie.getPopularity()));
            this.tv_vote_count.setText(String.valueOf(movie.getVoteCount()));
            this.tv_vote_average.setText(String.valueOf(movie.getVoteAverage()));
        }
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
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    /**
     * Load movie details.
     */
    private void loadMovieDetails() {
        if (this.movieDetailsPresenter != null) {
            this.movieDetailsPresenter.initialize(currentMovieId());
        }
    }

    /**
     * Get current movie id from fragments arguments.
     */
    private int currentMovieId() {
        final Bundle arguments = getArguments();
        Preconditions.checkNotNull(arguments, "Fragment arguments cannot be null");
        return arguments.getInt(PARAM_MOVIE_ID);
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        MoviesDetailsFragment.this.loadMovieDetails();
    }
}
