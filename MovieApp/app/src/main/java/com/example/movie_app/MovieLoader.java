package com.example.movie_app;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.movie_app.Utils.JsonUtils;
import com.example.movie_app.Utils.NetworkUtils;
import com.example.movie_app.model.Movie;

import java.util.ArrayList;

public class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    String linkUrl;

    public MovieLoader(Context context, String url) {
        super(context);
        linkUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        String response = NetworkUtils.fetchResponse(linkUrl);
        return JsonUtils.getMovieList(response);
    }
}
