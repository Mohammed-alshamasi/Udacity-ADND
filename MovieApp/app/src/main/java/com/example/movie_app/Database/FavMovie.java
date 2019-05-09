package com.example.movie_app.Database;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.movie_app.model.Movie;

public class FavMovie {

    private boolean markAsFavorite = true;
    private Database database;

    public FavMovie (Context context) {
        database = Database.getDatabase(context);
    }

    public void insertFavMovie (Movie movie){
        markAsFavorite = true;
        new FavoriteMoviesAsyncTask ().execute(movie);
    }

    public void deleteFavMovie (Movie movie){
        markAsFavorite = false;
        new FavoriteMoviesAsyncTask ().execute(movie);
    }


    private class FavoriteMoviesAsyncTask extends AsyncTask <Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movie) {
            if (markAsFavorite){

                database.moviesDAO().insertMovie(movie[0]);
            } else {
                database.moviesDAO().deleteMovie(movie[0].getImage());
            }
            return null;
        }
    }


}

