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
package com.haris.android.movies.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import com.haris.android.movies.data.cache.MovieCache;
import com.haris.android.movies.data.net.RestApi;
import com.haris.android.movies.data.net.retrofit.RetrofitRestApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link MovieDataStore}.
 */
@Singleton
public class MovieDataStoreFactory {

    private final Context context;
    private final MovieCache movieCache;

    @Inject
    MovieDataStoreFactory(@NonNull Context context, @NonNull MovieCache movieCache) {
        this.context = context.getApplicationContext();
        this.movieCache = movieCache;
    }

    /**
     * Create {@link MovieDataStore} from a movie id.
     */
    public MovieDataStore create(int movieId) {
        MovieDataStore movieDataStore;

        if (!this.movieCache.isExpired() && this.movieCache.isCached(movieId)) {
            movieDataStore = new DiskMovieDataStore(this.movieCache);
        } else {
            movieDataStore = createCloudDataStore();
        }

        return movieDataStore;
    }

    /**
     * Create {@link MovieDataStore} to retrieve data from the Cloud.
     */
    public MovieDataStore createCloudDataStore() {
        final RestApi restApi = new RetrofitRestApiImpl();
        return new CloudMovieDataStore(restApi, this.movieCache);
    }
}
