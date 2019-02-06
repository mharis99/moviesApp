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
package com.haris.android.movies.data.entity.mapper;

import com.haris.android.movies.data.entity.MovieEntity;
import com.haris.android.movies.domain.Movie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MovieEntityDataMapperTest {

  private static final int FAKE_MOVIE_ID = 123;
  private static final String FAKE_FULLNAME = "Tony Stark";

  private MovieEntityDataMapper movieEntityDataMapper;

  @Before
  public void setUp() throws Exception {
    movieEntityDataMapper = new MovieEntityDataMapper();
  }

  @Test
  public void testTransformMovieEntity() {
    MovieEntity movieEntity = createFakeMovieEntity();
    Movie movie = movieEntityDataMapper.transform(movieEntity);

    assertThat(movie, is(instanceOf(Movie.class)));
    assertThat(movie.getMovieId(), is(FAKE_MOVIE_ID));
    assertThat(movie.getTitle(), is(FAKE_FULLNAME));
  }

  @Test
  public void testTransformMovieEntityCollection() {
    MovieEntity mockMovieEntityOne = mock(MovieEntity.class);
    MovieEntity mockMovieEntityTwo = mock(MovieEntity.class);

    List<MovieEntity> movieEntityList = new ArrayList<MovieEntity>(5);
    movieEntityList.add(mockMovieEntityOne);
    movieEntityList.add(mockMovieEntityTwo);

    Collection<Movie> movieCollection = movieEntityDataMapper.transform(movieEntityList);

    assertThat(movieCollection.toArray()[0], is(instanceOf(Movie.class)));
    assertThat(movieCollection.toArray()[1], is(instanceOf(Movie.class)));
    assertThat(movieCollection.size(), is(2));
  }

  private MovieEntity createFakeMovieEntity() {
    MovieEntity movieEntity = new MovieEntity();
    movieEntity.setMovieId(FAKE_MOVIE_ID);
    movieEntity.setTitle(FAKE_FULLNAME);

    return movieEntity;
  }
}
