package com.example.movie_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ActivityDetail extends AppCompatActivity {

    TextView mTitleTextView,mReleaseDate,mRatingTextView,mPlotTextView;
    ImageView mImageView;
    private final String TITLE_KEY="title";
    private final String REL_DATE_KEY="rel_date";
    private final String RATING_KEY="rating";
    private final String PLOT_KEY="plot";
    private final String IMAGE_KEY="image";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitleTextView=findViewById(R.id.title_tv);
        mReleaseDate=findViewById(R.id.relase_date_tv);
        mRatingTextView=findViewById(R.id.rating_tv);
        mPlotTextView=findViewById(R.id.plot_tv);
        mImageView=findViewById(R.id.poster);


        Bundle b=getIntent().getExtras();
        if (b!=null){
            mTitleTextView.setText(b.getString(TITLE_KEY));
            mReleaseDate.setText(b.getString(REL_DATE_KEY));
            double rating=b.getDouble(RATING_KEY);
            mRatingTextView.setText(String.valueOf(rating));
            mPlotTextView.setText(b.getString(PLOT_KEY));
            String imagePath=b.getString(IMAGE_KEY);
            Picasso.with(this).load(imagePath).into(mImageView);
        }



    }
}
