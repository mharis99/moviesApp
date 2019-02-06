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
import com.haris.android.movies.presentation.model.MovieModel;
import com.haris.android.movies.presentation.R;
import com.haris.android.movies.presentation.internal.di.components.DaggerMovieComponent;
import com.haris.android.movies.presentation.view.fragment.MovieListFragment;


/**
 * Activity that shows a list of Movies.
 */
public class MovieListActivity extends BaseActivity implements HasComponent<MovieComponent>,
        MovieListFragment.MovieListListener {

    private static final String INTENT_EXTRA_PARAM_FILTER = "com.haris.android.INTENT_PARAM_FILTER";
    private static final String INTENT_EXTRA_PARAM_POPULAR = "com.haris.android.INTENT_PARAM_POPULAR";
    private static final String INTENT_EXTRA_PARAM_TOP = "com.haris.android.INTENT_PARAM_TOP";

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MovieListActivity.class);
    }

    public static Intent getFilterCallingIntent(Context context) {
        Intent intent = new Intent(context, MovieListActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_FILTER, true);
        return intent;
    }

    public static Intent getPopularMoviesCallingIntent(Context context) {
        Intent intent = new Intent(context, MovieListActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_POPULAR, true);
        return intent;
    }

    public static Intent getTopMoviesCallingIntent(Context context) {
        Intent intent = new Intent(context, MovieListActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_TOP, true);
        return intent;
    }

    private boolean filter;
    private boolean popular;
    private boolean top;
    private MovieComponent movieComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        this.initializeActivity();
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity() {
        this.filter = getIntent().getBooleanExtra(INTENT_EXTRA_PARAM_FILTER, false);
        this.popular = getIntent().getBooleanExtra(INTENT_EXTRA_PARAM_POPULAR, false);
        this.top = getIntent().getBooleanExtra(INTENT_EXTRA_PARAM_TOP, false);
        addFragment(R.id.fragmentContainer, MovieListFragment.getListFragment(this.filter, this.popular, this.top));


        if (this.filter) {
            this.setTitle(R.string.activity_title_filter_movie_list);

        } else if (this.popular) {
            this.setTitle(R.string.activity_title_popular_movie_list);

        } else if (this.top) {
            this.setTitle(R.string.activity_title_popular_top_list);

        } else {
            this.setTitle(R.string.activity_title_movie_list);

        }


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

    @Override
    public void onMovieClicked(MovieModel movieModel) {
        this.navigator.navigateToMovieDetails(this, movieModel.getMovieId());
    }
}