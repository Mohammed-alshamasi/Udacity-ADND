package com.mohammedalshamasi.capastone.Adapter;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mohammedalshamasi.capastone.Data.User;
import com.mohammedalshamasi.capastone.MessageActivity;
import com.mohammedalshamasi.capastone.MyWidget;
import com.mohammedalshamasi.capastone.R;

import java.util.List;
import java.util.Random;


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
            setUpWidget(context,users);
        }

    }
    private void setUpWidget(Context context,List<User> users){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        ComponentName thisWidget = new ComponentName(context, MyWidget.class);

        Random rand=new Random();

        CharSequence username= users.get(rand.nextInt(users.size())).getUsername();
            remoteViews.setTextViewText(R.id.users_tv,username);

        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }
}
