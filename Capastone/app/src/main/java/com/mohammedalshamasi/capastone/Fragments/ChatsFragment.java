package com.mohammedalshamasi.capastone.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mohammedalshamasi.capastone.Adapter.UserAdapter;
import com.mohammedalshamasi.capastone.Data.ChatList;
import com.mohammedalshamasi.capastone.Data.User;
import com.mohammedalshamasi.capastone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {

    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<User> users;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    List<ChatList> usersList;

    public ChatsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chat_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        usersList = new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference=FirebaseDatabase.getInstance().getReference("ChatList").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()) {
                    ChatList chatList=data.getValue(ChatList.class);
                    usersList.add(chatList);

                }

            chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }

    private void chatList() {
        users=new ArrayList<>();
        databaseReference=FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    for (ChatList chatList:usersList) {
                        if (user.getId().equalsIgnoreCase(chatList.getId())){
                            users.add(user);
                        }
                    }

                }
                 userAdapter=new UserAdapter(users,getContext());
                recyclerView.setAdapter(userAdapter);

            }  @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }


}
