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
package com.haris.android.movies.data.net.retrofit;

import com.haris.android.movies.data.entity.MovieEntity;
import com.haris.android.movies.data.entity.Response;
import com.haris.android.movies.data.exception.NetworkConnectionException;
import com.haris.android.movies.data.net.RestApi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RetrofitRestApiImpl implements RestApi {

    private Retrofit retrofit;
    private OkHttpClient.Builder builder;
    private MovieRetrofitApi movieRetrofitApi;
    private static final long DEFAULT_TIMEOUT = 5;

    public RetrofitRestApiImpl() {
        builder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();

                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("api_key", RestApi.API_KEY)
                            .build();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder().url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                });

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(simpleCallAdapter)
                .baseUrl(RestApi.API_BASE_URL)
                .build();

        movieRetrofitApi = retrofit.create(MovieRetrofitApi.class);
    }

    private final CallAdapter.Factory simpleCallAdapter = new CallAdapter.Factory() {
        @Override
        public CallAdapter<Object, Object> get(final Type returnType, Annotation[] annotations, Retrofit retrofit) {
            // if returnType is retrofit2.Call, do nothing
            if (returnType.toString().contains("retrofit2.Call")) {
                return null;
            }

            return new CallAdapter<Object, Object>() {
                @Override
                public Type responseType() {
                    return returnType;
                }

                @Override
                public Object adapt(Call<Object> call) {
                    try {
                        return call.execute().body();
                    } catch (Exception e) {
                        throw new RuntimeException(e); // do something better
                    }
                }
            };
        }
    };

    @Override
    public Observable<Response<List<MovieEntity>>> movieEntityList(int page) {
        return Observable.create(emitter -> {
            try {
                Response<List<MovieEntity>> response = getMovieListFromApi(page);
                if (response != null) {
                    emitter.onNext(response);
                    emitter.onComplete();
                } else {
                    emitter.onError(new NetworkConnectionException());
                }
            } catch (Exception e) {
                emitter.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Response<List<MovieEntity>>> popularMovieEntityList(int page) {
        return Observable.create(emitter -> {
            try {
                Response<List<MovieEntity>> response = getPopularMovieListFromApi(page);
                if (response != null) {
                    emitter.onNext(response);
                    emitter.onComplete();
                } else {
                    emitter.onError(new NetworkConnectionException());
                }
            } catch (Exception e) {
                emitter.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Response<List<MovieEntity>>> topMovieEntityList(int page) {
        return Observable.create(emitter -> {
            try {
                Response<List<MovieEntity>> response = getTopMovieListFromApi(page);
                if (response != null) {
                    emitter.onNext(response);
                    emitter.onComplete();
                } else {
                    emitter.onError(new NetworkConnectionException());
                }
            } catch (Exception e) {
                emitter.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }


    @Override
    public Observable<Response<List<MovieEntity>>> movieEntityFilterList(int page, String minYear, String maxYear) {
        return Observable.create(emitter -> {
            try {
                Response<List<MovieEntity>> response = getMovieFilterListFromApi(page, minYear, maxYear);
                if (response != null) {
                    emitter.onNext(response);
                    emitter.onComplete();
                } else {
                    emitter.onError(new NetworkConnectionException());
                }
            } catch (Exception e) {
                emitter.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<MovieEntity> movieEntityById(int movieId) {
        return Observable.create(emitter -> {
            try {
                MovieEntity response = getMovieDetailsFromApi(movieId);
                if (response != null) {
                    emitter.onNext(response);
                    emitter.onComplete();
                } else {
                    emitter.onError(new NetworkConnectionException());
                }
            } catch (Exception e) {
                emitter.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Response<List<MovieEntity>>> searchMovieEntityList(String query) {
        return Observable.create(emitter -> {
            try {
                Response<List<MovieEntity>> response = searchMoviesFromApi(query);
                if (response != null) {
                    emitter.onNext(response);
                    emitter.onComplete();
                } else {
                    emitter.onError(new NetworkConnectionException());
                }
            } catch (Exception e) {
                emitter.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    private Response<List<MovieEntity>> getMovieListFromApi(int page) {
        return movieRetrofitApi.getMovieList(page);
    }

    private Response<List<MovieEntity>> getPopularMovieListFromApi(int page) {
        return movieRetrofitApi.getPopularMovieList(page);
    }

    private Response<List<MovieEntity>> getTopMovieListFromApi(int page) {
        return movieRetrofitApi.getTopMovieList(page);
    }

    private Response<List<MovieEntity>> getMovieFilterListFromApi(int page, String minYear, String maxYear) {
        return movieRetrofitApi.filterMovieList(page, minYear, maxYear);
    }

    private MovieEntity getMovieDetailsFromApi(int movieId) {
        return movieRetrofitApi.getMovieDetails(movieId);
    }

    private Response<List<MovieEntity>> searchMoviesFromApi(String query) {
        return movieRetrofitApi.searchMovies(query);
    }

}
