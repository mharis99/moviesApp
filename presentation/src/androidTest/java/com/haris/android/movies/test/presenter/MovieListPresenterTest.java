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
package com.haris.android.movies.test.presenter;

import android.content.Context;

import com.haris.android.movies.domain.interactor.FilterMovieList;
import com.haris.android.movies.domain.interactor.GetMovieList;
import com.haris.android.movies.domain.interactor.GetPopularMoviesList;
import com.haris.android.movies.domain.interactor.GetTopMoviesList;
import com.haris.android.movies.presentation.mapper.MovieModelDataMapper;
import com.haris.android.movies.presentation.presenter.MovieListPresenter;
import com.haris.android.movies.presentation.view.MovieListView;

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
public class MovieListPresenterTest {

    private MovieListPresenter movieListPresenter;

    @Mock
    private Context mockContext;
    @Mock
    private MovieListView mockMovieListView;
    @Mock
    private GetMovieList mockGetMovieList;
    @Mock
    private FilterMovieList mockFilterMovieList;
    @Mock
    private GetPopularMoviesList mockPopularMovieList;
    @Mock
    private GetTopMoviesList mockTopMovieList;
    @Mock
    private MovieModelDataMapper mockMovieModelDataMapper;

    @Before
    public void setUp() {
        movieListPresenter = new MovieListPresenter(mockGetMovieList, mockFilterMovieList,mockPopularMovieList,mockTopMovieList, mockMovieModelDataMapper);
        movieListPresenter.setView(mockMovieListView);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testMovieListPresenterInitialize() {
        given(mockMovieListView.context()).willReturn(mockContext);

        movieListPresenter.initialize();

        verify(mockMovieListView).hideRetry();
        verify(mockMovieListView).showLoading();
        verify(mockGetMovieList).execute(any(DisposableObserver.class), any(GetMovieList.Params.class));
    }
}
