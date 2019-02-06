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
package com.haris.android.movies.data.cache.serializer;

import com.haris.android.movies.data.cache.serializer.Serializer;
import com.haris.android.movies.data.entity.MovieEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SerializerTest {

  private static final String JSON_RESPONSE = "{\n" +
          "vote_count: 2124,\n" +
          "id: 1,\n" +
          "video: false,\n" +
          "vote_average: 7.4,\n" +
          "title: \"Spider-Man: Homecoming\",\n" +
          "popularity: 141.111286,\n" +
          "poster_path: \"/c24sv2weTHPsmDa7jEMN0m2P3RT.jpg\",\n" +
          "original_title: \"Spider-Man: Homecoming\",\n" +
          "backdrop_path: \"/vc8bCGjdVp0UbMNLzHnHSLRbBWQ.jpg\",\n" +
          "adult: false,\n" +
          "overview: \"Following the events of Captain America: Civil War, Peter Parker, with the help of his mentor Tony Stark, tries to balance his life as an ordinary high school student in Queens, New York City, with fighting crime as his superhero alter ego Spider-Man as a new threat, the Vulture, emerges.\",\n" +
          "release_date: \"2017-07-05\"\n" +
          "}";

  private Serializer serializer;

  @Before
  public void setUp() {
    serializer = new Serializer();
  }

  @Test
  public void testSerializeHappyCase() {
    final MovieEntity movieEntityOne = serializer.deserialize(JSON_RESPONSE, MovieEntity.class);
    final String jsonString = serializer.serialize(movieEntityOne, MovieEntity.class);
    final MovieEntity movieEntityTwo = serializer.deserialize(jsonString, MovieEntity.class);

    assertThat(movieEntityOne.getMovieId(), is(movieEntityTwo.getMovieId()));
    assertThat(movieEntityOne.getTitle(), is(equalTo(movieEntityTwo.getTitle())));
  }

  @Test
  public void testDesearializeHappyCase() {
    final MovieEntity movieEntity = serializer.deserialize(JSON_RESPONSE, MovieEntity.class);

    assertThat(movieEntity.getMovieId(), is(1));
    assertThat(movieEntity.getTitle(), is("Spider-Man: Homecoming"));
  }
}
