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
package com.haris.android.movies.test.mapper;

import com.haris.android.movies.domain.Movie;
import com.haris.android.movies.presentation.mapper.MovieModelDataMapper;
import com.haris.android.movies.presentation.model.MovieModel;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class MovieModelDataMapperTest extends TestCase {

  private static final int FAKE_MOVIE_ID = 123;
  private static final String FAKE_TITLE = "Inception";

  private MovieModelDataMapper movieModelDataMapper;

  @Override protected void setUp() throws Exception {
    super.setUp();
    movieModelDataMapper = new MovieModelDataMapper();
  }

  public void testTransformMovie() {
    Movie movie = createFakeMovie();
    MovieModel movieModel = movieModelDataMapper.transform(movie);

    assertThat(movieModel, is(instanceOf(MovieModel.class)));
    assertThat(movieModel.getMovieId(), is(FAKE_MOVIE_ID));
    assertThat(movieModel.getTitle(), is(FAKE_TITLE));
  }

  public void testTransformMovieCollection() {
    Movie mockMovieOne = mock(Movie.class);
    Movie mockMovieTwo = mock(Movie.class);

    List<Movie> movieList = new ArrayList<Movie>(5);
    movieList.add(mockMovieOne);
    movieList.add(mockMovieTwo);

    Collection<MovieModel> movieModelList = movieModelDataMapper.transform(movieList);

    assertThat(movieModelList.toArray()[0], is(instanceOf(MovieModel.class)));
    assertThat(movieModelList.toArray()[1], is(instanceOf(MovieModel.class)));
    assertThat(movieModelList.size(), is(2));
  }

  private Movie createFakeMovie() {
    Movie movie = new Movie(FAKE_MOVIE_ID);
    movie.setTitle(FAKE_TITLE);

    return movie;
  }
}
