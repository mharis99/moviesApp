/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.haris.android.movies.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haris.android.movies.presentation.model.MovieModel;
import com.squareup.picasso.Picasso;
import com.haris.android.movies.presentation.R;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adaptar that manages a collection of {@link MovieModel}.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    public interface OnItemClickListener {
        void onMovieItemClicked(MovieModel movieModel);
    }

    private Context context;
    private List<MovieModel> moviesCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    @Inject
    MoviesAdapter(Context context) {
        this.context = context;
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.moviesCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.moviesCollection != null) ? this.moviesCollection.size() : 0;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        final MovieModel movieModel = this.moviesCollection.get(position);
        holder.textViewTitle.setText(movieModel.getTitle());
        Picasso.with(context).load(movieModel.getBackdropPath()).into(holder.imageViewAvatar);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoviesAdapter.this.onItemClickListener != null) {
                    MoviesAdapter.this.onItemClickListener.onMovieItemClicked(movieModel);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setMoviesCollection(Collection<MovieModel> MoviesCollection) {
        this.validateMoviesCollection(MoviesCollection);
        this.moviesCollection = (List<MovieModel>) MoviesCollection;
        this.notifyDataSetChanged();
    }

    public void addMoviesCollection(Collection<MovieModel> moviesCollection) {
        this.validateMoviesCollection(moviesCollection);
        this.moviesCollection.addAll(this.moviesCollection.size(), moviesCollection);
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateMoviesCollection(Collection<MovieModel> MoviesCollection) {
        if (MoviesCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView textViewTitle;
        @Bind(R.id.avatar)
        ImageView imageViewAvatar;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
