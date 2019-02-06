/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haris.android.movies.data.repository.datasource;

import com.haris.android.movies.data.ApplicationTestCase;
import com.haris.android.movies.data.cache.MovieCache;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class MovieDataStoreFactoryTest extends ApplicationTestCase {

  private static final int FAKE_MOVIE_ID = 11;

  private MovieDataStoreFactory movieDataStoreFactory;

  @Mock private MovieCache mockMovieCache;

  @Before
  public void setUp() {
    movieDataStoreFactory = new MovieDataStoreFactory(RuntimeEnvironment.application, mockMovieCache);
  }

  @Test
  public void testCreateDiskDataStore() {
    given(mockMovieCache.isCached(FAKE_MOVIE_ID)).willReturn(true);
    given(mockMovieCache.isExpired()).willReturn(false);

    MovieDataStore movieDataStore = movieDataStoreFactory.create(FAKE_MOVIE_ID);

    assertThat(movieDataStore, is(notNullValue()));
    assertThat(movieDataStore, is(instanceOf(DiskMovieDataStore.class)));

    verify(mockMovieCache).isCached(FAKE_MOVIE_ID);
    verify(mockMovieCache).isExpired();
  }

  @Test
  public void testCreateCloudDataStore() {
    given(mockMovieCache.isExpired()).willReturn(true);
    given(mockMovieCache.isCached(FAKE_MOVIE_ID)).willReturn(false);

    MovieDataStore movieDataStore = movieDataStoreFactory.create(FAKE_MOVIE_ID);

    assertThat(movieDataStore, is(notNullValue()));
    assertThat(movieDataStore, is(instanceOf(CloudMovieDataStore.class)));

    verify(mockMovieCache).isExpired();
  }
}
