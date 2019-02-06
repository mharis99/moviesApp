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
package com.haris.android.movies.data.entity.mapper;

import com.google.gson.JsonSyntaxException;
import com.haris.android.movies.data.entity.MovieEntity;
import com.haris.android.movies.data.entity.mapper.MovieEntityJsonMapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class MovieEntityJsonMapperTest {

    private static final String JSON_RESPONSE_MOVIE_DETAILS = "{\n" +
            "    adult: false,\n" +
            "    backdrop_path: \"/p0OQNh9Sdg7EqKQyWYOG3skGAa2.jpg\",\n" +
            "    belongs_to_collection: null,\n" +
            "    budget: 40000000,\n" +
            "    homepage: \"\",\n" +
            "    id: 1,\n" +
            "    imdb_id: \"tt0109456\",\n" +
            "    original_language: \"en\",\n" +
            "    original_title: \"Color of Night\",\n" +
            "    overview: \"When New York psychiatrist Bill Capa visits Los Angeles to take over his murdered colleague's therapy group, he finds himself embroiled in the thick of a mystery when he bumps into Rose and begins a torrid affair.\",\n" +
            "    popularity: 2.350572,\n" +
            "    poster_path: \"/qvNWTJ8m65DDGr8olt1o39OQVkH.jpg\",\n" +
            "    status: \"Released\",\n" +
            "    tagline: \"In the heat of desire, love can turn to deception. Nothing is what it seems when day turns into night.\",\n" +
            "    title: \"Color of Night\",\n" +
            "    video: false,\n" +
            "    vote_average: 5.4,\n" +
            "    vote_count: 108\n" +
            "}";

    private static final String JSON_RESPONSE_MOVIE_COLLECTION = "[\n" +
            "    {\n" +
            "        vote_count: 2124,\n" +
            "        id: 1,\n" +
            "        video: false,\n" +
            "        vote_average: 7.4,\n" +
            "        title: \"Spider-Man: Homecoming\",\n" +
            "        popularity: 141.111286,\n" +
            "        poster_path: \"/c24sv2weTHPsmDa7jEMN0m2P3RT.jpg\",\n" +
            "        original_language: \"en\",\n" +
            "        original_title: \"Spider-Man: Homecoming\",\n" +
            "        backdrop_path: \"/vc8bCGjdVp0UbMNLzHnHSLRbBWQ.jpg\",\n" +
            "        adult: false,\n" +
            "        overview: \"Following the events of Captain America: Civil War, Peter Parker, with the help of his mentor Tony Stark, tries to balance his life as an ordinary high school student in Queens, New York City, with fighting crime as his superhero alter ego Spider-Man as a new threat, the Vulture, emerges.\",\n" +
            "        release_date: \"2017-07-05\"\n" +
            "    },\n" +
            "    {\n" +
            "        vote_count: 4473,\n" +
            "        id: 2,\n" +
            "        video: false,\n" +
            "        vote_average: 6.8,\n" +
            "        title: \"Beauty and the Beast\",\n" +
            "        popularity: 111.7385,\n" +
            "        poster_path: \"/tWqifoYuwLETmmasnGHO7xBjEtt.jpg\",\n" +
            "        original_language: \"en\",\n" +
            "        original_title: \"Beauty and the Beast\",\n" +
            "        backdrop_path: \"/6aUWe0GSl69wMTSWWexsorMIvwU.jpg\",\n" +
            "        adult: false,\n" +
            "        overview: \"A live-action adaptation of Disney's version of the classic 'Beauty and the Beast' tale of a cursed prince and a beautiful young woman who helps him break the spell.\",\n" +
            "        release_date: \"2017-03-16\"\n" +
            "    }\n" +
            "]";

    private MovieEntityJsonMapper movieEntityJsonMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        movieEntityJsonMapper = new MovieEntityJsonMapper();
    }

    @Test
    public void testTransformMovieEntityHappyCase() {
        MovieEntity movieEntity = movieEntityJsonMapper.transformMovieEntity(JSON_RESPONSE_MOVIE_DETAILS);

        assertThat(movieEntity.getMovieId(), is(1));
        assertThat(movieEntity.getTitle(), is(equalTo("Color of Night")));
    }

    @Test
    public void testTransformMovieEntityCollectionHappyCase() {
        Collection<MovieEntity> movieEntityCollection =
                movieEntityJsonMapper.transformMovieEntityCollection(
                        JSON_RESPONSE_MOVIE_COLLECTION);

        assertThat(((MovieEntity) movieEntityCollection.toArray()[0]).getMovieId(), is(1));
        assertThat(((MovieEntity) movieEntityCollection.toArray()[1]).getTitle(), is("Beauty and the Beast"));
        assertThat(movieEntityCollection.size(), is(2));
    }

    @Test
    public void testTransformMovieEntityNotValidResponse() {
        expectedException.expect(JsonSyntaxException.class);
        movieEntityJsonMapper.transformMovieEntity("ironman");
    }

    @Test
    public void testTransformMovieEntityCollectionNotValidResponse() {
        expectedException.expect(JsonSyntaxException.class);
        movieEntityJsonMapper.transformMovieEntityCollection("Tony Stark");
    }
}
