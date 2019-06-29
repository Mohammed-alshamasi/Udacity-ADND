package com.example.capastone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capastone.Data.User;
import com.example.capastone.MessageActivity;
import com.example.capastone.R;
import com.example.capastone.StartActivity;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ItemViewHolder> {

    List<User> users;
    Context context;
    public UserAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_item,viewGroup,false);
        return new UserAdapter.ItemViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int i) {
        final User user=users.get(i);
        viewHolder.username.setText(user.getUsername());
        if (user.getImageURL().equalsIgnoreCase("default")){
            viewHolder.userImage.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(context).load(user.getImageURL()).into(viewHolder.userImage);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, MessageActivity.class);
                intent.putExtra("userId",user.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    class ItemViewHolder extends RecyclerView.ViewHolder{
       public ImageView userImage;
       public TextView username;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage=itemView.findViewById(R.id.profile_image);
            username=itemView.findViewById(R.id.username);
        }
    }
}
