package com.example.movie_app;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;

import android.net.Uri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;



import com.example.movie_app.model.Movie;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> , MovieAdapter.onClickDetail {
    public RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    private ArrayList<Movie> movies;

    private final int ID = 7;

    private Boolean highest_rated=false;
    private Boolean popular=true;

    private static final String API_KEY=BuildConfig.ApiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);

        movieAdapter = new MovieAdapter(this, movies,this);

        recyclerView.setAdapter(movieAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        getLoaderManager().initLoader(ID, null, this);



        }

    protected void onRestart() {
        super.onRestart();
        getLoaderManager().restartLoader(ID,null,this);
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        return new MovieLoader(this,buildLink());
    }

    @Override
    public void onLoadFinished(Loader <ArrayList<Movie>> loader, ArrayList<Movie> movieArrayList) {
        movies.clear();
       movies.addAll(movieArrayList);
        movieAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

        movies.clear();
        movieAdapter.notifyDataSetChanged();
    }


    private String buildLink() {

        final String baseUrl = "https://api.themoviedb.org/3/discover/movie";
        Uri uri = Uri.parse(baseUrl);
        Uri.Builder builder = uri.buildUpon();

       if(highest_rated){
            String TopRated ="https://api.themoviedb.org/3/movie/top_rated";
           Uri uriTopRated = Uri.parse(TopRated);
           Uri.Builder builderTopRated = uriTopRated.buildUpon();
           builderTopRated.appendQueryParameter("api_key",API_KEY);
           return builderTopRated.toString();
        } else if (popular){
            builder.query("popular");

        }

        builder.appendQueryParameter("api_key",API_KEY);
        return builder.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int getId=item.getItemId();
        if(getId==R.id.highest_rated){
            highest_rated=true;
            popular=false;
            getLoaderManager().restartLoader(ID,null,this);
        }
        else{
            popular=true;
            highest_rated=false;
            getLoaderManager().restartLoader(ID,null,this);
        }

   return true;
    }

    public void detailClick(int pos) {
        Intent intent=new Intent(this,ActivityDetail.class);
        Bundle b = new Bundle();

        b.putString("title",movies.get(pos).getTitle());
        b.putString("plot",movies.get(pos).getPlot());
        b.putString("image",movies.get(pos).getImage());
        b.putString("rel_date",movies.get(pos).getRelease_date());
        b.putDouble("rating",movies.get(pos).getRating());

       intent.putExtras(b);
        startActivity(intent);
    }
}
