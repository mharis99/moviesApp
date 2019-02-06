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

import com.haris.android.movies.domain.executor.ThreadExecutor;
import com.haris.android.movies.domain.repository.MovieRepository;
import com.haris.android.movies.domain.Movie;
import com.haris.android.movies.domain.executor.PostExecutionThread;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Movie}.
 */
public class FilterMovieList extends UseCase<List<Movie>, FilterMovieList.Params> {

    private final MovieRepository movieRepository;

    @Inject
    FilterMovieList(MovieRepository movieRepository, ThreadExecutor threadExecutor,
                    PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.movieRepository = movieRepository;
    }

    @Override
    Observable<List<Movie>> buildUseCaseObservable(FilterMovieList.Params params) {
        return this.movieRepository.filterMovies(params.page, params.minYear, params.maxYear);
    }

    public static final class Params {

        private final int page;
        private final String minYear;
        private final String maxYear;

        private Params(int page, String minYear, String maxYear) {
            this.page = page;
            this.minYear = minYear;
            this.maxYear = maxYear;
        }

        public static FilterMovieList.Params forFilter(int page, String minYear, String maxYear) {
            minYear = minYear + "-01-01"; // default to 1st Jan of that year
            maxYear = maxYear + "-01-01"; // default to 1st Jan of that year
            return new FilterMovieList.Params(page, minYear, maxYear);
        }
    }
}
