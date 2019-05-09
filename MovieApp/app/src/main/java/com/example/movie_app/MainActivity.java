package com.example.movie_app;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;




import android.net.Uri;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;



import com.example.movie_app.model.Movie;
import com.example.movie_app.model.MovieViewModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements  MovieAdapter.onClickDetail {
    public RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private MovieViewModel viewModel;

    private List<Movie> movies;

    public static Boolean highest_rated = false;
    public static Boolean popular = true;
    public static Boolean fav = false;

    private static final String API_KEY = BuildConfig.ApiKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);

        movieAdapter = new MovieAdapter(this, movies, this);

        recyclerView.setAdapter(movieAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);


            viewModel.getMovies(false, buildLink()).observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movieModels) {
                    movies.clear();
                    movies.addAll(movieModels);
                    movieAdapter.notifyDataSetChanged();
                }
            });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (fav) {
            viewModel.getMovies(true, "").observe(this, new Observer<List<Movie>>() {

                @Override
                public void onChanged(@Nullable List<Movie> movieModels) {
                    movies.clear();
                    if (movieModels != null) {
                        movies.addAll(movieModels);
                        movieAdapter.notifyDataSetChanged();
                    }
                }
            });
        } else
            viewModel.getMovies(false, buildLink()).observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movieModels) {
                    movies.clear();
                    movies.addAll(movieModels);
                    movieAdapter.notifyDataSetChanged();
                }
            });
    }

    private String buildLink() {

        final String baseUrl = "https://api.themoviedb.org/3/discover/movie";
        Uri uri = Uri.parse(baseUrl);
        Uri.Builder builder = uri.buildUpon();

        if (highest_rated) {
            String TopRated = "https://api.themoviedb.org/3/movie/top_rated";
            Uri uriTopRated = Uri.parse(TopRated);
            Uri.Builder builderTopRated = uriTopRated.buildUpon();
            builderTopRated.appendQueryParameter("api_key", API_KEY);
            return builderTopRated.toString();
        } else if (popular)
            builder.query("popular");

        builder.appendQueryParameter("api_key", API_KEY);
        return builder.toString();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int getId = item.getItemId();

        if (getId == R.id.highest_rated) {
            highest_rated = true;
            popular = false;
            fav = false;
            viewModel.getMovies(false, buildLink()).observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movieModels) {
                    movies.clear();
                    movies.addAll(movieModels);
                    movieAdapter.notifyDataSetChanged();
                }
            });
        } else if (getId == R.id.popular) {
            popular = true;
            highest_rated = false;
            fav = false;
            viewModel.getMovies(false, buildLink()).observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movieModels) {
                    movies.clear();
                    movies.addAll(movieModels);
                    movieAdapter.notifyDataSetChanged();
                }
            });

        } else if (getId == R.id.favourite) {
            popular = false;
            highest_rated = false;
            fav = true;
            if (fav) {
                viewModel.getMovies(true, "").observe(this, new Observer<List<Movie>>() {

                    @Override
                    public void onChanged(@Nullable List<Movie> movieModels) {
                        movies.clear();
                        if (movieModels!=null) {
                            movies.addAll(movieModels);
                            movieAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }


        }
        return true;
    }

    public void detailClick(int pos) {
        Intent intent = new Intent(this, ActivityDetail.class);
        Bundle b = new Bundle();
        b.putInt("id", movies.get(pos).getMovieID());
        b.putString("title", movies.get(pos).getTitle());
        b.putString("plot", movies.get(pos).getPlot());
        b.putString("image", movies.get(pos).getImage());
        b.putString("rel_date", movies.get(pos).getRelease_date());
        b.putDouble("rating", movies.get(pos).getRating());

        intent.putExtras(b);
        startActivity(intent);
    }
}