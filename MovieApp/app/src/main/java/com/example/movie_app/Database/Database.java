package com.example.movie_app.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.movie_app.model.Movie;

@android.arch.persistence.room.Database(entities = Movie.class, version = 2, exportSchema = false)


public abstract class Database extends RoomDatabase {

    public abstract  MoviesDAO moviesDAO ();

    private static Database database;

    public static Database getDatabase (Context context) {
        if (database == null){
            database = Room.databaseBuilder(context, Database.class, "FavoriteMovies")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return database;
    }
}
