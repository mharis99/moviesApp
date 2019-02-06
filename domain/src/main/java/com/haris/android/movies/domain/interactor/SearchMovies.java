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

package com.haris.android.movies.domain.interactor;

import com.haris.android.movies.domain.repository.MovieRepository;
import com.haris.android.movies.domain.Movie;
import com.haris.android.movies.domain.executor.PostExecutionThread;
import com.haris.android.movies.domain.executor.ThreadExecutor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Movie}.
 */
public class SearchMovies extends UseCase<List<Movie>, SearchMovies.Params> {

    private final MovieRepository movieRepository;

    @Inject
    SearchMovies(
            MovieRepository movieRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.movieRepository = movieRepository;
    }

    @Override
    Observable<List<Movie>> buildUseCaseObservable(Params params) {
        return this.movieRepository.searchMovies(params.query);
    }

    public static final class Params {
        private final String query;

        private Params(String query) {
            this.query = query;
        }

        public static SearchMovies.Params forSearch(String query) {
            return new SearchMovies.Params(query);
        }
    }
}
