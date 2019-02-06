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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.haris.android.movies.data.entity.MovieEntity;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class MovieEntityJsonMapper {

  private final Gson gson;

  @Inject
  public MovieEntityJsonMapper() {
    this.gson = new Gson();
  }

  /**
   * Transform from valid json string to {@link MovieEntity}.
   *
   * @param movieJsonResponse A json representing a movie profile.
   * @return {@link MovieEntity}.
   * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
   */
  public MovieEntity transformMovieEntity(String movieJsonResponse) throws JsonSyntaxException {
    final Type movieEntityType = new TypeToken<MovieEntity>() {}.getType();
    return this.gson.fromJson(movieJsonResponse, movieEntityType);
  }

  /**
   * Transform from valid json string to List of {@link MovieEntity}.
   *
   * @param movieListJsonResponse A json representing a collection of movies.
   * @return List of {@link MovieEntity}.
   * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
   */
  public List<MovieEntity> transformMovieEntityCollection(String movieListJsonResponse)
      throws JsonSyntaxException {
    final Type listOfMovieEntityType = new TypeToken<List<MovieEntity>>() {}.getType();
    return this.gson.fromJson(movieListJsonResponse, listOfMovieEntityType);
  }
}
