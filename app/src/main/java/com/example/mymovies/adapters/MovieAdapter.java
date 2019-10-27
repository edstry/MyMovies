package com.example.mymovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mymovies.R;
import com.example.mymovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>  {


    private List<Movie> movies;
    private onReachEndListener onReachEndListener;
    private onPosterClickListener onPosterClickListener;

    public MovieAdapter() {
        movies = new ArrayList<>();
    }

    public void setOnReachEndListener(MovieAdapter.onReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setOnPosterClickListener(MovieAdapter.onPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.moview_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    public void clear() {
        this.movies.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {

        if(movies.size() >=20 && i > movies.size()-4 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }

        Movie movie = movies.get(i);
        ImageView imageView = movieViewHolder.imageViewSmallPoster;
        Picasso.get().load(movie.getPosterPath())
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewSmallPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPoster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onPosterClickListener != null) {
                        onPosterClickListener.onPosterClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface onReachEndListener { // нужен для того чтобы подружать данные когда юзер дошёл до конца списка
        void onReachEnd();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void addMovies(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public interface onPosterClickListener {
        void onPosterClick(int position);
    }
}
