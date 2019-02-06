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
package com.haris.android.movies.domain.interactor;

import com.fernandocejas.arrow.checks.Preconditions;
import com.haris.android.movies.domain.Movie;
import com.haris.android.movies.domain.executor.PostExecutionThread;
import com.haris.android.movies.domain.executor.ThreadExecutor;
import com.haris.android.movies.domain.repository.MovieRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving data related to an specific {@link Movie}.
 */
public class GetMovieDetails extends UseCase<Movie, GetMovieDetails.Params> {

  private final MovieRepository movieRepository;

  @Inject
  GetMovieDetails(MovieRepository movieRepository, ThreadExecutor threadExecutor,
                  PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.movieRepository = movieRepository;
  }

  @Override Observable<Movie> buildUseCaseObservable(Params params) {
    Preconditions.checkNotNull(params);
    return this.movieRepository.movie(params.movieId);
  }

  public static final class Params {

    private final int movieId;

    private Params(int movieId) {
      this.movieId = movieId;
    }

    public static Params forMovie(int movieId) {
      return new Params(movieId);
    }
  }
}
