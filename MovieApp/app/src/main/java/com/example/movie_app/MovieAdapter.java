package com.example.movie_app;


import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;



import com.example.movie_app.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ItemViewHolder> {
    Context context;
    List<Movie> movies;
    onClickDetail mOnClickDetail;

    public MovieAdapter(Context context, List<Movie> m,onClickDetail onClickDetail){
        this.context=context;
        this.movies= m;
        this.mOnClickDetail=onClickDetail;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item,viewGroup,false);
        return new ItemViewHolder(view,mOnClickDetail);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {

        Picasso.with(context).load(movies.get(i).getMoviePoster()).into(itemViewHolder.movieImage);
        Log.i("msg", "link is " + movies.get(i).getMoviePoster());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movieImage;
        onClickDetail onClickDetail;
        public ItemViewHolder(@NonNull View itemView,onClickDetail onClickDetail) {
            super(itemView);
            movieImage=itemView.findViewById(R.id.image_view);
            this.onClickDetail=onClickDetail;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickDetail.detailClick(getAdapterPosition());
        }
    }
    public interface onClickDetail{
        void detailClick(int pos);
    }
}
