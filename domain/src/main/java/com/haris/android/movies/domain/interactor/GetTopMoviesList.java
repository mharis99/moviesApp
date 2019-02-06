package com.haris.android.movies.domain.interactor;

import com.haris.android.movies.domain.executor.ThreadExecutor;
import com.haris.android.movies.domain.repository.MovieRepository;
import com.haris.android.movies.domain.Movie;
import com.haris.android.movies.domain.executor.PostExecutionThread;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by DELL on 9/19/2017.
 */
public class GetTopMoviesList extends UseCase<List<Movie>, GetTopMoviesList.Params> {

    private final MovieRepository movieRepository;

    @Inject
    GetTopMoviesList(MovieRepository movieRepository, ThreadExecutor threadExecutor,
                     PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.movieRepository = movieRepository;
    }

    @Override
    Observable<List<Movie>> buildUseCaseObservable(GetTopMoviesList.Params params) {
        return this.movieRepository.topMovies(params.page);
    }

    public static final class Params {

        private final int page;

        private Params(int page) {
            this.page = page;
        }

        public static GetTopMoviesList.Params forList(int page) {
            return new GetTopMoviesList.Params(page);
        }
    }
}
