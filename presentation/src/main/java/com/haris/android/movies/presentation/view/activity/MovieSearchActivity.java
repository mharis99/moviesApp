/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */

package com.haris.android.movies.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.haris.android.movies.presentation.R;
import com.haris.android.movies.presentation.internal.di.HasComponent;
import com.haris.android.movies.presentation.internal.di.components.MovieComponent;
import com.haris.android.movies.presentation.model.MovieModel;
import com.haris.android.movies.presentation.view.fragment.MovieListFragment;
import com.haris.android.movies.presentation.view.fragment.MovieSearchFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import com.haris.android.movies.presentation.internal.di.components.DaggerMovieComponent;

/**
 * Activity that shows a list of Movies.
 */
public class MovieSearchActivity extends BaseActivity implements HasComponent<MovieComponent>,
        MovieListFragment.MovieListListener {

    private MaterialSearchView searchView;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MovieSearchActivity.class);
    }

    private MovieComponent movieComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, MovieSearchFragment.getMovieSearchFragment());
        }

        this.setTitle(R.string.activity_title_movie_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
    }

    private void initializeInjector() {
        this.movieComponent = DaggerMovieComponent
                .builder()
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    public MaterialSearchView getSearchView() {
        return searchView;
    }
}
