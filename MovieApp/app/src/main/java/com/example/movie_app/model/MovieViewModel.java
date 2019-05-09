package com.example.movie_app.model;
;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.example.movie_app.Database.Database;
import com.example.movie_app.Utils.JsonUtils;
import com.example.movie_app.Utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;


public class MovieViewModel extends AndroidViewModel{

    Database database;
    MutableLiveData<List<Movie>> movieLiveData = new MutableLiveData<>();

    public MovieViewModel (Application application) {
        super (application);
        database = Database.getDatabase(application);
    }

    public LiveData <List<Movie>> getMovies (boolean fav, String url) {

        if (fav){
            new FavoriteAsyncTask().execute();
            return movieLiveData;
        } else {
            new MoviesfromServerAsyncTask().execute(url);
            return movieLiveData;
        }
    }


    public class MoviesfromServerAsyncTask extends AsyncTask <String, Void, Void> {

        @Override
        protected Void doInBackground(String... url) {
            String response = NetworkUtils.fetchResponse(url[0]);
            List <Movie> movies =  JsonUtils.getMovieList(response);
            movieLiveData.postValue(movies);
            return null;
        }

    }

    public class FavoriteAsyncTask extends AsyncTask <Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            movieLiveData.postValue(database.moviesDAO().getAllMovies());
            return null;
        }

    }

}
