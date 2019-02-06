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
package com.haris.android.movies.domain.interactor;

import com.haris.android.movies.domain.executor.PostExecutionThread;
import com.haris.android.movies.domain.executor.ThreadExecutor;
import com.haris.android.movies.domain.interactor.GetMovieDetails;
import com.haris.android.movies.domain.interactor.GetMovieDetails.Params;
import com.haris.android.movies.domain.repository.MovieRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetMovieDetailsTest {

  private static final int MOVIE_ID = 123;

  private GetMovieDetails getMovieDetails;

  @Mock private MovieRepository mockMovieRepository;
  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;

  @Rule public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    getMovieDetails = new GetMovieDetails(mockMovieRepository, mockThreadExecutor,
        mockPostExecutionThread);
  }

  @Test
  public void testGetMovieDetailsUseCaseObservableHappyCase() {
    getMovieDetails.buildUseCaseObservable(Params.forMovie(MOVIE_ID));

    verify(mockMovieRepository).movie(MOVIE_ID);
    verifyNoMoreInteractions(mockMovieRepository);
    verifyZeroInteractions(mockPostExecutionThread);
    verifyZeroInteractions(mockThreadExecutor);
  }

  @Test
  public void testShouldFailWhenNoOrEmptyParameters() {
    expectedException.expect(NullPointerException.class);
    getMovieDetails.buildUseCaseObservable(null);
  }
}
