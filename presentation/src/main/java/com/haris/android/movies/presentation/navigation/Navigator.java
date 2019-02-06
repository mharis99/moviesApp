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
package com.haris.android.movies.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.haris.android.movies.presentation.view.activity.MovieDetailsActivity;
import com.haris.android.movies.presentation.view.activity.MovieListActivity;
import com.haris.android.movies.presentation.view.activity.MovieSearchActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator() {
        //empty
    }

    /**
     * Goes to the movie list screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToMovieList(Context context) {
        if (context != null) {
            Intent intentToLaunch = MovieListActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the movie details screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToMovieDetails(Context context, int movieId) {
        if (context != null) {
            Intent intentToLaunch = MovieDetailsActivity.getCallingIntent(context, movieId);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToMovieListWithFilter(Context context) {
        if (context != null) {
            Intent intentToLaunch = MovieListActivity.getFilterCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToPopularMovies(Context context) {
        if (context != null) {
            Intent intentToLaunch = MovieListActivity.getPopularMoviesCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToTopMovies(Context context) {
        if (context != null) {
            Intent intentToLaunch = MovieListActivity.getTopMoviesCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToMovieSearch(Context context) {
        if (context != null) {
            Intent intentToLaunch = MovieSearchActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }



}
