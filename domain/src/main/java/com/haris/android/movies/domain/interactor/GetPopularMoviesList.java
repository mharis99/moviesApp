package com.haris.android.movies.domain.interactor;

import com.haris.android.movies.domain.Movie;
import com.haris.android.movies.domain.executor.ThreadExecutor;
import com.haris.android.movies.domain.repository.MovieRepository;
import com.haris.android.movies.domain.executor.PostExecutionThread;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by DELL on 9/18/2017.
 */

public class GetPopularMoviesList extends UseCase<List<Movie>, GetPopularMoviesList.Params> {

    private final MovieRepository movieRepository;

    @Inject
    GetPopularMoviesList(MovieRepository movieRepository, ThreadExecutor threadExecutor,
                 PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.movieRepository = movieRepository;
    }

    @Override
    Observable<List<Movie>> buildUseCaseObservable(GetPopularMoviesList.Params params) {
        return this.movieRepository.popularMovies(params.page);
    }

    public static final class Params {

        private final int page;

        private Params(int page) {
            this.page = page;
        }

        public static GetPopularMoviesList.Params forList(int page) {
            return new GetPopularMoviesList.Params(page);
        }
    }
}
