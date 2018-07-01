package com.abrarkotwal.moviezone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abrarkotwal.moviezone.Activity.SingleMovieDisplayActivity;
import com.abrarkotwal.moviezone.Adapter.Pojo.Movie;
import com.abrarkotwal.moviezone.DB.Api;
import com.abrarkotwal.moviezone.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private Context context;
    private String checkAdult;

    public MoviesAdapter(Context context, List<Movie> movies) {
        this.movies = movies;
        this.context = context;
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_movie_display, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {

        final Movie currentMovie = movies.get(position);
        holder.movieTitle.setText(currentMovie.getTitle());
        holder.movieReleaseDate.setText(currentMovie.getReleaseDate());

        if (currentMovie.isAdult()){
            checkAdult = "(A)";
        }else {
            checkAdult = "(U/A)";
        }

        holder.movieAdault.setText(checkAdult);

        Glide.with(context).load(Api.IMG_DISP_URL+currentMovie.getPosterPath()).into(holder.moviePoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleMovieDisplayActivity.class);
                intent.putExtra("id",currentMovie.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        TextView movieTitle,movieReleaseDate,movieAdault;


        public MovieViewHolder(View itemView) {
            super(itemView);
            moviePoster         =  itemView.findViewById(R.id.moviePoster);
            movieTitle          =  itemView.findViewById(R.id.movieTitle);
            movieReleaseDate    =  itemView.findViewById(R.id.movieReleaseDate);
            movieAdault         =  itemView.findViewById(R.id.movieAdault);
        }
    }

}
