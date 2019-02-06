/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.haris.android.movies.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.haris.android.movies.presentation.internal.di.HasComponent;
import com.haris.android.movies.presentation.internal.di.components.MovieComponent;
import com.haris.android.movies.presentation.view.fragment.MoviesDetailsFragment;
import com.haris.android.movies.presentation.R;
import com.haris.android.movies.presentation.internal.di.components.DaggerMovieComponent;

/**
 * Activity that shows details of a certain movie.
 */
public class MovieDetailsActivity extends BaseActivity implements HasComponent<MovieComponent> {

    private static final String INTENT_EXTRA_PARAM_MOVIE_ID = "com.haris.android.INTENT_PARAM_MOVIE_ID";

    public static Intent getCallingIntent(Context context, int movieId) {
        Intent callingIntent = new Intent(context, MovieDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_MOVIE_ID, movieId);
        return callingIntent;
    }

    private int movieId;
    private MovieComponent movieComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout);

        this.initializeActivity();
        this.initializeInjector();
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity() {
        this.movieId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_MOVIE_ID, -1);
        addFragment(R.id.fragmentContainer, MoviesDetailsFragment.forMovie(movieId));
    }

    private void initializeInjector() {
        this.movieComponent = DaggerMovieComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public MovieComponent getComponent() {
        return movieComponent;
    }
}
