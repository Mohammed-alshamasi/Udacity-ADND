package com.mohammedalshamasi.capastone.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mohammedalshamasi.capastone.Data.Chat;
import com.mohammedalshamasi.capastone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ItemViewHolder> {

    public final static int MSG_LEFT=0;
    public final static int MSG_RIGHT=1;

    private List<Chat> chats;
    private Context context;
    private String imageUrl;
    private FirebaseUser firebaseUser;
    private TextView isSeen;
    public MessageAdapter(List<Chat> chats, Context context,String imageUrl) {
        this.chats = chats;
        this.context = context;
        this.imageUrl=imageUrl;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == MSG_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, viewGroup, false);
            return new MessageAdapter.ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, viewGroup, false);
            return new MessageAdapter.ItemViewHolder(view);
        }

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int i) {
        Chat chat=chats.get(i);

        viewHolder.showMessage.setText(chat.getMsg());

        if (imageUrl.equalsIgnoreCase("default")){
            viewHolder.userImage.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(context).load(imageUrl).into(viewHolder.userImage);
        }
        if(i==chats.size()-1)
            if (TextUtils.isEmpty(chats.get(i).getMsg()))
                isSeen.setVisibility(View.VISIBLE);


    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
    class ItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView userImage;
        public TextView showMessage;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage=itemView.findViewById(R.id.profile_image);
            showMessage=itemView.findViewById(R.id.show_message);
            isSeen=itemView.findViewById(R.id.txt_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if (chats.get(position).getSender().equalsIgnoreCase(firebaseUser.getUid())){
            return MSG_RIGHT;
        }
        return MSG_LEFT;
    }
}
