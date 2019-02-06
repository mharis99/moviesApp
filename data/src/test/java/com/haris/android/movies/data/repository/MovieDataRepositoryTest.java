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
package com.haris.android.movies.data.repository;

import com.haris.android.movies.data.entity.MovieEntity;
import com.haris.android.movies.data.entity.mapper.MovieEntityDataMapper;
import com.haris.android.movies.data.repository.datasource.MovieDataStore;
import com.haris.android.movies.data.repository.datasource.MovieDataStoreFactory;
import com.haris.android.movies.domain.Movie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MovieDataRepositoryTest {

  private static final int FAKE_MOVIE_ID = 123;
  private static final int PAGE = 1;

  private MovieDataRepository movieDataRepository;

  @Mock private MovieDataStoreFactory mockMovieDataStoreFactory;
  @Mock private MovieEntityDataMapper mockMovieEntityDataMapper;
  @Mock private MovieDataStore mockMovieDataStore;
  @Mock private MovieEntity mockMovieEntity;
  @Mock private Movie mockMovie;

  @Before
  public void setUp() {
    movieDataRepository = new MovieDataRepository(mockMovieDataStoreFactory, mockMovieEntityDataMapper);
    given(mockMovieDataStoreFactory.create(anyInt())).willReturn(mockMovieDataStore);
    given(mockMovieDataStoreFactory.createCloudDataStore()).willReturn(mockMovieDataStore);
  }

  @Test
  public void testGetMoviesHappyCase() {
    List<MovieEntity> MoviesList = new ArrayList<>();
    MoviesList.add(new MovieEntity());
    given(mockMovieDataStore.movieEntityList(PAGE)).willReturn(Observable.just(MoviesList));

    movieDataRepository.movies(PAGE);

    verify(mockMovieDataStoreFactory).createCloudDataStore();
    verify(mockMovieDataStore).movieEntityList(PAGE);
  }

  @Test
  public void testGetMovieHappyCase() {
    MovieEntity movieEntity = new MovieEntity();
    given(mockMovieDataStore.movieEntityDetails(FAKE_MOVIE_ID)).willReturn(Observable.just(movieEntity));
    movieDataRepository.movie(FAKE_MOVIE_ID);

    verify(mockMovieDataStoreFactory).create(FAKE_MOVIE_ID);
    verify(mockMovieDataStore).movieEntityDetails(FAKE_MOVIE_ID);
  }
}
