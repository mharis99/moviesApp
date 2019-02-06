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
import com.haris.android.movies.presentation.view.activity.MovieDetailsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class MovieDetailsActivityTest extends ActivityInstrumentationTestCase2<MovieDetailsActivity> {

    private static final int FAKE_MOVIE_ID = 123;
    private MovieDetailsActivity movieDetailsActivity;

    public MovieDetailsActivityTest() {
        super(MovieDetailsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.setActivityIntent(createTargetIntent());
        this.movieDetailsActivity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testContainsMovieDetailsFragment() {
        Fragment movieDetailsFragment =
                movieDetailsActivity.getFragmentManager().findFragmentById(R.id.fragmentContainer);
        assertThat(movieDetailsFragment, is(notNullValue()));
    }

    public void testContainsProperTitle() {
        String actualTitle = this.movieDetailsActivity.getTitle().toString().trim();

        assertThat(actualTitle, is("Movie Details"));
    }

    public void testLoadMovieHappyCaseViews() {
        onView(withId(R.id.rl_retry)).check(matches(not(isDisplayed())));
        onView(withId(R.id.rl_progress)).check(matches(not(isDisplayed())));

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_original_title)).check(matches(isDisplayed()));
    }

    public void testLoadMovieHappyCaseData() throws InterruptedException {
        onView(withId(R.id.tv_title)).check(matches(withText("")));
    }

    private Intent createTargetIntent() {
        Intent intentLaunchActivity =
                MovieDetailsActivity.getCallingIntent(getInstrumentation().getTargetContext(), FAKE_MOVIE_ID);

        return intentLaunchActivity;
    }
}
