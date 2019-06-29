package com.example.capastone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capastone.Adapter.MessageAdapter;
import com.example.capastone.Data.Chat;
import com.example.capastone.Data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MessageActivity extends AppCompatActivity {

    EditText editText;
    ImageButton imageButton;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    ValueEventListener seenListener;

    MessageAdapter messageAdapter;
    List<Chat> chats;
    RecyclerView recyclerView;

    Intent intent;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText=findViewById(R.id.text_send);
        imageButton=findViewById(R.id.btn_send);

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        intent=getIntent();
         userId=intent.getStringExtra("userId");
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg=editText.getText().toString();
                if (!TextUtils.isEmpty(msg))
                sendMessage(firebaseUser.getUid(),userId,msg);
                else{
                    Toast.makeText(MessageActivity.this,"Write something first",Toast.LENGTH_SHORT).show();
                }
                editText.setText("");
            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference().child("User").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user =dataSnapshot.getValue(User.class);
                getSupportActionBar().setTitle(user.getUsername());
                readMessage(firebaseUser.getUid(),userId,user.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        seenMessage(userId);
    }
    private void sendMessage(String sender,String rec ,String msg){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();

        HashMap<String ,Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",rec);
        hashMap.put("msg",msg);
        hashMap.put("isSeen",false);
        reference.child("Chat").push().setValue(hashMap);

        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("ChatList")
                .child(firebaseUser.getUid())
                .child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists())
                    databaseReference.child("id").setValue(userId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void seenMessage(final String id){
        databaseReference=FirebaseDatabase.getInstance().getReference("Chat");
        seenListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data :dataSnapshot.getChildren()) {
                    Chat chat=data.getValue(Chat.class);
                    if (chat.getReceiver().equalsIgnoreCase(firebaseUser.getUid())&& chat.getSender().equalsIgnoreCase(id)){
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("isSeen",true);
                        data.getRef().updateChildren(hashMap);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void readMessage(final String myid, final String userId, final String imageUrl){
        chats=new ArrayList<>();

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Chat");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            chats.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()) {

                    Chat chat =data.getValue(Chat.class);
                    if (chat.getSender().equalsIgnoreCase(userId)&& chat.getReceiver().equalsIgnoreCase(myid)
                            ||chat.getSender().equalsIgnoreCase(myid)&& chat.getReceiver().equalsIgnoreCase(userId)){
                        chats.add(chat);
                    }
                    messageAdapter=new MessageAdapter(chats,MessageActivity.this,imageUrl);
                    recyclerView.setAdapter(messageAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseReference.removeEventListener(seenListener);
    }
}
