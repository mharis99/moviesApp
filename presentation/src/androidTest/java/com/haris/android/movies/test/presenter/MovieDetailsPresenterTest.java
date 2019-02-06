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
package com.haris.android.movies.test.presenter;

import android.content.Context;

import com.haris.android.movies.domain.interactor.GetMovieDetails;
import com.haris.android.movies.domain.interactor.GetMovieDetails.Params;
import com.haris.android.movies.presentation.mapper.MovieModelDataMapper;
import com.haris.android.movies.presentation.presenter.MovieDetailsPresenter;
import com.haris.android.movies.presentation.view.MovieDetailsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MovieDetailsPresenterTest {

  private static final int MOVIE_ID = 1;

  private MovieDetailsPresenter movieDetailsPresenter;

  @Mock private Context mockContext;
  @Mock private MovieDetailsView mockMovieDetailsView;
  @Mock private GetMovieDetails mockGetMovieDetails;
  @Mock private MovieModelDataMapper mockMovieModelDataMapper;

  @Before
  public void setUp() {
    movieDetailsPresenter = new MovieDetailsPresenter(mockGetMovieDetails, mockMovieModelDataMapper);
    movieDetailsPresenter.setView(mockMovieDetailsView);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testMovieDetailsPresenterInitialize() {
    given(mockMovieDetailsView.context()).willReturn(mockContext);

    movieDetailsPresenter.initialize(MOVIE_ID);

    verify(mockMovieDetailsView).hideRetry();
    verify(mockMovieDetailsView).showLoading();
    verify(mockGetMovieDetails).execute(any(DisposableObserver.class), any(Params.class));
  }
}
