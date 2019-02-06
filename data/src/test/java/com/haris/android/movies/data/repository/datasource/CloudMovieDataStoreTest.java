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
package com.haris.android.movies.data.repository.datasource;

import com.haris.android.movies.data.cache.MovieCache;
import com.haris.android.movies.data.entity.MovieEntity;
import com.haris.android.movies.data.entity.Response;
import com.haris.android.movies.data.net.RestApi;
import com.haris.android.movies.data.repository.datasource.CloudMovieDataStore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CloudMovieDataStoreTest {

    private static final int FAKE_MOVIE_ID = 765;
    private static final int PAGE = 1;

    private CloudMovieDataStore cloudMovieDataStore;

    @Mock
    private RestApi mockRestApi;
    @Mock
    private MovieCache mockMovieCache;

    @Before
    public void setUp() {
        cloudMovieDataStore = new CloudMovieDataStore(mockRestApi, mockMovieCache);
    }

    @Test
    public void testGetMovieEntityListFromApi() {
        when(mockRestApi.movieEntityList(PAGE)).thenReturn(getMovieEntityListResponse());
        cloudMovieDataStore.movieEntityList(PAGE);
        verify(mockRestApi).movieEntityList(PAGE);
    }

    @Test
    public void testGetMovieEntityDetailsFromApi() {
        MovieEntity fakeMovieEntity = new MovieEntity();
        Observable<MovieEntity> fakeObservable = Observable.just(fakeMovieEntity);
        given(mockRestApi.movieEntityById(FAKE_MOVIE_ID)).willReturn(fakeObservable);

        cloudMovieDataStore.movieEntityDetails(FAKE_MOVIE_ID);

        verify(mockRestApi).movieEntityById(FAKE_MOVIE_ID);
    }

    private Observable<Response<List<MovieEntity>>> getMovieEntityListResponse() {
        List<MovieEntity> movieEntityList = new ArrayList<>();
        Response<List<MovieEntity>> response = new Response<>();
        response.setData(movieEntityList);

        return Observable.fromArray(response);
    }
}
