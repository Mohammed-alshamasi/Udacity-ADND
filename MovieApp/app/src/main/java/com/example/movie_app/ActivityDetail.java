package com.example.movie_app;

        import android.app.Activity;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.movie_app.Database.Database;
        import com.example.movie_app.Database.FavMovie;
        import com.example.movie_app.Utils.JsonUtils;
        import com.example.movie_app.Utils.NetworkUtils;
        import com.example.movie_app.model.Movie;
        import com.example.movie_app.model.Reviews;
        import com.example.movie_app.model.Trailers;
        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;
        import java.util.List;

        import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class ActivityDetail extends AppCompatActivity {

    TextView mTitleTextView,mReleaseDate,mRatingTextView,mPlotTextView;
    ImageView mImageView,mFav;
    Button Trailer1,Trailer2,ReviewButton;
    private final String TITLE_KEY="title";
    private final String REL_DATE_KEY="rel_date";
    private final String RATING_KEY="rating";
    private final String PLOT_KEY="plot";
    private final String IMAGE_KEY="image";
    private final String ID_KEY="id";
    public static int id;
    private static final String API_KEY=BuildConfig.ApiKey;
    ArrayList<Trailers> Trailers;
     FavMovie FavWorker = new FavMovie(this);
    boolean favHasBeenClicked = false;
    Movie movieRetrieved;
    private ArrayList<Reviews> Reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitleTextView=findViewById(R.id.title_tv);
        mReleaseDate=findViewById(R.id.relase_date_tv);
        mRatingTextView=findViewById(R.id.rating_tv);
        mPlotTextView=findViewById(R.id.plot_tv);
        mImageView=findViewById(R.id.poster);
        mFav=findViewById(R.id.fav);
        Trailer1=findViewById(R.id.trailer1);
        Trailer2=findViewById(R.id.trailer2);
        ReviewButton=findViewById(R.id.reviews);


        Bundle b=getIntent().getExtras();
        if (b!=null){

            movieRetrieved=new Movie(b.getInt(ID_KEY),b.getString(REL_DATE_KEY),b.getString(PLOT_KEY),b.getString(TITLE_KEY),b.getString(IMAGE_KEY),b.getDouble(RATING_KEY));
            id =b.getInt(ID_KEY);
            mTitleTextView.setText(b.getString(TITLE_KEY));
            mReleaseDate.setText(b.getString(REL_DATE_KEY));
            double rating=b.getDouble(RATING_KEY);
            mRatingTextView.setText(String.valueOf(rating));
            mPlotTextView.setText(b.getString(PLOT_KEY));
            Picasso.with(this).load(movieRetrieved.getMoviePoster()).into(mImageView);


        }
        new TrailerFromJsonAsyncTask().execute(buildJsonLinkTrailer(id));
        new SingleMovieAsyncTask().execute(movieRetrieved);
        new ReviewsFromJsonAsyncTask().execute(buildJsonLinkReviews(id));

        Trailer1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Trailers.size()>0){
                    String key = Trailers.get(0).getKey();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+key));
                    intent.putExtra("VIDEO_ID", key);
                    startActivity(intent);
                }else
                    Toast.makeText(ActivityDetail.this,"No First Trailer ",Toast.LENGTH_SHORT).show();

            }
        });

        Trailer2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Trailers.size()>1){
                    String key = Trailers.get(1).getKey();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+key));
                    intent.putExtra("VIDEO_ID", key);
                    startActivity(intent);
                }else
                    Toast.makeText(ActivityDetail.this,"No Second Trailer ",Toast.LENGTH_SHORT).show();

            }
        });

        mFav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new SingleMovieAsyncTask().execute(movieRetrieved);
                favHasBeenClicked=true;
            }
        });
        ReviewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle b = new Bundle();
                if (Reviews.size() == 2) {
                    b.putString("author1", Reviews.get(0).getAuthor());
                    b.putString("content1",Reviews.get(0).getContent());
                    b.putString("author2", Reviews.get(1).getAuthor());
                    b.putString("content2",Reviews.get(1).getContent());
                }
                else if (Reviews.size() == 1) {
                    b.putString("author1", Reviews.get(0).getAuthor());
                    b.putString("content1",Reviews.get(0).getContent());

                }
                Intent intent = new Intent(ActivityDetail.this,ReviewActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }


    public String buildJsonLinkTrailer(int id){
        final String baseUrl = "https://api.themoviedb.org/3/movie";
        Uri uri = Uri.parse(baseUrl);
        Uri.Builder builder = uri.buildUpon();
        builder.appendPath(String.valueOf(id));
        builder.appendPath("videos");
        builder.appendQueryParameter("api_key",API_KEY);

        return builder.toString();
    }

    public class TrailerFromJsonAsyncTask extends AsyncTask<String,Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String response = NetworkUtils.fetchResponse(strings [0]);
            Trailers =  JsonUtils.getTrailersList(response);
            return null;
        }


    }

    private void markAsFavorite(Movie movie) {
        FavWorker.insertFavMovie(movie);
    }

    private void unfavoriteMovie(Movie movie) {
        FavWorker.deleteFavMovie(movie);
    }
    private class SingleMovieAsyncTask extends AsyncTask<Movie, Void, Movie> {

        @Override
        protected Movie doInBackground(Movie... movie) {

            Database database = Database.getDatabase(ActivityDetail.this);
            Movie singleMovie = database.moviesDAO().getSingleMovie(movie[0].getImage());
            return singleMovie;
        }

        @Override
        protected void onPostExecute(Movie list) {
            super.onPostExecute(list);
            if(favHasBeenClicked){
                if (list != null) {

                    unfavoriteMovie(movieRetrieved);
                    mFav.setImageResource(R.drawable.emptystar);
                } else {
                    markAsFavorite(movieRetrieved);
                    mFav.setImageResource(R.drawable.star);
                }
            }
            else {
                if (list != null) {
                    mFav.setImageResource(R.drawable.star);
                } else {
                    mFav.setImageResource(R.drawable.emptystar);
                }
            }
        }
    }
    public String buildJsonLinkReviews(int id) {
        final String baseUrl = "https://api.themoviedb.org/3/movie";
        Uri uri = Uri.parse(baseUrl);
        Uri.Builder builder = uri.buildUpon();
        builder.appendPath(String.valueOf(id));
        builder.appendPath("reviews");
        builder.appendQueryParameter("api_key", API_KEY);


        return builder.toString();
    }
    public class ReviewsFromJsonAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String response = NetworkUtils.fetchResponse(strings[0]);
            Reviews=JsonUtils.getReviewsList(response);
            return null;
        }
    }
}
