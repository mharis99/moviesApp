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

import com.haris.android.movies.data.cache.MovieCache;
import com.haris.android.movies.data.repository.datasource.DiskMovieDataStore;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DiskMovieDataStoreTest {

  private static final int FAKE_MOVIE_ID = 11;
  private static final int PAGE = 1;

  private DiskMovieDataStore diskMovieDataStore;

  @Mock private MovieCache mockMovieCache;

  @Rule public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    diskMovieDataStore = new DiskMovieDataStore(mockMovieCache);
  }

  @Test
  public void testGetMovieEntityListUnsupported() {
    expectedException.expect(UnsupportedOperationException.class);
    diskMovieDataStore.movieEntityList(PAGE);
  }

  @Test
  public void testGetMovieEntityDetailesFromCache() {
    diskMovieDataStore.movieEntityDetails(FAKE_MOVIE_ID);
    verify(mockMovieCache).get(FAKE_MOVIE_ID);
  }
}
