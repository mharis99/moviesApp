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
package com.haris.android.movies.data.cache;

import com.haris.android.movies.data.entity.MovieEntity;

import io.reactivex.Observable;

/**
 * An interface representing a movie Cache.
 */
public interface MovieCache {
  /**
   * Gets an {@link Observable} which will emit a {@link MovieEntity}.
   *
   * @param movieId The movie id to retrieve data.
   */
  Observable<MovieEntity> get(final int movieId);

  /**
   * Puts and element into the cache.
   *
   * @param movieEntity Element to insert in the cache.
   */
  void put(MovieEntity movieEntity);

  /**
   * Checks if an element (Movie) exists in the cache.
   *
   * @param movieId The id used to look for inside the cache.
   * @return true if the element is cached, otherwise false.
   */
  boolean isCached(final int movieId);

  /**
   * Checks if the cache is expired.
   *
   * @return true, the cache is expired, otherwise false.
   */
  boolean isExpired();

  /**
   * Evict all elements of the cache.
   */
  void evictAll();
}
