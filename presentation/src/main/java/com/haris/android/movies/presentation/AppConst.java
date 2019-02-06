package com.haris.android.movies.presentation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by DELL on 9/19/2017.
 */

public class AppConst {

    @IntDef({SelectionType.LIST_MOVIES, SelectionType.FILTER_MOVIES, SelectionType.POPULAR_MOVIES, SelectionType.TOP_MOVIES, SelectionType.SEARCH_MOVIES})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SelectionType {
        int LIST_MOVIES = 0;
        int FILTER_MOVIES = 1;
        int POPULAR_MOVIES = 2;
        int TOP_MOVIES = 3;
        int SEARCH_MOVIES = 4;
    }
}
