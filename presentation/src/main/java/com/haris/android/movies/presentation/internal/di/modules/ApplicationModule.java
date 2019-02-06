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
package com.haris.android.movies.presentation.internal.di.modules;

import android.content.Context;

import com.haris.android.movies.data.cache.MovieCache;
import com.haris.android.movies.data.cache.MovieCacheImpl;
import com.haris.android.movies.data.executor.JobExecutor;
import com.haris.android.movies.data.repository.MovieDataRepository;
import com.haris.android.movies.domain.executor.PostExecutionThread;
import com.haris.android.movies.domain.executor.ThreadExecutor;
import com.haris.android.movies.domain.repository.MovieRepository;
import com.haris.android.movies.presentation.AndroidApplication;
import com.haris.android.movies.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides @Singleton Context provideApplicationContext() {
        return this.application;
    }

    @Provides @Singleton ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton
    MovieCache provideMovieCache(MovieCacheImpl MovieCache) {
        return MovieCache;
    }

    @Provides @Singleton
    MovieRepository provideMovieRepository(MovieDataRepository MovieDataRepository) {
        return MovieDataRepository;
    }
}
