package com.example.movie_app;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;


public class ReviewActivity extends AppCompatActivity {
    private TextView author1TV,content1TV,author2TV,content2TV,author1CONST,author2CONST,noReviews;
    private String author1,author2,content1,content2;
    View dvider1,dvider2,dvider3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        noReviews=findViewById(R.id.no_reviews);

        dvider1=findViewById(R.id.view);
        dvider2=findViewById(R.id.view2);
        dvider3=findViewById(R.id.view3);

        author1CONST=findViewById(R.id.author);
        author2CONST=findViewById(R.id.author2);

        author1TV=findViewById(R.id.author_name);
        content1TV=findViewById(R.id.content);
        author2TV=findViewById(R.id.author_name2);
        content2TV=findViewById(R.id.content2);

        Bundle b=getIntent().getExtras();

        if (b!=null){
            author1=b.getString("author1");
            content1=b.getString("content1");
            author2=b.getString("author2");
            content2=b.getString("content2");

            if (author1!=null && content1!=null){
                author1TV.setText(author1);
                content1TV.setText(content1);
            }

            else{
                author1CONST.setVisibility(View.GONE);
                author1TV.setVisibility(View.GONE);
                content1TV.setVisibility(View.GONE);
                dvider1.setVisibility(View.GONE);
                dvider2.setVisibility(View.GONE);
                dvider3.setVisibility(View.GONE);
            }

            if (author2!=null && content2!=null){
                author2TV.setText(author2);
                content2TV.setText(content2);
            } else{
                author2CONST.setVisibility(View.GONE);
                author2TV.setVisibility(View.GONE);
                content2TV.setVisibility(View.GONE);
                dvider3.setVisibility(View.GONE);
            }
            if (author1==null && content1==null)
                if (author2==null && content2==null)
                    noReviews.setVisibility(View.VISIBLE);




        }

}
}

