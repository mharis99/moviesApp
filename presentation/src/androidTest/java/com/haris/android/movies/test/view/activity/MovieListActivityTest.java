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
package com.haris.android.movies.test.view.activity;

import android.app.Fragment;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.haris.android.movies.presentation.R;
import com.haris.android.movies.presentation.view.activity.MovieListActivity;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class MovieListActivityTest extends ActivityInstrumentationTestCase2<MovieListActivity> {

    private MovieListActivity movieListActivity;

    public MovieListActivityTest() {
        super(MovieListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testContainsMovieListFragment() {
        this.setActivityIntent(createTargetIntent());
        movieListActivity = getActivity();

        Fragment movieListFragment =
                movieListActivity.getFragmentManager().findFragmentById(R.id.fragmentContainer);
        assertThat(movieListFragment, is(notNullValue()));
    }

    @Test
    public void testMovieListView_WhenWithoutFilter() {
        this.setActivityIntent(createTargetIntent());
        movieListActivity = getActivity();

        String actualTitle = this.movieListActivity.getTitle().toString().trim();

        assertThat(actualTitle, is("Movies List"));
    }

    @Test
    public void testMovieListView_WhenWithFilter() {
        this.setActivityIntent(createFilterTargetIntent());
        movieListActivity = getActivity();

        String actualTitle = this.movieListActivity.getTitle().toString().trim();

        assertThat(actualTitle, is("Filter Movies List"));
        onView(withId(R.id.filter_movies_view)).check(matches(isDisplayed()));
    }

    private Intent createTargetIntent() {
        Intent intentLaunchActivity =
                MovieListActivity.getCallingIntent(getInstrumentation().getTargetContext());

        return intentLaunchActivity;
    }

    private Intent createFilterTargetIntent() {
        Intent intentLaunchActivity =
                MovieListActivity.getFilterCallingIntent(getInstrumentation().getTargetContext());

        return intentLaunchActivity;
    }
}
