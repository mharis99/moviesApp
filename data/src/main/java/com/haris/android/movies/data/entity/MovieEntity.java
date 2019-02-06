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
package com.haris.android.movies.data.entity;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Movie Entity used in the data layer.
 */
public class MovieEntity {

  @SerializedName("id")
  private int movieId;

  @SerializedName("poster_path")
  @Nullable
  private String posterPath;

  @SerializedName("adult")
  private boolean adult;

  @SerializedName("overview")
  @NotNull
  private String overview;

  @SerializedName("description")
  @NotNull
  private String description;

  @SerializedName("release_date")
  @NotNull
  private String releaseDate;

  @SerializedName("genre_ids")
  @NotNull
  private List<Integer> genreIds;

  @SerializedName("original_title")
  @NotNull
  private String originalTitle;

  @SerializedName("original_language")
  @NotNull
  private String originalLanguage;

  @SerializedName("title")
  @NotNull
  private String title;

  @SerializedName("backdrop_path")
  @Nullable
  private String backdropPath;

  @SerializedName("popularity")
  private float popularity;

  @SerializedName("vote_count")
  private int voteCount;

  @SerializedName("video")
  private boolean video;

  @SerializedName("vote_average")
  private float voteAverage;



  public MovieEntity() {
    //empty
  }

  public int getMovieId() {
    return movieId;
  }

  @Nullable
  public String getPosterPath() {
    return posterPath;
  }

  public boolean isAdult() {
    return adult;
  }

  @NotNull
  public String getOverview() {
    return overview;
  }

  @NotNull
  public String getDescription() {
    return description;
  }

  @NotNull
  public String getReleaseDate() {
    return releaseDate;
  }

  @NotNull
  public List<Integer> getGenreIds() {
    return genreIds;
  }

  @NotNull
  public String getOriginalTitle() {
    return originalTitle;
  }

  @NotNull
  public String getOriginalLanguage() {
    return originalLanguage;
  }

  @NotNull
  public String getTitle() {
    return title;
  }

  @Nullable
  public String getBackdropPath() {
    return backdropPath;
  }

  public float getPopularity() {
    return popularity;
  }

  public int getVoteCount() {
    return voteCount;
  }

  public boolean isVideo() {
    return video;
  }

  public float getVoteAverage() {
    return voteAverage;
  }

  public void setMovieId(int movieId) {
    this.movieId = movieId;
  }

  public void setTitle(@NotNull String title) {
    this.title = title;
  }
}
