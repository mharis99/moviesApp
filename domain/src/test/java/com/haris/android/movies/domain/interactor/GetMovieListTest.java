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

import com.haris.android.movies.domain.executor.PostExecutionThread;
import com.haris.android.movies.domain.executor.ThreadExecutor;
import com.haris.android.movies.domain.interactor.GetMovieList;
import com.haris.android.movies.domain.repository.MovieRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetMovieListTest {

    private static final int PAGE = 1;
    private GetMovieList getMovieList;

    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;
    @Mock
    private MovieRepository mockMovieRepository;

    @Before
    public void setUp() {
        getMovieList = new GetMovieList(mockMovieRepository, mockThreadExecutor,
                mockPostExecutionThread);
    }

    @Test
    public void testGetMovieListUseCaseObservableHappyCase() {
        getMovieList.buildUseCaseObservable(GetMovieList.Params.forList(PAGE));

        verify(mockMovieRepository).movies(PAGE);
        verifyNoMoreInteractions(mockMovieRepository);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);
    }
}
